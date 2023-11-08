package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.utils.PageData;
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
    public Result<PageData<Role>> getRoleList(int page, int size){
        page=(page-1)*size;
        try {
            PageData<Role> pageData = new PageData<>();
            List<Role> roles = roleService.getRoleList(page, size);
            int count = roleService.getRoleCount();
            pageData.setPagedList(roles);
            pageData.setTotalCount(count);
            return Result.ok(pageData);
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
    public Result<PageData<Role>> getRoleListByRoleName(@RequestParam("roleName") String roleName,
                                                        @RequestParam("page") int page,
                                                        @RequestParam("size") int size){
        page=(page-1)*size;
        try {
            PageData<Role> pageData = new PageData<>();
            List<Role> roles = roleService.getRoleListByRoleName(roleName, page, size);
            int count = roleService.getRoleCountByRoleName(roleName);
            pageData.setPagedList(roles);
            pageData.setTotalCount(count);
            return Result.ok(pageData);
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
            // 如果修改的是“超级管理员”角色
            if(roleFormDTO.getId() == 2001L){
                return Result.fail(null);
            }
            roleService.modifyRole(roleFormDTO);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 删除角色
     *
     * @param roleId roleId
     * @return {@link Result}<{@link Object}>
     */
    @DeleteMapping(path = "/deleteRole")
    public Result<Object> deleteRole(@RequestParam("roleId") long roleId){
        try {
            // 如果删除的是“超级管理员”角色
            if(roleId == 2001L){
                return Result.fail(null);
            }
            roleService.deleteRole(roleId);
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
            long roleId = assignMenuDTO.getRoleId();
            List<RoleMenu> roleMenus= Collections.synchronizedList(new ArrayList<>());
            for (long menuId : assignMenuDTO.getMenuList()) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setId(SnowId.nextId());
                roleMenu.setRoleId(assignMenuDTO.getRoleId());
                roleMenu.setMenuId(menuId);
                roleMenus.add(roleMenu);
            }
            //调用分配角色业务类
            roleService.assignMenuToRole(roleId,roleMenus);
            return Result.ok(null);
        }catch (Exception e){
            return Result.fail(null);
        }
    }


}
