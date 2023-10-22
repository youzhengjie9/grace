package com.grace.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 配置版本service实现类
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
    public List<ConfigVersionListItemVO> getConfigVersionListItemVOByPage(String namespaceId, String groupName, String dataId, Integer page, Integer size) {
        return configVersionMapper.getConfigVersionListItemVOByPage(namespaceId, groupName, dataId, page, size);
    }

    @Override
    public int getConfigVersionTotalCount(String namespaceId, String groupName, String dataId) {
        return configVersionMapper.getConfigVersionTotalCount(namespaceId, groupName, dataId);
    }

    @Override
    public ConfigVersion getConfigVersion(Long configVersionId) {

        return configVersionMapper.getConfigVersion(configVersionId);
    }

    @Override
    public Map<String, Set<String>> getAllDataIdAndGroupName(String namespaceId) {
        Map<String, Set<String>> allDataIdAndGroupNameMap = new ConcurrentHashMap<>();
        Set<String> allDataId = Collections.synchronizedSet(new HashSet<>());
        Set<String> allGroupName = Collections.synchronizedSet(new HashSet<>());
        List<ConfigVersion> configVersionList = configVersionMapper.getAllDataIdAndGroupName(namespaceId);
        configVersionList.forEach(configVersion -> {
            // 将dataId和groupName分别放在不同的set集合中进行去重。
            allDataId.add(configVersion.getDataId());
            allGroupName.add(configVersion.getGroupName());
        });
        // 将数据放到map集合中
        allDataIdAndGroupNameMap.put("allDataId", allDataId);
        allDataIdAndGroupNameMap.put("allGroupName", allGroupName);
        return allDataIdAndGroupNameMap;
    }

    @Override
    public Boolean rollbackConfig(Long configVersionId, HttpServletRequest request) {
        // 获取指定的配置版本
        ConfigVersion configVersion = configVersionMapper.getConfigVersion(configVersionId);
        if (Objects.isNull(configVersion)) {
            return false;
        }
        String namespaceId = configVersion.getNamespaceId();
        String groupName = configVersion.getGroupName();
        String dataId = configVersion.getDataId();
        long currentConfigVersionId = configService.getCurrentVersionId(namespaceId, groupName, dataId);
        // 如果这个配置现在的版本id“等于”我们想要回滚的配置版本id（则不需要进行回滚，因为配置版本一样,没必要回滚）
        if (currentConfigVersionId == configVersionId) {
            // 直接返回回滚成功即可
            return true;
        }
        LocalDateTime currentTime = LocalDateTime.now();
        // 如果这个配置现在的版本id“不等于”我们想要回滚的配置版本id（则需要进行回滚）
        // 对于回滚操作来说,配置表(sys_config)的配置属于是“旧配置”
        Config oldConfig = configService.getConfig(namespaceId, groupName, dataId);
        // 如果配置表(sys_config)对应的配置存在,则修改配置
        if (oldConfig != null) {
            LambdaUpdateWrapper<Config> updateWrapper =
                    new LambdaUpdateWrapper<Config>()
                            // 更新当前配置的版本id
                            .set(Config::getCurrentVersionId, configVersionId)
                            .set(Config::getContent, configVersion.getContent())
                            .set(Config::getMd5, configVersion.getMd5())
                            .set(Config::getConfigDesc, configVersion.getConfigDesc())
                            .set(Config::getType, configVersion.getType())
                            .set(Config::getLastUpdateTime, currentTime)
                            .eq(Config::getNamespaceId, namespaceId)
                            .eq(Config::getGroupName, groupName)
                            .eq(Config::getDataId, dataId);
            // 修改配置（不走发布配置方法,因为发布配置方法会创建一条配置版本,我们这里不需要）
            return configService.update(null, updateWrapper);
        }
        // 如果配置表(sys_config)对应的配置不存在（说明该配置“被删除”了）
        // 那么这种情况下我们的"回滚原理"是: 利用配置版本（ConfigVersion）的数据创建一个配置（Config）
        Config config = ConfigBuilder.newBuilder()
                .id(SnowId.nextId())
                .namespaceId(configVersion.getNamespaceId())
                .groupName(configVersion.getGroupName())
                .dataId(configVersion.getDataId())
                .content(configVersion.getContent())
                .md5(configVersion.getMd5())
                .configDesc(configVersion.getConfigDesc())
                .type(configVersion.getType())
                // 记得把当前配置的版本id给设置上
                .currentVersionId(configVersionId)
                // TODO: 2023/10/20 userid为执行版本回滚的用户
                .createUserId(123L)
                .createUserIp(IpUtils.getIpAddrByHttpServletRequest(request))
                .createTime(currentTime)
                .lastUpdateTime(currentTime)
                .build();
        // 重新创建配置(不走发布配置方法,因为发布配置方法会创建一条配置版本,我们这里不需要)
        return configService.save(config);

    }
}
