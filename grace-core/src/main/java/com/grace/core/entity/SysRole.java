package com.grace.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 角色
 * @author youzhengjie
 * @date 2022/10/13 12:04:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
@EqualsAndHashCode
@Builder
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     * mp会自动为@TableId("id")属性生成id（默认是雪花算法生成的分布式id）。
     */
    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    private Long id;

    /**
     * 角色名称，比如管理员
     */
    @TableField("name")
    private String name;

    /**
     * 角色关键字，比如admin
     */
    @TableField("role_key")
    private String roleKey;

    /**
     * 角色状态（0正常 1停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableLogic
    @TableField("del_flag")
    private int delFlag;

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
     * 备注
     */
    @TableField("remark")
    private String remark;

}
