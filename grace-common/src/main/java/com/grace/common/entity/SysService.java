package com.grace.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * grace注册中心的服务表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 00:54:38
 */
@TableName("sys_service")
public class SysService implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。服务名id
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    /**
     * 服务名称
     */
    @TableField("service_name")
    private String serviceName;

    /**
     * 分组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 元数据
     */
    @TableField("meta_data")
    private String metaData;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    public SysService() {
    }

    public SysService(Long id, String serviceName, String groupName, String metaData, LocalDateTime createTime) {
        this.id = id;
        this.serviceName = serviceName;
        this.groupName = groupName;
        this.metaData = metaData;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
        return "SysService{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", metaData='" + metaData + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
