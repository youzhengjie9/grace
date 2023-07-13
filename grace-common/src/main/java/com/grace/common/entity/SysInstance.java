package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * grace注册中心的实例表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 01:02:52
 */
@TableName("sys_instance")
public class SysInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,服务实例id
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    /**
     * 该实例所属的服务id
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId("service_id")
    private Long serviceId;

    /**
     * 该实例的ip地址
     */
    @TableField("ip_addr")
    private String ipAddr;

    /**
     * 该实例的端口号
     */
    @TableField("port")
    private int port;

    /**
     * 该实例的权重
     */
    @TableField("weight")
    private int weight;

    /**
     * 该实例的元数据
     */
    @TableField("meta_data")
    private String metaData;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    public SysInstance() {
    }

    public SysInstance(Long id, Long serviceId, String ipAddr, int port, int weight, String metaData, LocalDateTime createTime) {
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

    public SysInstance setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public SysInstance setServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public SysInstance setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
        return this;
    }

    public int getPort() {
        return port;
    }

    public SysInstance setPort(int port) {
        this.port = port;
        return this;
    }

    public int getWeight() {
        return weight;
    }

    public SysInstance setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    public String getMetaData() {
        return metaData;
    }

    public SysInstance setMetaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public SysInstance setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }


    /**
     * SysInstance建造者类
     *
     * @author youzhengjie
     * @date 2023-07-07 22:26:31
     */
    private static final class SysInstanceBuilder {

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

        public SysInstanceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SysInstanceBuilder serviceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }

        public SysInstanceBuilder ipAddr(String ipAddr) {
            this.ipAddr = ipAddr;
            return this;
        }

        public SysInstanceBuilder port(int port) {
            this.port = port;
            return this;
        }

        public SysInstanceBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }

        public SysInstanceBuilder metaData(String metaData) {
            this.metaData = metaData;
            return this;
        }

        public SysInstanceBuilder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }
        /**
         * 构建对象
         */
        public SysInstance build(){
            return BeanUtil.copyProperties(this, SysInstance.class);
        }
    }

    @Override
    public String toString() {
        return "SysInstance{" +
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
