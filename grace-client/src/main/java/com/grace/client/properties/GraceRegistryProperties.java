package com.grace.client.properties;

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
    private String consoleAddress = "127.0.0.1:8848";

    /**
     * grace用户名
     */
    private String username = "grace";

    /**
     * grace密码
     */
    private String password = "grace";

    /**
     * 命名空间（将该服务的实例注册到哪个命名空间上）
     */
    private String namespace;

    /**
     * 该服务实例所属的服务名
     */
    @Value("${grace.registry.service-name:${spring.application.name}}")
    private String serviceName;

    /**
     * 该服务实例的权重
     */
    private int weight = 1;

    /**
     * TODO 暂不支持集群模式
     * 集群名称
     */
    private String clusterName = "DEFAULT";

    /**
     * 分组名称
     */
    private String groupName = "DEFAULT_GROUP";

    /**
     * 元数据
     */
    private Map<String, String> metadata = new ConcurrentHashMap<>();

    /**
     * 是否将该服务实例注册到grace注册中心
     */
    private boolean enableRegister = true;

    /**
     * 客户端发送心跳请求的时间间隔,单位秒（如这里设置30,则说明30秒发送一个心跳请求给grace服务端）
     */
    private Integer heartBeatInterval;
    /**
     * 客户端发送心跳请求的超时时间,单位秒（如这里设置5,则说明客户端发送的心跳请求5秒內没有得到回应则超时）
     */
    private Integer heartBeatTimeout;

    /**
     * 是否为临时实例
     * <p>
     * 如果为true,则实例为临时实例,该实例需要定时向grace控制台发送心跳包,否则会被剔除。
     * 如果为false，则实例为永久实例，不需要发送心跳包，而是控制台会发送请求询问永久实例是否在线，就算该永久实例不在线也不会被剔除）
     */
    private boolean ephemeral = true;

    public GraceRegistryProperties() {
    }

    public GraceRegistryProperties(String consoleAddress, String username, String password, String namespace, String serviceName, int weight, String clusterName, String groupName, Map<String, String> metadata, boolean enableRegister, Integer heartBeatInterval, Integer heartBeatTimeout, boolean ephemeral) {
        this.consoleAddress = consoleAddress;
        this.username = username;
        this.password = password;
        this.namespace = namespace;
        this.serviceName = serviceName;
        this.weight = weight;
        this.clusterName = clusterName;
        this.groupName = groupName;
        this.metadata = metadata;
        this.enableRegister = enableRegister;
        this.heartBeatInterval = heartBeatInterval;
        this.heartBeatTimeout = heartBeatTimeout;
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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public boolean isEphemeral() {
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
                ", namespace='" + namespace + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", weight=" + weight +
                ", clusterName='" + clusterName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", metadata=" + metadata +
                ", enableRegister=" + enableRegister +
                ", heartBeatInterval=" + heartBeatInterval +
                ", heartBeatTimeout=" + heartBeatTimeout +
                ", ephemeral=" + ephemeral +
                '}';
    }
}
