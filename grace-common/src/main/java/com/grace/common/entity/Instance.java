package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;

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
    private Long id;

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
     * 该实例的元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Instance() {
    }



    public Long getId() {
        return id;
    }

    public Instance setId(Long id) {
        this.id = id;
        return this;
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

    public boolean isHealthy() {
        return healthy;
    }

    public Instance setHealthy(boolean healthy) {
        this.healthy = healthy;
        return this;
    }

    public boolean isEphemeral() {
        return ephemeral;
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
    public static final class InstanceBuilder {

        /**
         * 主键,服务实例id
         */
        private Long id;

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
        private double weight = 1.00;

        /**
         * 实例是否健康
         */
        private boolean healthy = true;

        /**
         * 是否为临时实例
         */
        private boolean ephemeral = true;

        /**
         * 该实例的元数据
         */
        private Map<String, String> metadata = new HashMap<>();

        /**
         * 创建时间
         */
        private LocalDateTime createTime;

        public InstanceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public InstanceBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
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

        public InstanceBuilder weight(double weight) {
            this.weight = weight;
            return this;
        }

        public InstanceBuilder healthy(boolean healthy) {
            this.healthy = healthy;
            return this;
        }

        public InstanceBuilder ephemeral(boolean ephemeral) {
            this.ephemeral = ephemeral;
            return this;
        }

        public InstanceBuilder metadata(Map<String,String> metadata) {
            this.metadata = metadata;
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
                ", serviceName='" + serviceName + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", port=" + port +
                ", weight=" + weight +
                ", healthy=" + healthy +
                ", ephemeral=" + ephemeral +
                ", metadata=" + metadata +
                ", createTime=" + createTime +
                '}';
    }
}
