package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.petal.common.base.annotation.OperLog;
import com.petal.common.base.dto.SysAssignMenuDTO;
import com.petal.common.base.dto.SysRoleFormDTO;
import com.petal.common.base.entity.SysRole;
import com.petal.common.base.entity.SysRoleMenu;
import com.petal.common.base.enums.ResponseType;
import com.petal.common.base.utils.ResponseResult;
import com.petal.common.base.utils.SnowId;
import com.petal.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 角色接口
 *
 * @author youzhengjie
 * @date 2023/11/05 18:17:39
 */
@RestController
@RequestMapping(path = ParentMappingConstants.ROLE_CONTROLLER)
public class RoleController {

    private SysRoleService sysRoleService;

    @Autowired
    public void setSysRoleService(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     *
     * @param page
     * @param size
     * @return
     */
    //1 8 = 0 8
    //2 8 = 8 8
    //3 8 = 16 8
    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    @OperLog("查询所有角色并分页")
    @GetMapping(path = "/selectAllRoleByLimit")
    @ApiOperation("查询所有角色并分页")
    public ResponseResult<List<SysRole>> selectAllRoleByLimit(int page, int size){
        page=(page-1)*size;
        try {
            List<SysRole> sysRoles = sysRoleService.selectAllRoleByLimit(page, size);
            return ResponseResult.build
                    (ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMessage(),sysRoles);
        }catch (Exception e){
            return ResponseResult.build
                    (ResponseType.ERROR.getCode(), ResponseType.ERROR.getMessage(),null);
        }
    }

    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    @OperLog("查询所有角色数量")
    @GetMapping(path = "/selectAllRoleCount")
    @ApiOperation("查询所有角色数量")
    public ResponseResult<Integer> selectAllRoleCount(){

        try {
            int count = sysRoleService.selectAllRoleCount();
            return ResponseResult.build
                    (ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMessage(),count);
        }catch (Exception e){
            return ResponseResult.build
                    (ResponseType.ERROR.getCode(), ResponseType.ERROR.getMessage(),null);
        }

    }

    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    @OperLog("查询所有角色")
    @GetMapping(path = "/selectAllRole")
    @ApiOperation("查询所有角色")
    public ResponseResult<List<SysRole>> selectAllRole(){
        try {
            List<SysRole> sysRoles = sysRoleService.selectAllRole();
            return ResponseResult.build
                    (ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMessage(),sysRoles);
        }catch (Exception e){
            return ResponseResult.build
                    (ResponseType.ERROR.getCode(), ResponseType.ERROR.getMessage(),null);
        }
    }

    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    @OperLog("根据用户id查询用户已经选择的角色")
    @GetMapping(path = "/selectUserCheckedRoleByUserId")
    @ApiOperation("根据用户id查询用户已经选择的角色")
    public ResponseResult<List<SysRole>> selectUserCheckedRoleByUserId(@RequestParam("id") String id){
        try {
            long userid = Long.parseLong(id);
            List<SysRole> sysRoles = sysRoleService.selectUserCheckedRoleByUserId(userid);
            return ResponseResult.build
                    (ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMessage(),sysRoles);
        }catch (Exception e){
            return ResponseResult.build
                    (ResponseType.ERROR.getCode(), ResponseType.ERROR.getMessage(),null);
        }
    }

    @PreAuthorize("@pms.hasPermission('sys:role:list:add')")
    @OperLog("添加角色")
    @PostMapping("/addRole")
    @ApiOperation("添加角色")
    public ResponseResult<Object> addRole(@RequestBody @Valid SysRoleFormDTO sysRoleFormDTO){
        try {
            sysRoleService.addRole(sysRoleFormDTO);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),null);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

    @PreAuthorize("@pms.hasPermission('sys:role:list:update')")
    @OperLog("修改角色")
    @PostMapping(path = "/updateRole")
    @ApiOperation("修改角色")
    public ResponseResult<Object> updateRole(@RequestBody @Valid SysRoleFormDTO sysRoleFormDTO){

        try {
            sysRoleService.updateRole(sysRoleFormDTO);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),null);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

    /**
     * 删除角色
     *
     * @param id id
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@pms.hasPermission('sys:role:list:delete')")
    @OperLog("删除角色")
    @DeleteMapping(path = "/deleteRole")
    @ApiOperation("删除角色")
    public ResponseResult<Object> deleteRole(@RequestParam("id") long id){
        try {
            sysRoleService.deleteRole(id);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),null);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }

    }

    /**
     * 分配菜单
     *
     * @param sysAssignMenuDTO 分配菜单dto
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@pms.hasPermission('sys:role:list:assign-menu')")
    @OperLog("分配菜单")
    @PostMapping(path = "/assignMenu")
    @ApiOperation("分配菜单")
    public ResponseResult<Object> assignMenu(@RequestBody @Valid SysAssignMenuDTO sysAssignMenuDTO){
        try {
            if(sysAssignMenuDTO.getMenuList()==null || sysAssignMenuDTO.getMenuList().length==0){
                return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                        ResponseType.SUCCESS.getMessage(),null);
            }
            List<SysRoleMenu> sysRoleMenus=new CopyOnWriteArrayList<>();

            for (long menuId : sysAssignMenuDTO.getMenuList()) {
                SysRoleMenu sysRoleMenu = SysRoleMenu
                        .builder()
                        //手动使用雪花算法生成分布式id
                        .id(SnowId.nextId())
                        .roleId(sysAssignMenuDTO.getRoleid())
                        .menuId(menuId)
                        .build();
                sysRoleMenus.add(sysRoleMenu);
            }
            //调用分配角色业务类
            sysRoleService.assignMenuToRole(sysRoleMenus);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),null);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }

    }


    /**
     * mysql通过role的name关键字搜索
     *
     * @param roleName 角色名
     * @param page     页面
     * @param size     大小
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    @OperLog("根据角色名搜索角色并分页")
    @GetMapping(path = "/searchRoleByRoleNameAndLimit")
    @ApiOperation("根据角色名搜索角色并分页")
    public ResponseResult<List<SysRole>> searchRoleByRoleNameAndLimit(@RequestParam("roleName") String roleName,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("size") int size){
        page=(page-1)*size;
        try {
            List<SysRole> sysRoles = sysRoleService.searchRoleByRoleNameAndLimit(roleName, page, size);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),sysRoles);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

    /**
     * 按role的name搜索role数量
     *
     * @param roleName 角色名
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@pms.hasPermission('sys:role:list')")
    @OperLog("按role的name搜索role数量")
    @GetMapping(path = "/searchRoleCountByRoleName")
    @ApiOperation("按role的name搜索role数量")
    public ResponseResult<Integer> searchRoleCountByRoleName(@RequestParam("roleName") String roleName){
        try {
            int count = sysRoleService.searchRoleCountByRoleName(roleName);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),count);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

}
