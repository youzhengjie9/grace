package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * grace注册中心的服务表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 00:54:38
 */
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命名空间id
     */
    private String namespaceId;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 存储该service下的所有临时实例
     */
    private Set<Instance> ephemeralInstances = new HashSet<>();

    /**
     * 存储该service下的所有永久实例
     */
    private Set<Instance> persistentInstances = new HashSet<>();

    /**
     * 元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Service() {
    }

    public Service(String namespaceId, String groupName, String serviceName, Set<Instance> ephemeralInstances, Set<Instance> persistentInstances, Map<String, String> metadata, LocalDateTime createTime) {
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.serviceName = serviceName;
        this.ephemeralInstances = ephemeralInstances;
        this.persistentInstances = persistentInstances;
        this.metadata = metadata;
        this.createTime = createTime;
    }

    public Service setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Service setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public Service setGroupName(String groupName) {
        this.groupName = groupName;
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
                ", ephemeralInstances=" + ephemeralInstances +
                ", persistentInstances=" + persistentInstances +
                ", metadata=" + metadata +
                ", createTime=" + createTime +
                '}';
    }
}
