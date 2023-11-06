package com.grace.common.vo;

import java.io.Serializable;

public class UserInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 该用户的动态菜单（侧边栏）
     */
    private String dynamicMenu;

    /**
     * 该用户的动态路由（router）
     */
    private String dynamicRouter;

    // 用户权限perm（JSON类型）
    private String perm;

    public UserInfoVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getDynamicMenu() {
        return dynamicMenu;
    }

    public void setDynamicMenu(String dynamicMenu) {
        this.dynamicMenu = dynamicMenu;
    }

    public String getDynamicRouter() {
        return dynamicRouter;
    }

    public void setDynamicRouter(String dynamicRouter) {
        this.dynamicRouter = dynamicRouter;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    @Override
    public String toString() {
        return "UserInfoVO{" +
                "username='" + username + '\'' +
                ", dynamicMenu='" + dynamicMenu + '\'' +
                ", dynamicRouter='" + dynamicRouter + '\'' +
                ", perm='" + perm + '\'' +
                '}';
    }
}
