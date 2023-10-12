package com.grace.common.entity;

import com.grace.common.entity.builder.InstanceBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * grace注册中心的实例
 *
 * @author youzhengjie
 * @date 2023/06/16 01:02:52
 */
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务实例id
     */
    private String instanceId;

    /**
     * 该实例所属的服务名
     */
    private String serviceName;

    /**
     * 该实例的ip地址
     */
    private String ipAddr;

    /**
     * 该实例的端口号
     */
    private int port;

    /**
     * 该实例的权重
     */
    private double weight = 1.0D;

    /**
     * 实例是否健康
     */
    private boolean healthy = true;

    /**
     * 是否为临时实例
     */
    private boolean ephemeral = true;

    /**
     * 是否在线
     */
    private boolean online = true;

    /**
     * 该实例的元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Instance() {

    }

    public Instance(String instanceId, String serviceName, String ipAddr, int port, double weight, boolean healthy, boolean ephemeral, boolean online, Map<String, String> metadata, LocalDateTime createTime) {
        this.instanceId = instanceId;
        this.serviceName = serviceName;
        this.ipAddr = ipAddr;
        this.port = port;
        this.weight = weight;
        this.healthy = healthy;
        this.ephemeral = ephemeral;
        this.online = online;
        this.metadata = metadata;
        this.createTime = createTime;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Instance setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public Instance setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Instance setPort(int port) {
        this.port = port;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public Instance setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Instance setMetadata(Map<String,String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Instance setEphemeral(boolean ephemeral) {
        this.ephemeral = ephemeral;
        return this;
    }

    public boolean getHealthy() {
        return healthy;
    }

    public Instance setHealthy(boolean healthy) {
        this.healthy = healthy;
        return this;
    }

    public boolean getEphemeral() {
        return ephemeral;
    }

    public Instance setOnline(boolean online) {
        this.online = online;
        return this;
    }

    public boolean getOnline() {
        return online;
    }


    public Instance setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }


    @Override
    public String toString() {
        return "Instance{" +
                "instanceId='" + instanceId + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", healthy=" + healthy +
                ", ephemeral=" + ephemeral +
                ", online=" + online +
                ", metadata=" + metadata +
                ", createTime=" + createTime +
                '}';
    }
}
