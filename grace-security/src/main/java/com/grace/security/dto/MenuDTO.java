package com.grace.security.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.grace.security.entity.Menu;

import java.io.Serializable;

/**
 * 菜单DTO
 *
 * @author youzhengjie
 * @date 2023-11-06 20:49:50
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuDTO implements Serializable {

    private Menu menu;

    /**
     * 所属菜单id（也就是父菜单id）
     */
    private Long parentId;

    /**
     * 新增/修改菜单的类型（0目录、1菜单、2按钮）
     */
    private int menuType;

    public MenuDTO() {
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }
}
