package com.grace.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 配置版本实体类（其实配置版本就相当于是操作配置的日志）,包括对配置的新增、修改、删除都会记录到这里
 *
 * @author youzhengjie
 * @date 2023/10/19 17:28:12
 */
@TableName("sys_config_version")
public class ConfigVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,配置版本id（通过这个id去获取到"某一个"配置版本，但是获取某一个配置的“配置版本列表”还是通过namespaceId、groupName、dataId）
     */
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    /**
     * 配置所属的命名空间id
     */
    @TableField("namespace_id")
    private String namespaceId;

    /**
     * 配置所属的分组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 配置的名称
     */
    @TableField("data_id")
    private String dataId;

    /**
     * 配置的内容
     */
    @TableField("content")
    private String content;

    /**
     * 配置内容的MD5值
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
     * 操作这个配置的用户的id
     */
    @TableField("operation_user_id")
    private Long operationUserId;

    /**
     * 操作这个配置的用户的ip地址（操作人的ip地址）
     */
    @TableField("operation_user_ip")
    private String operationUserIp;

    /**
     * 这个配置被执行了什么操作（比如新增、修改、删除）,可查看com.grace.common.enums.ConfigOperationTypeEnum
     */
    @TableField("operation_type")
    private String operationType;

    /**
     * 操作这个配置的时间
     */
    @TableField("operation_time")
    private LocalDateTime operationTime;

    public ConfigVersion() {
    }

    public ConfigVersion(Long id, String namespaceId, String groupName, String dataId, String content, String md5, String configDesc, String type, Long operationUserId, String operationUserIp, String operationType, LocalDateTime operationTime) {
        this.id = id;
        this.namespaceId = namespaceId;
        this.groupName = groupName;
        this.dataId = dataId;
        this.content = content;
        this.md5 = md5;
        this.configDesc = configDesc;
        this.type = type;
        this.operationUserId = operationUserId;
        this.operationUserIp = operationUserIp;
        this.operationType = operationType;
        this.operationTime = operationTime;
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

    public Long getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(Long operationUserId) {
        this.operationUserId = operationUserId;
    }

    public String getOperationUserIp() {
        return operationUserIp;
    }

    public void setOperationUserIp(String operationUserIp) {
        this.operationUserIp = operationUserIp;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public LocalDateTime getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
    }

    @Override
    public String toString() {
        return "ConfigVersion{" +
                "id=" + id +
                ", namespaceId='" + namespaceId + '\'' +
                ", groupName='" + groupName + '\'' +
                ", dataId='" + dataId + '\'' +
                ", content='" + content + '\'' +
                ", md5='" + md5 + '\'' +
                ", configDesc='" + configDesc + '\'' +
                ", type='" + type + '\'' +
                ", operationUserId=" + operationUserId +
                ", operationUserIp='" + operationUserIp + '\'' +
                ", operationType='" + operationType + '\'' +
                ", operationTime=" + operationTime +
                '}';
    }
}
