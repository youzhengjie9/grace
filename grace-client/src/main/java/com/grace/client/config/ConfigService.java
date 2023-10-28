package com.grace.client.config;

import com.grace.common.entity.Config;

/**
 * 配置中心
 *
 * @author youzhengjie
 * @date 2023/10/26 18:41:53
 */
public interface ConfigService {

    /**
     * 获取配置
     *
     * @return {@link Config}
     */
    Config getConfig(String namespaceId,String groupName,String dataId);

}