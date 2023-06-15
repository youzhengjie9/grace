package com.grace.core.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单（权限）表实体类
 *
 * @author youzhengjie
 * @date 2023/06/15 21:40:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value="sys_menu")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class SysMenu implements Serializable,Comparable<SysMenu> {

    private static final long serialVersionUID = 1L;


    /**
     * 菜单id
     */
    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    private Long id;


    /**
     * 父菜单id
     */
    @TableField("parent_id")
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    private Long parentId;


    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;


    /**
     * vue路由地址（type=1才会生效，type=0和2不生效）
     */
    @TableField("path")
    private String path;


    /**
     * 组件路径。动态路由要用到。views目录下的组件路径,自动会补上前缀‘../views’，这个前缀是固定的写法不能写到数据库里不然会报错（type=1才会生效，type=0和2不生效）
     */
    @TableField("component")
    private String component;

    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 菜单显示状态（0显示 1隐藏）（type=0或者1才会生效，type=2不生效）
     */
    @TableField("visible")
    private Integer visible;


    /**
     * 权限标识，比如sys:user:list(type=0设置为null即可，不会生效)
     */
    @TableField("perms")
    private String perms;

    /**
     * 菜单类型。0：目录（点击后台侧边栏可以展开成下一级菜单的按钮）;1：菜单（点击后台侧边栏直接跳转vue路由组件的按钮）;2：按钮;菜单里面的按钮
     */
    @TableField("type")
    private Integer type;

    /**
     * 菜单图标（type=0或者1才会生效，type=2不生效）
     */
    @TableField("icon")
    private String icon;

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
    @TableLogic//逻辑删除
    @TableField("del_flag")
    private Integer delFlag;


    /**
     * 前端菜单排序，默认值为1，1的优先级最高，排在最上面
     */
    @TableField("sort")
    private int sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 当前菜单的下级菜单
     */
    private List<SysMenu> children;

    //实现排序接口
    @Override
    public int compareTo(SysMenu o) {
        //升序
        return this.sort - o.getSort();
    }


}