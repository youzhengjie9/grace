package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * grace注册中心的服务表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 00:54:38
 */
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务名id
     */
    private Long id;

    /**
     * 命名空间id
     */
    private Long namespaceId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 该service下的所有实例
     */
    private List<Instance> instances = new ArrayList<>();

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

    public Service(Long id, Long namespaceId, String serviceName, String groupName, List<Instance> instances, Map<String, String> metadata, LocalDateTime createTime) {
        this.id = id;
        this.namespaceId = namespaceId;
        this.serviceName = serviceName;
        this.groupName = groupName;
        this.instances = instances;
        this.metadata = metadata;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public Service setId(Long id) {
        this.id = id;
        return this;
    }

    public Service setNamespaceId(Long namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public Long getNamespaceId() {
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

    public List<Instance> getInstances() {
        return instances;
    }

    public Service setInstances(List<Instance> instances) {
        this.instances = instances;
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

    /**
     * Service建造者类
     *
     * @author youzhengjie
     * @date 2023-07-07 19:15:38
     */
    private static final class ServiceBuilder {

        /**
         * 服务名id
         */
        private Long id;

        /**
         * 命名空间id
         */
        private Long namespaceId;

        /**
         * 服务名称
         */
        private String serviceName;

        /**
         * 分组名称
         */
        private String groupName;

        /**
         * 该service下的所有实例
         */
        private List<Instance> instances = new ArrayList<>();

        /**
         * 元数据
         */
        private Map<String, String> metadata = new HashMap<>();

        /**
         * 创建时间
         */
        private LocalDateTime createTime;

        public ServiceBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ServiceBuilder namespaceId(Long namespaceId) {
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

        public ServiceBuilder instances(List<Instance> instances) {
            this.instances = instances;
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

        /**
         * 构建对象
         */
        public Service build(){
            return BeanUtil.copyProperties(this, Service.class);
        }

    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", namespaceId=" + namespaceId +
                ", serviceName='" + serviceName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", instances=" + instances +
                ", metadata=" + metadata +
                ", createTime=" + createTime +
                '}';
    }
}
