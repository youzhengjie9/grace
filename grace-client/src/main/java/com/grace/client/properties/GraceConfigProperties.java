package com.grace.client.properties;

import com.grace.common.constant.Constants;
import com.grace.common.enums.ConfigTypeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * grace配置中心的配置
 * <p>
 * 注意: 一定要将下面的配置和spring.application.name、spring.profile.active配置在bootstrap.yml上
 *
 * @author youzhengjie
 * @date 2023/10/26 18:55:38
 */
@ConfigurationProperties(prefix = "grace.config")
public class GraceConfigProperties {

    /**
     * 命名空间id（该配置中心的配置在哪个命名空间上）
     */
    private String namespaceId = Constants.DEFAULT_NAMESPACE_ID;

    /**
     * 分组名称
     */
    private String groupName = Constants.DEFAULT_GROUP_NAME;

    /**
     * 配置类型
     */
    private ConfigTypeEnum configType = ConfigTypeEnum.YAML;

    public GraceConfigProperties() {
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ConfigTypeEnum getConfigType() {
        return configType;
    }

    public void setConfigType(ConfigTypeEnum configType) {
        this.configType = configType;
    }

    @Override
    public String toString() {
        return "GraceConfigProperties{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", configType=" + configType +
                '}';
    }
}
