package com.grace.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 用户表(User)实体类
 *
 */
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId(value = "id",type = IdType.INPUT)
    private Long id;

    /**
    * 用户名
    */
    @TableField("user_name")
    private String userName;

    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;

    /**
    * 密码
    */
    @TableField("password")
    private String password;
    /**
    * 账号状态（0正常 1停用）
    */
    @TableField("status")
    private Integer status;

    /**
    * 邮箱
    */
    @TableField("email")
    private String email;

    /**
    * 手机号
    */
    @TableField("phone")
    private String phone;

    /**
    * 用户性别（0男，1女，2未知）
    */
    @TableField("sex")
    private Integer sex;

    /**
    * 头像地址
    */
    @TableField("avatar")
    private String avatar;

    /**
    * 创建时间
    */
    @TableField("create_time")
    private LocalDate createTime;

    /**
    * 更新时间
    */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
    * 删除标志（0代表未删除，1代表已删除）
    */
    @TableLogic //逻辑删除（0代表未删除，1代表已删除）
    @TableField("del_flag")
    private Integer delFlag;

    public User() {
    }

    public User(Long id, String userName, String nickName, String password, Integer status, String email, String phone, Integer sex, String avatar, LocalDate createTime, LocalDateTime updateTime, Integer delFlag) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.status = status;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.avatar = avatar;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.delFlag = delFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", delFlag=" + delFlag +
                '}';
    }
}