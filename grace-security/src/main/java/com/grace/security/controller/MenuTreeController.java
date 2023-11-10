package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.utils.Result;
import com.grace.security.service.MenuTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单树控制器
 *
 * @author youzhengjie
 * @date 2023-11-07 00:30:50
 */
@RestController
@RequestMapping(path = ParentMappingConstants.MENU_TREE_CONTROLLER)
public class MenuTreeController {

    @Autowired
    private MenuTreeService menuTreeService;

    /**
     * 根据用户的userId来构建前端的后台管理系统侧边栏菜单
     *
     * @param userId 用户id
     * @return 菜单的json串
     */
    @GetMapping(path = "/buildTreeByUserId")
    public Result<String> buildTreeByUserId(@RequestParam("userId") String userId){
        try {
            String menuTree = menuTreeService.buildTreeByUserId(Long.parseLong(userId));
            return Result.ok(menuTree);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 将系统所有菜单权限构建成一棵树（应用于菜单管理表格数据）
     *
     * @return {@link Result}<{@link String}>
     */
    @PreAuthorize("@pms.hasPermission('menu:list')")
    @GetMapping(path = "/buildAllMenuPermissionTree")
    public Result<String> buildAllMenuPermissionTree(){
        try {
            String menuTree = menuTreeService.buildAllMenuPermissionTree();
            return Result.ok(menuTree);
        }catch (Exception e){
            return Result.fail(null);
        }

    }

    /**
     * 构建分配菜单的树（和上面buildAllMenuPermissionTree方法区别仅仅是这个方法只展示部分需要的字段、而buildAllMenuPermissionTree方法展示所有字段）
     *
     * @return {@link Result}<{@link String}>
     */
    @GetMapping(path = "/buildAssignMenuTree")
    public Result<String> buildAssignMenuTree(){
        try {
            String menuTree = menuTreeService.buildAssignMenuTree();
            return Result.ok(menuTree);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 根据新增的菜单的菜单类型来构建可以选择的菜单树（应用于菜单管理中的所属菜单上）
     * <p>
     * 如果新增的是目录type=0（可以选择的所属菜单有）：顶层目录（也就是第一层目录）、其他目录
     * 如果新增的是菜单type=1（可以选择的所属菜单有）：顶层目录（也就是第一层目录）、其他目录
     * 如果新增的是按钮type=2（可以选择的所属菜单有）：菜单
     *
     * @param type 菜单类型
     * @return {@link Result}<{@link String}>
     */
    @GetMapping(path = "/buildCanChooseMenuTreeByNewMenuType")
    public Result<String> buildCanChooseMenuTreeByNewMenuType(@RequestParam("type") int type){
        try {
            String menuTree = menuTreeService.buildCanChooseMenuTreeByNewMenuType(type);
            return Result.ok(menuTree);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

}
