package com.grace.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * grace注册中心的服务实例表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 01:02:52
 */
@TableName("sys_service_instance")
public class SysServiceInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,服务实例id
     * mp会自动为@TableId("id")属性生成id（默认是雪花算法生成的分布式id）。
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId("id")
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

    public SysServiceInstance() {
    }

    public SysServiceInstance(Long id, Long serviceId, String ipAddr, int port, int weight, String metaData, LocalDateTime createTime) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysServiceInstance{" +
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
