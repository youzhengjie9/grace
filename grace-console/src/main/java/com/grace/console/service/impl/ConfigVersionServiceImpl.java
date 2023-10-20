package com.grace.console.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.entity.Config;
import com.grace.common.entity.ConfigVersion;
import com.grace.common.entity.builder.ConfigBuilder;
import com.grace.common.utils.IpUtils;
import com.grace.common.utils.SnowId;
import com.grace.console.mapper.ConfigVersionMapper;
import com.grace.console.service.ConfigService;
import com.grace.console.service.ConfigVersionService;
import com.grace.console.vo.ConfigVersionListItemVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * revisions config service impl
 *
 * @author youzhengjie
 * @date 2023/10/19 15:10:57
 */
@Service
public class ConfigVersionServiceImpl extends ServiceImpl<ConfigVersionMapper, ConfigVersion> implements ConfigVersionService {

    private static final Logger log = LoggerFactory.getLogger(ConfigVersionServiceImpl.class);

    @Autowired
    private ConfigVersionMapper configVersionMapper;

    @Autowired
    private ConfigService configService;

    @Override
    public List<ConfigVersionListItemVO> getRevisionsConfigListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size) {
        return configVersionMapper.getRevisionsConfigListItemVOByPage(namespaceId, groupName, dataId, page, size);
    }

    @Override
    public int getRevisionsConfigTotalCount(String namespaceId, String groupName, String dataId) {
        return configVersionMapper.getRevisionsConfigTotalCount(namespaceId, groupName, dataId);
    }

    @Override
    public ConfigVersion getRevisionsConfig(Long revisionsConfigId) {

        return configVersionMapper.getRevisionsConfig(revisionsConfigId);
    }

    @Override
    public Boolean rollbackConfig(Long revisionsConfigId, HttpServletRequest request) {

        // 获取指定的历史配置
        ConfigVersion configVersion = configVersionMapper.getRevisionsConfig(revisionsConfigId);
        if(Objects.isNull(configVersion)){
            return false;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        Config config = ConfigBuilder.newBuilder()
                .id(SnowId.nextId())
                .namespaceId(configVersion.getNamespaceId())
                .groupName(configVersion.getGroupName())
                .dataId(configVersion.getDataId())
                .content(configVersion.getContent())
                .md5(configVersion.getMd5())
                .configDesc(configVersion.getConfigDesc())
                .type(configVersion.getType())
                // TODO: 2023/10/20 userid
                .createUserId(123L)
                .createUserIp(IpUtils.getIpAddrByHttpServletRequest(request))
                .createTime(currentTime)
                .lastUpdateTime(currentTime)
                .build();
        // 发布配置
        configService.publishConfig(config);

    }
}
