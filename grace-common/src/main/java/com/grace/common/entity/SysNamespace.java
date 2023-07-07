package com.grace.common.entity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * grace命名空间表实体类
 *
 * @author youzhengjie
 * @date 2023/06/16 08:43:03
 */
@TableName("sys_name_space")
public class SysNamespace implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,命令空间id
     */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

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

    public SysNamespace() {
    }

    public SysNamespace(Long id, String namespaceName, String desc, LocalDateTime createTime, LocalDateTime updateTime, int delFlag) {
        this.id = id;
        this.namespaceName = namespaceName;
        this.desc = desc;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.delFlag = delFlag;
    }

    public Long getId() {
        return id;
    }

    public SysNamespace setId(Long id) {
        this.id = id;
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

    public SysNamespace setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public SysNamespace setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public SysNamespace setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public SysNamespace setDelFlag(int delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    /**
     * 获取建造者对象
     *
     * @return {@link SysNamespaceBuilder}
     */
    public static SysNamespaceBuilder builder(){
        return new SysNamespaceBuilder();
    }

    /**
     * SysNamespace建造者类
     *
     * @author youzhengjie
     * @date 2023/07/02 21:56:30
     */
    private static final class SysNamespaceBuilder {

        /**
         * 主键,命令空间id
         */
        private Long id;

        /**
         * 命名空间名称
         */
        private String namespaceName;

        /**
         * 描述
         */
        private String desc;

        /**
         * 创建时间
         */
        private LocalDateTime createTime;

        /**
         * 最后一次修改时间
         */
        private LocalDateTime updateTime;

        /**
         * 删除标志（0代表未删除，1代表已删除）
         */
        private int delFlag;

        public SysNamespaceBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SysNamespaceBuilder namespaceName(String namespaceName) {
            this.namespaceName = namespaceName;
            return this;
        }

        public SysNamespaceBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        public SysNamespaceBuilder createTime(LocalDateTime createTime) {
            this.createTime = createTime;
            return this;
        }

        public SysNamespaceBuilder updateTime(LocalDateTime updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public SysNamespaceBuilder delFlag(int delFlag) {
            this.delFlag = delFlag;
            return this;
        }

        /**
         * 构建对象
         *
         * @return {@link SysNamespace}
         */
        public SysNamespace build(){
            //使用bean拷贝,将当前对象（SysNamespaceBuilder），转成建造出来的对象（SysNamespace）
            return BeanUtil.copyProperties(this, SysNamespace.class);
        }

    }

    @Override
    public String toString() {
        return "SysNamespace{" +
                "id=" + id +
                ", namespaceName='" + namespaceName + '\'' +
                ", desc='" + desc + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}
