package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.utils.Result;
import com.grace.security.dto.MenuDTO;
import com.grace.security.entity.Menu;
import com.grace.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author youzhengjie
 * @date 2023-11-07 00:28:35
 */
@RestController
@RequestMapping(path = ParentMappingConstants.MENU_CONTROLLER)
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 通过角色id去查询该角色已选择的菜单
     *
     * @param id 角色id
     * @return {@link Result}<{@link List}<{@link Menu}>>
     */
    @GetMapping(path = "/getRoleCheckedMenuByRoleId")
    public Result<List<Menu>> getRoleCheckedMenuByRoleId(@RequestParam("id") String id){
        try {
            long roleId = Long.parseLong(id);
            List<Menu> menus = menuService.getRoleCheckedMenuByRoleId(roleId);
            return Result.ok(menus);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 添加菜单
     *
     * @param menuDTO 菜单DTO
     * @return {@link Result}<{@link Object}>
     */
    @PostMapping("/addMenu")
    public Result<Object> addMenu(@RequestBody @Valid MenuDTO menuDTO){
        try {
            menuService.addMenu(menuDTO);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 修改菜单
     *
     * @param menuDTO 菜单DTO
     * @return {@link Result}<{@link Object}>
     */
    @PostMapping("/modifyMenu")
    public Result<Object> modifyMenu(@RequestBody @Valid MenuDTO menuDTO){
        try {
            menuService.modifyMenu(menuDTO);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 删除菜单
     *
     * @param menuId menuId
     * @return {@link Result}<{@link Object}>
     */
    @DeleteMapping(path = "/deleteMenu")
    public Result<Object> deleteMenu(@RequestParam("menuId") long menuId){
        try {
            menuService.deleteMenu(menuId);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 通过菜单id获取菜单名称
     *
     * @param menuId menuId
     * @return {@link Result}<{@link String}>
     */
    @GetMapping(path = "/getMenuNameByMenuId")
    public Result<String> getMenuNameByMenuId(@RequestParam("menuId") long menuId){
        try {
            //说明是顶层菜单
            String menuName=null;
            if(menuId==0){
                menuName="顶层菜单";
            }else {
                menuName = menuService.getMenuNameByMenuId(menuId);
            }
            return Result.ok(menuName);
        } catch (Exception e) {
            return Result.fail(null);
        }
    }

    /**
     * 根据userid获取用户权限。（说白了就是获取sys_menu表中type=1和type=2的perms），这就是我们访问任何接口和菜单的权限
     */
    @GetMapping(path = "/getUserPermissionByUserId")
    public Result<List<String>> getUserPermissionByUserId(@RequestParam("userid") long userid){
        try {
            List<String> permission = menuService.getUserPermissionByUserId(userid);
            return Result.ok(permission);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

}
