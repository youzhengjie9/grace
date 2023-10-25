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
     * grace控制台（服务端）的ip地址（格式例如:127.0.0.1:8848）
     */
    private String consoleAddress = Constants.DEFAULT_CONSOLE_ADDR;

    /**
     * grace用户名
     */
    private String username = Constants.DEFAULT_USERNAME;

    /**
     * grace密码
     */
    private String password = Constants.DEFAULT_PASSWORD;

    /**
     * 命名空间id（将该服务的实例注册到哪个命名空间上）
     */
    private String namespaceId = Constants.DEFAULT_NAMESPACE_ID;

    /**
     * 分组名称
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
     * 客户端发送心跳请求的时间间隔,单位秒（如这里设置5,则说明每隔5秒发送一个心跳请求给grace-console）
     */
    private Integer heartBeatInterval = Constants.HEART_BEAT_INTERVAL;
    /**
     * 心跳超时时间,单位: 秒（如果在heartBeatTimeout时间范围内某个实例没有发送请求则超时）
     * <p>
     * 如果(当前时间 - 某个实例最后一次心跳时间) > heartBeatTimeout）则会把该实例的healthy修改为false
     */
    private Integer heartBeatTimeout = Constants.HEART_BEAT_TIMEOUT;

    /**
     * 最大心跳超时时间,单位秒（如果在maxHeartBeatTimeout时间范围内某个实例没有发送请求则将该实例“删除”）
     * <p>
     * 如果(当前时间 - 某个实例最后一次心跳时间) > maxHeartBeatTimeout）则会把该实例“删除”
     */
    private Integer maxHeartBeatTimeout = Constants.MAX_HEART_BEAT_TIMEOUT;

    /**
     * 是否为临时实例
     * <p>
     * 如果为true,则实例为临时实例,该实例需要定时向grace控制台发送心跳包,否则会被剔除。
     * 如果为false，则实例为永久实例，不需要发送心跳包，而是控制台会发送请求询问永久实例是否在线，就算该永久实例不在线也不会被剔除）
     */
    private boolean ephemeral = Constants.DEFAULT_EPHEMERAL;

    public GraceRegistryProperties() {
    }

    public GraceRegistryProperties(String consoleAddress, String username, String password, String namespaceId, String groupName, String serviceName, double weight, Map<String, String> metadata, boolean enableRegister, Integer heartBeatInterval, Integer heartBeatTimeout, Integer maxHeartBeatTimeout, boolean ephemeral) {
        this.consoleAddress = consoleAddress;
        this.username = username;
        this.password = password;
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.serviceName = serviceName;
        this.weight = weight;
        this.metadata = metadata;
        this.enableRegister = enableRegister;
        this.heartBeatInterval = heartBeatInterval;
        this.heartBeatTimeout = heartBeatTimeout;
        this.maxHeartBeatTimeout = maxHeartBeatTimeout;
        this.ephemeral = ephemeral;
    }

    public String getConsoleAddress() {
        return consoleAddress;
    }

    public void setConsoleAddress(String consoleAddress) {
        this.consoleAddress = consoleAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getHeartBeatInterval() {
        return heartBeatInterval;
    }

    public void setHeartBeatInterval(Integer heartBeatInterval) {
        this.heartBeatInterval = heartBeatInterval;
    }

    public Integer getHeartBeatTimeout() {
        return heartBeatTimeout;
    }

    public void setHeartBeatTimeout(Integer heartBeatTimeout) {
        this.heartBeatTimeout = heartBeatTimeout;
    }

    public Integer getMaxHeartBeatTimeout() {
        return maxHeartBeatTimeout;
    }

    public void setMaxHeartBeatTimeout(Integer maxHeartBeatTimeout) {
        this.maxHeartBeatTimeout = maxHeartBeatTimeout;
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
                "consoleAddress='" + consoleAddress + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", weight=" + weight +
                ", metadata=" + metadata +
                ", enableRegister=" + enableRegister +
                ", heartBeatInterval=" + heartBeatInterval +
                ", heartBeatTimeout=" + heartBeatTimeout +
                ", maxHeartBeatTimeout=" + maxHeartBeatTimeout +
                ", ephemeral=" + ephemeral +
                '}';
    }
}
