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

    public SysService setId(Long id) {
        this.id = id;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public SysService setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public SysService setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public String getMetaData() {
        return metaData;
    }

    public SysService setMetaData(String metaData) {
        this.metaData = metaData;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public SysService setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * SysService建造者类
     *
     * @author youzhengjie
     * @date 2023-07-07 19:15:38
     */
    private static final class SysServiceBuilder {

        /**
         * 主键。服务名id
         */
        private Long id;

        /**
         * 服务名称
         */
        private String serviceName;

        /**
         * 分组名称
         */
        private String groupName;

        /**
         * 元数据
         */
        private String metaData;

        /**
         * 创建时间
         */
        private LocalDateTime createTime;

        public SysServiceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SysServiceBuilder serviceName(String serviceName) {
            this.serviceName = serviceName;
            return this;
        }

        public SysServiceBuilder groupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public SysServiceBuilder metaData(String metaData) {
            this.metaData = metaData;
            return this;
        }

        public SysServiceBuilder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        /**
         * 构建对象
         */
        public SysService build(){
            return BeanUtil.copyProperties(this, SysService.class);
        }

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
