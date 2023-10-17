package com.grace.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.constant.Constants;
import com.grace.common.entity.Config;
import com.grace.common.utils.IpUtils;
import com.grace.common.utils.MD5Utils;
import com.grace.common.utils.SnowId;
import com.grace.console.dto.PublishConfigDTO;
import com.grace.console.mapper.ConfigMapper;
import com.grace.console.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * ConfigService实现类
 *
 * @author youzhengjie
 * @date 2023/10/17 23:40:01
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public Boolean publishConfig(PublishConfigDTO publishConfigDTO, HttpServletRequest request) {

        Config config = publishConfigDTO.buildConfigByPublishConfigDTO();
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
                            .eq(Config::getContent,config.getContent())
                            .eq(Config::getMd5,config.getMd5())
                            .eq(Config::getConfigDesc,config.getConfigDesc())
                            .eq(Config::getType,config.getType())
                            .eq(Config::getLastUpdateTime,config.getLastUpdateTime());
            int updateResult = configMapper.update(null,updateWrapper);
            return updateResult > 0 ;
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


}

