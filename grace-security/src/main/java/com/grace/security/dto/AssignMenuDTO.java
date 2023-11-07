package com.grace.security.dto;

import java.io.Serializable;

/**
 * 分配菜单DTO
 *
 * @author youzhengjie
 * @date 2023-11-07 11:48:07
 */
public class AssignMenuDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 该角色新的菜单列表的id数组
     */
    private long[] menuList;

    private long roleid;

    public long[] getMenuList() {
        return menuList;
    }

    public void setMenuList(long[] menuList) {
        this.menuList = menuList;
    }

    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }
}
