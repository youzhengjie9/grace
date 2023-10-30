package com.grace.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.constant.Constants;
import com.grace.common.entity.Config;
import com.grace.common.entity.ConfigVersion;
import com.grace.common.entity.builder.ConfigVersionBuilder;
import com.grace.common.enums.ConfigOperationTypeEnum;
import com.grace.common.utils.IpUtils;
import com.grace.common.utils.MD5Utils;
import com.grace.common.utils.SnowId;
import com.grace.console.event.ConfigModifiedEvent;
import com.grace.console.mapper.ConfigMapper;
import com.grace.console.mapper.ConfigVersionMapper;
import com.grace.console.service.ConfigService;
import com.grace.console.vo.ConfigListItemVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService, ApplicationContextAware {

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private ConfigVersionMapper configVersionMapper;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Boolean publishConfig(Config config, HttpServletRequest request) {
        try {
            String namespaceId = config.getNamespaceId();
            String groupName = config.getGroupName();
            String dataId = config.getDataId();
            // 生成当前最新的配置版本的id（因为我们后面要生成一条配置版本）
            long versionId = SnowId.nextId();
            LocalDateTime currentTime = LocalDateTime.now();
            // 设置新增和修改共用的属性（这样避免在下面写两份同样的代码）
            config.setMd5(MD5Utils.md5Hex(config.getContent(), Constants.ENCODE));
            config.setLastUpdateTime(currentTime);
            // 如果数据库中没有该配置,则“新增”该配置
            if(getConfig(namespaceId, groupName, dataId) == null){
                config.setId(SnowId.nextId());
                // 设置当前配置对应的最新的配置版本id
                config.setCurrentVersionId(versionId);
                // TODO: 2023/10/18 userid暂时写死
                config.setCreateUserId(666888L);
                config.setCreateUserIp(IpUtils.getIpAddrByHttpServletRequest(request));
                config.setCreateTime(currentTime);
                int insertConfigResult = configMapper.insert(config);
                // 如果“新增”成功就生成一个类型为“新增”的配置版本
                if(insertConfigResult > 0){
                    // 创建一个类型为“新增”的配置版本
                    ConfigVersion configVersion = ConfigVersionBuilder
                            .directBuild(config,versionId,ConfigOperationTypeEnum.INSERT, request);
                    // 生成类型为“新增”的配置版本
                    int insertConfigVersionResult = configVersionMapper.insert(configVersion);
                    if (insertConfigVersionResult > 0) {
                        return true;
                    }else {
                        // 如果生成配置版本失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configVersionMapper.insert(configVersion)）
                        throw new RuntimeException();
                    }
                }
            }
            // 如果数据库中有该配置,则“修改”该配置
            else {
                LambdaUpdateWrapper<Config> updateWrapper =
                        new LambdaUpdateWrapper<Config>()
                                // 更新当前配置对应的最新的配置版本id
                                .set(Config::getCurrentVersionId,versionId)
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
                // 如果“修改”成功就生成一个类型为“修改”的配置版本
                if(updateResult > 0) {
                    // 创建一个类型为“修改”的配置版本
                    ConfigVersion configVersion = ConfigVersionBuilder
                            .directBuild(config, versionId, ConfigOperationTypeEnum.UPDATE, request);
                    // 生成类型为“修改”的配置版本
                    int insertConfigVersionResult = configVersionMapper.insert(configVersion);
                    if (insertConfigVersionResult > 0) {
                        // 创建配置被修改事件
                        ConfigModifiedEvent configModifiedEvent =
                                new ConfigModifiedEvent(this,namespaceId,groupName,dataId);
                        // 发布配置被修改事件
                        applicationContext.publishEvent(configModifiedEvent);
                        return true;
                    }else {
                        // 如果生成配置版本失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configMapper.update(null,updateWrapper)）
                        throw new RuntimeException();
                    }
                }
            }
            return false;
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
            // 配置版本id
            long versionId = SnowId.nextId();
            // 删除配置
            int deleteConfigResult = configMapper.deleteConfig(namespaceId, groupName, dataId);
            // 如果删除配置成功
            if(deleteConfigResult > 0){
                // 创建一个类型为“删除”的配置版本
                ConfigVersion configVersion = ConfigVersionBuilder
                        .directBuild(config, versionId, ConfigOperationTypeEnum.DELETE, request);
                // 生成类型为“删除”的配置版本
                int insertConfigVersionResult = configVersionMapper.insert(configVersion);
                // 如果生成配置版本成功
                if(insertConfigVersionResult > 0){
                    return true;
                }else {
                    // 如果生成配置版本失败则直接抛出异常,让MySQL事务进行回滚（主要是回滚这步configMapper.deleteConfig(namespaceId, groupName, dataId)
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

    @Override
    public long getCurrentVersionId(String namespaceId, String groupName, String dataId) {
        Long currentVersionId = configMapper.getCurrentVersionId(namespaceId, groupName, dataId);
        // 如果currentVersionId为空,说明该配置在sys_config表中不存在（这种情况比较罕见,但是也有可能）
        if(currentVersionId == null){
            return 0;
        }
        return currentVersionId;
    }

}

