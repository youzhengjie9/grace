package com.grace.client.properties;

import com.grace.common.constant.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * grace注册中心属性类
 *
 * @author youzhengjie
 * @date 2023/06/16 10:05:17
 */
@ConfigurationProperties(prefix = "grace.registry")
public class GraceRegistryProperties {

    /**
     * 命名空间id（将该服务的实例注册到哪个命名空间上）
     */
    private String namespaceId = Constants.DEFAULT_NAMESPACE_ID;

    /**
     * 分组名称（将该服务的实例注册到哪个分组上）
     */
    private String groupName = Constants.DEFAULT_GROUP_NAME;

    /**
     * 该服务实例所属的服务名
     */
    @Value("${grace.registry.service-name:${spring.application.name}}")
    private String serviceName;

    /**
     * 该服务实例的权重
     */
    private double weight = Constants.DEFAULT_WEIGHT;

    /**
     * 该实例的元数据
     */
    private Map<String, String> metadata = new ConcurrentHashMap<>();

    /**
     * 是否将该服务实例注册到grace注册中心
     */
    private boolean enableRegister = true;

    /**
     * todo 暂时只支持临时实例
     * 是否为临时实例
     * <p>
     * 如果为true,则实例为临时实例,该实例需要定时向grace控制台发送心跳包,否则会被剔除。
     * 如果为false，则实例为永久实例，不需要发送心跳包，而是控制台会发送请求询问永久实例是否在线，就算该永久实例不在线也不会被剔除）
     */
    private boolean ephemeral = Constants.DEFAULT_EPHEMERAL;

    public GraceRegistryProperties() {
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public boolean isEnableRegister() {
        return enableRegister;
    }

    public void setEnableRegister(boolean enableRegister) {
        this.enableRegister = enableRegister;
    }

    public boolean getEphemeral() {
        return ephemeral;
    }

    public void setEphemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
    }

    @Override
    public String toString() {
        return "GraceRegistryProperties{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", weight=" + weight +
                ", metadata=" + metadata +
                ", enableRegister=" + enableRegister +
                ", ephemeral=" + ephemeral +
                '}';
    }
}
