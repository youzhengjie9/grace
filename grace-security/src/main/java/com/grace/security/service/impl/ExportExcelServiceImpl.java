package com.grace.security.service.impl;

import com.grace.common.utils.EasyExcelUtils;
import com.grace.security.entity.Menu;
import com.grace.security.entity.Role;
import com.grace.security.entity.User;
import com.grace.security.service.ExportExcelService;
import com.grace.security.service.MenuService;
import com.grace.security.service.RoleService;
import com.grace.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 导出excel服务impl
 *
 * @author youzhengjie
 * @date 2023-11-07 16:08:23
 */
@Service
public class ExportExcelServiceImpl implements ExportExcelService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private EasyExcelUtils easyExcelUtils;

    private static final String EXPORT_USER_PREFIX="用户表";

    private static final String EXPORT_ROLE_PREFIX="角色表";

    private static final String EXPORT_MENU_PREFIX="菜单表";

    /**
     * 导出所有用户
     *
     * @param response response
     */
    @Override
    public void exportAllUser(HttpServletResponse response) {

        //查询出数据，后面这些数据会生成excel表
        List<User> userList = userService.lambdaQuery()
                .select(User::getId,
                        User::getUsername,
                        User::getStatus,
                        User::getCreateTime,
                        User::getUpdateTime)
                .list();
        easyExcelUtils.writeExcel(userList,User.class,EXPORT_USER_PREFIX,response);
    }

    /**
     * 导出所有角色
     *
     * @param response response
     */
    @Override
    public void exportAllRole(HttpServletResponse response) {
        List<Role> roleList = roleService.lambdaQuery()
                .select(Role::getId,
                        Role::getName,
                        Role::getRoleKey,
                        Role::getStatus,
                        Role::getDelFlag,
                        Role::getCreateTime,
                        Role::getUpdateTime,
                        Role::getRemark)
                .list();
        easyExcelUtils.writeExcel(roleList, Role.class,EXPORT_ROLE_PREFIX,response);
    }

    @Override
    public void exportAllMenu(HttpServletResponse response) {

        List<Menu> menuList = menuService.lambdaQuery()
                .select(Menu::getId,
                        Menu::getParentId,
                        Menu::getMenuName,
                        Menu::getPath,
                        Menu::getComponent,
                        Menu::getStatus,
                        Menu::getVisible,
                        Menu::getPerms,
                        Menu::getType,
                        Menu::getCreateTime,
                        Menu::getUpdateTime,
                        Menu::getDelFlag,
                        Menu::getSort,
                        Menu::getRemark)
                .orderByAsc(Menu::getSort)
                .list();
        easyExcelUtils.writeExcel(menuList, Menu.class,EXPORT_MENU_PREFIX,response);
    }
}
