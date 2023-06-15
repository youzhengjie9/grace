package com.grace.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * 用户表(User)实体类
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
@EqualsAndHashCode
@Builder
@Accessors(chain = true)
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * 主键
    * mp会自动为@TableId("id")属性生成id（默认是雪花算法生成的分布式id）。
    */
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @TableId("id")
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
}