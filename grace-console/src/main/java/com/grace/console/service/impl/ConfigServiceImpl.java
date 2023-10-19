package com.grace.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.constant.Constants;
import com.grace.common.entity.Config;
import com.grace.common.entity.RevisionsConfig;
import com.grace.common.entity.builder.RevisionsConfigBuilder;
import com.grace.common.enums.ConfigOperationTypeEnum;
import com.grace.common.utils.IpUtils;
import com.grace.common.utils.MD5Utils;
import com.grace.common.utils.SnowId;
import com.grace.console.mapper.ConfigMapper;
import com.grace.console.mapper.RevisionsConfigMapper;
import com.grace.console.service.ConfigService;
import com.grace.console.vo.ConfigListItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ConfigService实现类
 *
 * @author youzhengjie
 * @date 2023/10/17 23:40:01
 */
@Service
@Transactional // 开启单体事务
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private RevisionsConfigMapper revisionsConfigMapper;

    @Override
    public Boolean publishConfig(Config config, HttpServletRequest request) {
        try {
            String namespaceId = config.getNamespaceId();
            String groupName = config.getGroupName();
            String dataId = config.getDataId();
            LocalDateTime currentTime = LocalDateTime.now();

            // 如果数据库中没有该配置,则“新增”该配置
            if(getConfig(namespaceId, groupName, dataId) == null){
                config.setId(SnowId.nextId());
                config.setMd5(MD5Utils.md5Hex(config.getContent(), Constants.ENCODE));
                // TODO: 2023/10/18 userid暂时写死
                config.setCreateUserId(666888L);
                config.setCreateUserIp(IpUtils.getIpAddrByHttpServletRequest(request));
                config.setCreateTime(currentTime);
                config.setLastUpdateTime(currentTime);
                int insertResult = configMapper.insert(config);
                return insertResult > 0 ;
            }
            // 如果数据库中有该配置,则“修改”该配置
            else {
                config.setMd5(MD5Utils.md5Hex(config.getContent(), Constants.ENCODE));
                config.setLastUpdateTime(currentTime);
                LambdaUpdateWrapper<Config> updateWrapper =
                        new LambdaUpdateWrapper<Config>()
                                .set(Config::getConfigDesc,config.getConfigDesc())
                                .set(Config::getType,config.getType())
                                .set(Config::getContent,config.getContent())
                                .set(Config::getMd5,config.getMd5())
                                .set(Config::getLastUpdateTime,config.getLastUpdateTime())
                                .eq(Config::getNamespaceId, namespaceId)
                                .eq(Config::getGroupName, groupName)
                                .eq(Config::getDataId, dataId);
                // 修改配置
                int updateResult = configMapper.update(null,updateWrapper);
                // 如果修改成功就生成一个历史配置
                if(updateResult > 0) {
                    RevisionsConfig revisionsConfig = RevisionsConfigBuilder.newBuilder()
                            .id(SnowId.nextId())
                            .namespaceId(namespaceId)
                            .groupName(groupName)
                            .dataId(dataId)
                            .content(config.getContent())
                            .md5(config.getMd5())
                            .configDesc(config.getConfigDesc())
                            .type(config.getType())
                            .operationUserId(123456L)
                            .operationUserIp(IpUtils.getIpAddrByHttpServletRequest(request))
                            // 设置操作类型为更新
                            .operationType(ConfigOperationTypeEnum.UPDATE.getOperationType())
                            .operationTime(currentTime)
                            .build();
                    // 生成历史配置
                    int insertResult = revisionsConfigMapper.insert(revisionsConfig);
                    if (insertResult > 0) {
                        return true;
                    }else {
                        // 如果生成历史配置失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configMapper.update(null,updateWrapper)）
                        throw new RuntimeException();
                    }
                }
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            // 触发事务回滚
            throw new RuntimeException();
        }
    }

    @Override
    public Config getConfig(String namespaceId, String groupName, String dataId) {

        return configMapper.selectOne(
                new LambdaQueryWrapper<Config>()
                        .eq(Config::getNamespaceId, namespaceId)
                        .eq(Config::getGroupName, groupName)
                        .eq(Config::getDataId, dataId)
        );
    }

    @Override
    public List<ConfigListItemVO> getConfigListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size) {
        return configMapper.getConfigListItemVOByPage(namespaceId, groupName, dataId, page, size);
    }

    @Override
    public int getConfigTotalCount(String namespaceId, String groupName, String dataId) {
        return configMapper.getConfigTotalCount(namespaceId, groupName, dataId);
    }

    @Override
    public Boolean deleteConfig(String namespaceId, String groupName, String dataId, HttpServletRequest request) {

        try {
            // 先通过namespaceId, groupName, dataId查询该需要被删除的配置（注意: 必须放在删除配置的代码之前）
            Config config = getConfig(namespaceId, groupName, dataId);
            // 删除配置
            int deleteConfigResult = configMapper.deleteConfig(namespaceId, groupName, dataId);
            // 如果删除配置成功
            if(deleteConfigResult > 0){

                RevisionsConfig revisionsConfig = RevisionsConfigBuilder.newBuilder()
                        .id(SnowId.nextId())
                        .namespaceId(namespaceId)
                        .groupName(groupName)
                        .dataId(dataId)
                        .content(config.getContent())
                        .md5(config.getMd5())
                        .configDesc(config.getConfigDesc())
                        .type(config.getType())
                        .operationUserId(5201314L)
                        .operationUserIp(IpUtils.getIpAddrByHttpServletRequest(request))
                         // 设置操作类型为删除
                        .operationType(ConfigOperationTypeEnum.DELETE.getOperationType())
                        .operationTime(LocalDateTime.now())
                        .build();
                // 生成历史配置
                int insertResult = revisionsConfigMapper.insert(revisionsConfig);
                // 如果生成历史配置成功
                if(insertResult > 0){
                    return true;
                }else {
                    // 如果生成历史配置失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configMapper.deleteConfig(namespaceId, groupName, dataId)
                    throw new RuntimeException();
                }
            }
            return false;
        }catch (Exception e){
            e.printStackTrace();
            // 触发回滚
            throw new RuntimeException();
        }

    }


}

