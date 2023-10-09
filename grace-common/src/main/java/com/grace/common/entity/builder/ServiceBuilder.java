package com.grace.common.entity.builder;

import cn.hutool.core.bean.BeanUtil;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service建造者类
 *
 * @author youzhengjie
 * @date 2023/10/06 00:13:56
 */
public class ServiceBuilder {

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
     * 保护阈值
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
    private Map<String, String> metadata;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后一次的修改时间
     */
    private LocalDateTime lastUpdatedTime;

    /**
     * 创建建造者对象
     *
     * @return {@link ServiceBuilder}
     */
    public static ServiceBuilder newBuilder() {
        return new ServiceBuilder();
    }

    public ServiceBuilder namespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public ServiceBuilder serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public ServiceBuilder groupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public ServiceBuilder protectThreshold(Float protectThreshold) {
        this.protectThreshold = protectThreshold;
        return this;
    }

    public ServiceBuilder ephemeralInstances(Set<Instance> ephemeralInstances) {
        this.ephemeralInstances = ephemeralInstances;
        return this;
    }

    public ServiceBuilder persistentInstances(Set<Instance> persistentInstances) {
        this.persistentInstances = persistentInstances;
        return this;
    }

    public ServiceBuilder metadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public ServiceBuilder createTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public ServiceBuilder lastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
        return this;
    }

    /**
     * 构建对象
     */
    public Service build() {
        return new Service(namespaceId, groupName, serviceName, protectThreshold, ephemeralInstances, persistentInstances, metadata, createTime, lastUpdatedTime);
    }


}