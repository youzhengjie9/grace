package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * grace注册中心的服务表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 00:54:38
 */
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命名空间id（一旦创建就不能修改）
     */
    private String namespaceId;

    /**
     * 分组名称（一旦创建就不能修改）
     */
    private String groupName;

    /**
     * 服务名称（一旦创建就不能修改）
     */
    private String serviceName;

    /**
     * 保护阈值（其实就是最小的健康实例数占比,如果当前service的健康实例数占比<=最小的健康实例数占比,说明需要触发保护阈值）
     * <p>
     * protectThreshold的范围是,最小值为 0,最大值为 1
     * <p>
     * 比如protectThreshold=0.25,相当于protectThreshold（最小的健康实例数占比）为 25%,小于或者等于这个值就会触发保护阈值
     */
    private Float protectThreshold;

    /**
     * 存储该service下的所有临时实例
     */
    private Set<Instance> ephemeralInstances;

    /**
     * 存储该service下的所有永久实例
     */
    private Set<Instance> persistentInstances;

    /**
     * 元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次的修改时间
     */
    private LocalDateTime lastUpdatedTime;

    public Service() {
    }

    public Service(String namespaceId, String groupName, String serviceName, Float protectThreshold, Set<Instance> ephemeralInstances, Set<Instance> persistentInstances, Map<String, String> metadata, LocalDateTime createTime, LocalDateTime lastUpdatedTime) {
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.serviceName = serviceName;
        this.protectThreshold = protectThreshold;
        this.ephemeralInstances = ephemeralInstances;
        this.persistentInstances = persistentInstances;
        this.metadata = metadata;
        this.createTime = createTime;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public Float getProtectThreshold() {
        return protectThreshold;
    }

    public Service setProtectThreshold(Float protectThreshold) {
        this.protectThreshold = protectThreshold;
        return this;
    }

    public Set<Instance> getEphemeralInstances() {
        return ephemeralInstances;
    }

    public Service settEphemeralInstances(Set<Instance> ephemeralInstances) {
        this.ephemeralInstances = ephemeralInstances;
        return this;
    }

    public Set<Instance> getPersistentInstances() {
        return persistentInstances;
    }

    public Service setPersistentInstances(Set<Instance> persistentInstances) {
        this.persistentInstances = persistentInstances;
        return this;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public Service setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Service setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public Service setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
        return this;
    }

    /**
     * 获取所有实例（包括临时实例、永久实例）
     *
     * @return {@link List}<{@link Instance}>
     */
    public List<Instance> getAllInstance(){
        List<Instance> instanceList = new CopyOnWriteArrayList<>();
        // 如果临时实例集合不为空并且大小>0,则将所有“临时实例”保存到集合中
        if(ephemeralInstances != null && ephemeralInstances.size()>0) {
            instanceList.addAll(ephemeralInstances);
        }
        // 如果永久 实例集合不为空并且大小>0,则将所有“永久实例”保存到集合中
        if(persistentInstances != null && persistentInstances.size()>0) {
            instanceList.addAll(persistentInstances);
        }
        return instanceList;
    }

    /**
     * 获取所有健康实例的数量（包括临时实例、永久实例）
     *
     * @return int
     */
    public int getAllHealthyInstanceCount() {
        AtomicInteger healthyInstanceCount = new AtomicInteger(0);
        // 获取所有实例
        List<Instance> instanceList = getAllInstance();
        for (Instance instance : instanceList) {
            // 如果该实例是健康的
            if (instance.getHealthy()) {
                // 计数器+1
                healthyInstanceCount.incrementAndGet();
            }
        }
        return healthyInstanceCount.get();
    }

    /**
     * 获取service触发保护阈值的标志（判断当前service是否触发保护阈值）
     * <p>
     * 如果当前service的健康实例数占比<=最小的健康实例数占比,说明需要触发保护阈值）
     *
     * @return boolean
     */
    public boolean getServiceTriggerProtectThresholdFlag(){

        // 当前service的健康实例数占比为(getAllHealthyInstanceCount() * 1.0 / getAllInstance().size())
        // 保护阈值（最小的健康实例数占比）为 protectThreshold
        return (getAllHealthyInstanceCount() * 1.0 / getAllInstance().size()) <= protectThreshold ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Service service = (Service) o;
        return Objects.equals(namespaceId, service.namespaceId)
                && Objects.equals(groupName, service.groupName)
                && Objects.equals(serviceName, service.serviceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespaceId, groupName ,serviceName);
    }

    @Override
    public String toString() {
        return "Service{" +
                "namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", protectThreshold=" + protectThreshold +
                ", ephemeralInstances=" + ephemeralInstances +
                ", persistentInstances=" + persistentInstances +
                ", metadata=" + metadata +
                ", createTime=" + createTime +
                ", lastUpdatedTime=" + lastUpdatedTime +
                '}';
    }
}
