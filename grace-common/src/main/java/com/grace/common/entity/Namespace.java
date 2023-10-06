package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.grace.common.entity.builder.NamespaceBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * grace命名空间表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 08:43:03
 */
@TableName("sys_name_space")
public class Namespace implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,命令空间id
     */
    @TableId(value = "namespaceId",type = IdType.INPUT)
    private String namespaceId;

    /**
     * 命名空间名称
     */
    @TableField("`namespace_name`")
    private String namespaceName;

    /**
     * 描述
     */
    @TableField("`desc`")
    private String desc;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最后一次修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableLogic
    @TableField("del_flag")
    private int delFlag;

    public Namespace() {
    }

    public Namespace(String namespaceId, String namespaceName, String desc, LocalDateTime createTime, LocalDateTime updateTime, int delFlag) {
        this.namespaceId = namespaceId;
        this.namespaceName = namespaceName;
        this.desc = desc;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.delFlag = delFlag;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public Namespace setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
        return this;
    }

    public void setNamespaceName(String namespaceName) {
        this.namespaceName = namespaceName;
    }

    public String getNamespaceName() {
        return namespaceName;
    }

    public String getDesc() {
        return desc;
    }

    public Namespace setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Namespace setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public Namespace setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public Namespace setDelFlag(int delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    @Override
    public String toString() {
        return "Namespace{" +
                "namespaceId='" + namespaceId + '\'' +
                ", namespaceName='" + namespaceName + '\'' +
                ", desc='" + desc + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
