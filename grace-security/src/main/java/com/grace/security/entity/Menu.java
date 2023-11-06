package com.grace.security.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.grace.security.converter.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单（权限）表实体类
 *
 * @author youzhengjie
 * @date 2023/11/06 20:28:07
 */
@TableName(value="sys_menu")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu implements Serializable,Comparable<Menu> {

    private static final long serialVersionUID = 1L;


    /**
     * 菜单id
     */
    @TableId("id")
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @ExcelProperty("菜单id")
    private Long id;


    /**
     * 后台侧边栏。父菜单id,一级菜单为0
     */
    @TableField("parent_id")
    @JsonSerialize(using = ToStringSerializer.class) //解决雪花算法生成的id过长导致前端js精度丢失问题（也就是js拿到的数据和后端不一致问题）
    @ExcelProperty("父菜单id")
    private Long parentId;


    /**
     * 菜单/权限名称
     */
    @TableField("menu_name")
    @ExcelProperty("菜单名称")
    private String menuName;


    /**
     * vue路由地址（type=1才会生效，type=0和2不生效）
     */
    @TableField("path")
    @ExcelProperty("路由地址")
    private String path;


    /**
     * 动态路由要用到。views目录下的组件路径,自动会补上前缀‘../views’，这个前缀是固定的写法不能写到数据库里不然会报错（type=1才会生效，type=0和2不生效）
     */
    @TableField("component")
    @ExcelProperty("组件路径")
    private String component;

    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField("status")
    @ExcelProperty(value = "菜单状态",converter = StatusConverter.class)
    private Integer status;

    /**
     * 菜单显示状态（0显示 1隐藏）（type=0或者1才会生效，type=2不生效）
     */
    @TableField("visible")
    @ExcelProperty(value = "菜单显示状态",converter = VisibleConverter.class)
    private Integer visible;


    /**
     * 权限标识，比如sys:user:list(type=0设置为null即可，不会生效)
     */
    @TableField("perms")
    @ExcelProperty("权限标识")
    private String perms;

    /**
     * 菜单类型。0：目录（点击后台侧边栏可以展开成下一级菜单的按钮）;1：菜单（点击后台侧边栏直接跳转vue路由组件的按钮）;2：按钮;菜单里面的按钮
     */
    @TableField("type")
    @ExcelProperty(value = "菜单类型",converter = MenuTypeConverter.class)
    private Integer type;

    /**
     * 菜单图标（type=0或者1才会生效，type=2不生效）
     */
    @TableField("icon")
    @ExcelIgnore
    private String icon;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @ExcelProperty(value = "创建时间",converter = LocalDateTimeConverter.class)
    private LocalDateTime createTime;

    /**
     * 最后一次修改时间
     */
    @TableField("update_time")
    @ExcelProperty(value = "最后一次修改时间",converter = LocalDateTimeConverter.class)
    private LocalDateTime updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableLogic//逻辑删除
    @TableField("del_flag")
    @ExcelProperty(value = "删除标志",converter = DelFlagConverter.class)
    private Integer delFlag;


    /**
     * 前端菜单排序，默认值为1，1的优先级最高，排在最上面
     */
    @TableField("sort")
    @ExcelProperty("菜单排序")
    private int sort;

    /**
     * 备注
     */
    @TableField("remark")
    @ExcelProperty("备注")
    private String remark;

    /**
     * 当前菜单的下级菜单
     */
    @ExcelIgnore
    private List<Menu> children;


    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    /**
     * 实现排序接口
     *
     * @return int
     */
    @Override
    public int compareTo(Menu o) {
        //升序
        return this.sort - o.getSort();
    }

}