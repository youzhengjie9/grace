package com.grace.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.grace.common.entity.builder.ConfigBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * 配置中心的配置实体类
 *
 * @author youzhengjie
 * @date 2023/10/17 20:19:11
 */
@TableName("sys_config")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键。配置的id（可以当成是摆设,如果我们想操作config,可以通过namespaceId、groupName、dataId共同作为条件来定位config）
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    /**
     * 配置所属的命名空间id（注意: 这个属性一旦放入数据库则不能修改）
     */
    @TableField("namespace_id")
    private String namespaceId;

    /**
     * 配置所属的分组名称（注意: 这个属性一旦放入数据库则不能修改）
     */
    @TableField("group_name")
    private String groupName;

    /**
     * dataId。也就是配置的名称（注意: 这个属性一旦放入数据库则不能修改）
     */
    @TableField("data_id")
    private String dataId;

    /**
     * 该配置当前的版本id(com.grace.common.entity.ConfigVersion.id)
     */
    @TableField("current_version_id")
    private Long currentVersionId;

    /**
     * 配置的内容
     */
    @TableField("content")
    private String content;

    /**
     * 配置内容的MD5值,用于判断配置文件是否被修改,只要content内容被修改了,那么其生成的MD5值一定是不同的。
     * 生成该MD5值的原理是: 将配置内容content先进行MD5加密,然后再将这个MD5加密后的值转换成16进制字符串。
     * 注意: 可通过com.grace.common.utils.MD5Utils#md5Hex(java.lang.String, java.lang.String)方法生成MD5
     */
    @TableField("md5")
    private String md5;

    /**
     * 配置的描述
     */
    @TableField("config_desc")
    private String configDesc;

    /**
     * 配置的类型（com.grace.common.enums.ConfigTypeEnum枚举类所定义的类型）
     */
    @TableField("type")
    private String type;

    /**
     * 创建该配置的用户的id
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 创建该配置的用户的ip地址
     */
    @TableField("create_user_ip")
    private String createUserIp;

    /**
     * 创建该配置的时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最后一次修改该配置的时间
     */
    @TableField("last_update_time")
    private LocalDateTime lastUpdateTime;

    public Config() {
    }

    public Config(Long id, String namespaceId, String groupName, String dataId, Long currentVersionId, String content, String md5, String configDesc, String type, Long createUserId, String createUserIp, LocalDateTime createTime, LocalDateTime lastUpdateTime) {
        this.id = id;
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.dataId = dataId;
        this.currentVersionId = currentVersionId;
        this.content = content;
        this.md5 = md5;
        this.configDesc = configDesc;
        this.type = type;
        this.createUserId = createUserId;
        this.createUserIp = createUserIp;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public void setCurrentVersionId(Long currentVersionId) {
        this.currentVersionId = currentVersionId;
    }

    public Long getCurrentVersionId() {
        return currentVersionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getConfigDesc() {
        return configDesc;
    }

    public void setConfigDesc(String configDesc) {
        this.configDesc = configDesc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserIp() {
        return createUserIp;
    }

    public void setCreateUserIp(String createUserIp) {
        this.createUserIp = createUserIp;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Config config = (Config) o;
        return Objects.equals(id, config.id)
                && Objects.equals(namespaceId, config.namespaceId)
                && Objects.equals(groupName, config.groupName)
                && Objects.equals(dataId, config.dataId)
                && Objects.equals(currentVersionId, config.currentVersionId)
                && Objects.equals(content, config.content)
                && Objects.equals(md5, config.md5)
                && Objects.equals(configDesc, config.configDesc)
                && Objects.equals(type, config.type)
                && Objects.equals(createUserId, config.createUserId)
                && Objects.equals(createUserIp, config.createUserIp)
                && Objects.equals(createTime, config.createTime)
                && Objects.equals(lastUpdateTime, config.lastUpdateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, namespaceId, groupName, dataId,currentVersionId, content, md5, configDesc, type, createUserId, createUserIp, createTime, lastUpdateTime);
    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", dataId='" + dataId + '\'' +
                ", currentVersionId=" + currentVersionId +
                ", content='" + content + '\'' +
                ", md5='" + md5 + '\'' +
                ", configDesc='" + configDesc + '\'' +
                ", type='" + type + '\'' +
                ", createUserId=" + createUserId +
                ", createUserIp='" + createUserIp + '\'' +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
