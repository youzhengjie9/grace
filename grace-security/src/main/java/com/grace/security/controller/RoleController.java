package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.utils.Result;
import com.grace.common.utils.SnowId;
import com.grace.security.dto.AssignMenuDTO;
import com.grace.security.dto.RoleFormDTO;
import com.grace.security.entity.Role;
import com.grace.security.entity.RoleMenu;
import com.grace.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 角色接口
 *
 * @author youzhengjie
 * @date 2023/11/05 18:17:39
 */
@RestController
@RequestMapping(path = ParentMappingConstants.ROLE_CONTROLLER)
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取所有角色并分页
     *
     * @return {@link Result}<{@link List}<{@link Role}>>
     */
    @GetMapping(path = "/getRoleList")
    public Result<List<Role>> getRoleList(int page, int size){
        page=(page-1)*size;
        try {
            List<Role> roles = roleService.getRoleList(page, size);
            return Result.ok(roles);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 获取所有角色数量
     *
     * @return {@link Result}<{@link Integer}>
     */
    @GetMapping(path = "/getRoleCount")
    public Result<Integer> getRoleCount(){
        try {
            int count = roleService.getRoleCount();
            return Result.ok(count);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 获取所有角色
     *
     * @return {@link Result}<{@link List}<{@link Role}>>
     */
    @GetMapping(path = "/getAllRole")
    public Result<List<Role>> getAllRole(){
        try {
            List<Role> roles = roleService.getAllRole();
            return Result.ok(roles);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 根据用户id获取用户已经选择的角色
     *
     * @param id 用户id
     * @return {@link Result}<{@link List}<{@link Role}>>
     */
    @GetMapping(path = "/getUserCheckedRoleByUserId")
    public Result<List<Role>> getUserCheckedRoleByUserId(@RequestParam("id") String id){
        try {
            long userid = Long.parseLong(id);
            List<Role> roles = roleService.getUserCheckedRoleByUserId(userid);
            return Result.ok(roles);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 添加角色
     *
     * @return {@link Result}<{@link Object}>
     */
    @PostMapping("/addRole")
    public Result<Object> addRole(@RequestBody @Valid RoleFormDTO roleFormDTO){
        try {
            roleService.addRole(roleFormDTO);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 修改角色
     *
     * @return {@link Result}<{@link Object}>
     */
    @PostMapping(path = "/modifyRole")
    public Result<Object> modifyRole(@RequestBody @Valid RoleFormDTO roleFormDTO){
        try {
            roleService.modifyRole(roleFormDTO);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return {@link Result}<{@link Object}>
     */
    @DeleteMapping(path = "/deleteRole")
    public Result<Object> deleteRole(@RequestParam("id") long id){
        try {
            roleService.deleteRole(id);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 分配菜单
     *
     * @param assignMenuDTO 分配菜单dto
     * @return {@link Result}<{@link Object}>
     */
    @PostMapping(path = "/assignMenu")
    public Result<Object> assignMenu(@RequestBody @Valid AssignMenuDTO assignMenuDTO){
        try {
            if(assignMenuDTO.getMenuList()==null || assignMenuDTO.getMenuList().length==0){
                return Result.ok(null);
            }
            List<RoleMenu> roleMenus= Collections.synchronizedList(new ArrayList<>());
            for (long menuId : assignMenuDTO.getMenuList()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setId(SnowId.nextId());
                roleMenu.setRoleId(assignMenuDTO.getRoleid());
                roleMenu.setMenuId(menuId);
                roleMenus.add(roleMenu);
            }
            //调用分配角色业务类
            roleService.assignMenuToRole(roleMenus);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }


    /**
     * 通过角色名称获取角色列表
     *
     * @param roleName 角色名称
     * @param page     页面
     * @param size     大小
     * @return {@link Result}<{@link List}<{@link Role}>>
     */
    @GetMapping(path = "/getRoleListByRoleName")
    public Result<List<Role>> getRoleListByRoleName(@RequestParam("roleName") String roleName,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size){
        page=(page-1)*size;
        try {
            List<Role> roles = roleService.getRoleListByRoleName(roleName, page, size);
            return Result.ok(roles);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 通过角色名称获取角色数量
     *
     * @param roleName 角色名
     * @return {@link Result}<{@link Integer}>
     */
    @GetMapping(path = "/getRoleCountByRoleName")
    public Result<Integer> getRoleCountByRoleName(@RequestParam("roleName") String roleName){
        try {
            int count = roleService.getRoleCountByRoleName(roleName);
            return Result.ok(count);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

}
