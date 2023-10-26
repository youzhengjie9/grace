package com.grace.common.entity;

import com.grace.common.entity.builder.InstanceBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * grace注册中心的实例
 *
 * @author youzhengjie
 * @date 2023/06/16 01:02:52
 */
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务实例id（一旦创建就不能修改）
     */
    private String instanceId;

    /**
     * 该实例所属的服务名（一旦创建就不能修改）
     */
    private String serviceName;

    /**
     * 该实例的ip地址（一旦创建就不能修改）
     */
    private String ipAddr;

    /**
     * 该实例的端口号（一旦创建就不能修改）
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
     * 是否为临时实例（一旦创建就不能修改）
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
     * 创建时间（一旦创建就不能修改）
     */
    private LocalDateTime createTime;

    /**
     * 该实例最后一次的心跳时间（毫秒值）,用于判断该实例是否健康、是否需要删除
     */
    private volatile long lastHeartBeatTime = System.currentTimeMillis();

    public Instance() {

    }

    public Instance(String instanceId, String serviceName, String ipAddr, int port, double weight, boolean healthy, boolean ephemeral, boolean online, Map<String, String> metadata, LocalDateTime createTime, long lastHeartBeatTime) {
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
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public int getPort() {
        return port;
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

    public void setLastHeartBeatTime(long lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
    }

    public long getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Instance instance = (Instance) o;
        // 因为instance是存在service的集合中,所以只要ip和port相同就算是相同的实例
        return Objects.equals(serviceName, instance.serviceName)
                && Objects.equals(ipAddr, instance.ipAddr)
                && Objects.equals(port, instance.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, ipAddr, port);
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
