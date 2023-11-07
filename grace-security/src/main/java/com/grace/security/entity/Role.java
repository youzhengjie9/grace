package com.grace.security.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.grace.common.converter.DelFlagConverter;
import com.grace.common.converter.LocalDateTimeConverter;
import com.grace.common.converter.StatusConverter;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 角色表实体类
 *
 * @author youzhengjie
 * @date 2023-11-05 12:16:17
 */
@TableName("sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * mp会自动为@TableId("id")属性生成id（默认是雪花算法生成的分布式id）。
     */
    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @ExcelIgnore
    private Long id;

    /**
     * 角色名称
     */
    @TableField("name")
    @ExcelProperty("角色名称")
    private String name;

    /**
     * 角色关键字
     */
    @TableField("role_key")
    @ExcelProperty("角色关键字")
    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    @TableField("status")
    @ExcelProperty(value = "角色状态",converter = StatusConverter.class)
    private Integer status;

    @TableLogic
    @TableField("del_flag")
    @ExcelProperty(value = "删除标志",converter = DelFlagConverter.class)
    private int delFlag;

    @TableField("create_time")
    @ExcelProperty(value = "创建时间",converter = LocalDateTimeConverter.class)
    private LocalDateTime createTime;

    @TableField("update_time")
    @ExcelProperty(value = "最后一次修改时间",converter = LocalDateTimeConverter.class)
    private LocalDateTime updateTime;

    @TableField("remark")
    @ExcelProperty("备注")
    private String remark;

    public Role() {
    }

    public Role(Long id, String name, String roleKey, Integer status, int delFlag, LocalDateTime createTime, LocalDateTime updateTime, String remark) {
        this.id = id;
        this.name = name;
        this.roleKey = roleKey;
        this.status = status;
        this.delFlag = delFlag;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
