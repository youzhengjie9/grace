package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    private Long id;

    /**
     * 该实例所属的服务id
     */
    private Long serviceId;

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
    private int weight;

    /**
     * 该实例的元数据
     */
    private String metaData;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Instance() {
    }

    public Instance(Long id, Long serviceId, String ipAddr, int port, int weight, String metaData, LocalDateTime createTime) {
        this.id = id;
        this.serviceId = serviceId;
        this.ipAddr = ipAddr;
        this.port = port;
        this.weight = weight;
        this.metaData = metaData;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public Instance setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public Instance setServiceId(Long serviceId) {
        this.serviceId = serviceId;
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

    public int getWeight() {
        return weight;
    }

    public Instance setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public String getMetaData() {
        return metaData;
    }

    public Instance setMetaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Instance setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
    /**
     * 获取建造者对象
     *
     * @return {@link InstanceBuilder}
     */
    public static InstanceBuilder builder(){
        return new InstanceBuilder();
    }

    /**
     * Instance建造者类
     *
     * @author youzhengjie
     * @date 2023-07-07 22:26:31
     */
    private static final class InstanceBuilder {

        /**
         * 主键,服务实例id
         */
        private Long id;

        /**
         * 该实例所属的服务id
         */
        private Long serviceId;

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
        private int weight;

        /**
         * 该实例的元数据
         */
        private String metaData;

        /**
         * 创建时间
         */
        private LocalDateTime createTime;

        public InstanceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public InstanceBuilder serviceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public InstanceBuilder ipAddr(String ipAddr) {
            this.ipAddr = ipAddr;
            return this;
        }

        public InstanceBuilder port(int port) {
            this.port = port;
            return this;
        }

        public InstanceBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public InstanceBuilder metaData(String metaData) {
            this.metaData = metaData;
            return this;
        }

        public InstanceBuilder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }
        /**
         * 构建对象
         */
        public Instance build(){
            return BeanUtil.copyProperties(this, Instance.class);
        }
    }

    @Override
    public String toString() {
        return "Instance{" +
                "id=" + id +
                ", serviceId=" + serviceId +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", metaData='" + metaData + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
