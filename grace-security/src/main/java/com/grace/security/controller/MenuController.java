package com.grace.security.controller;

import com.petal.common.base.annotation.OperLog;
import com.petal.common.base.dto.SysMenuDTO;
import com.petal.common.base.entity.SysMenu;
import com.petal.common.base.enums.ResponseType;
import com.petal.common.base.utils.ResponseResult;
import com.petal.common.security.annotation.PermitAll;
import com.petal.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 菜单控制器
 *
 * @author youzhengjie
 * @date 2022/10/13 23:10:31
 */
@RestController
@RequestMapping(path = "/sys/menu")
@Api("菜单接口")
public class MenuController {

    private SysMenuService sysMenuService;

    @Autowired
    public void setSysMenuService(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @PreAuthorize("@pms.hasPermission('sys:menu:list')")
    @OperLog("根据角色id去查询已选择的角色菜单")
    @GetMapping(path = "/selectRoleCheckedMenuByRoleId")
    @ApiOperation("根据角色id去查询已选择的角色菜单")
    public ResponseResult<List<SysMenu>> selectRoleCheckedMenuByRoleId(@RequestParam("id") String id){

        try {
            long roleid = Long.parseLong(id);
            List<SysMenu> sysMenus = sysMenuService.selectRoleCheckedMenuByRoleId(roleid);
            return ResponseResult.build
                    (ResponseType.SUCCESS.getCode(), ResponseType.SUCCESS.getMessage(),sysMenus);
        }catch (Exception e){
            return ResponseResult.build
                    (ResponseType.ERROR.getCode(), ResponseType.ERROR.getMessage(),null);
        }
    }


    /**
     * 添加菜单
     *
     * @param sysMenuDTO 菜单dto
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@pms.hasPermission('sys:menu:list:add')")
    @OperLog("添加菜单")
    @PostMapping("/addMenu")
    @ApiOperation("添加菜单")
    public ResponseResult<Object> addMenu(@RequestBody @Valid SysMenuDTO sysMenuDTO){

        try {
            sysMenuService.addMenu(sysMenuDTO);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),null);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

    /**
     * 修改菜单
     *
     * @param sysMenuDTO 菜单dto
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@pms.hasPermission('sys:menu:list:update')")
    @OperLog("修改菜单")
    @PostMapping("/updateMenu")
    @ApiOperation("修改菜单")
    public ResponseResult<Object> updateMenu(@RequestBody @Valid SysMenuDTO sysMenuDTO){

        try {
            sysMenuService.updateMenu(sysMenuDTO);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),null);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

    /**
     * 删除菜单
     *
     * @param menuid menuid
     * @return {@link ResponseResult}
     */
    @PreAuthorize("@pms.hasPermission('sys:menu:list:delete')")
    @OperLog("删除菜单")
    @DeleteMapping(path = "/deleteMenu")
    @ApiOperation("删除菜单")
    public ResponseResult<Object> deleteMenu(@RequestParam("menuid") long menuid){
        try {
            sysMenuService.deleteMenu(menuid);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),null);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

    /**
     * 通过菜单id查询菜单名称
     *
     * @param menuid menuid
     * @return {@link String}
     */
    @PreAuthorize("@pms.hasPermission('sys:menu:list')")
    @OperLog("通过菜单id查询菜单名称")
    @GetMapping(path = "/selectMenuNameByMenuId")
    @ApiOperation("通过菜单id查询菜单名称")
    public ResponseResult<String> selectMenuNameByMenuId(@RequestParam("menuid") long menuid){
        try {
            //说明是顶层菜单
            String menuName=null;
            if(menuid==0){
                menuName="顶层菜单";
            }else {
                menuName = sysMenuService.selectMenuNameByMenuId(menuid);
            }
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),menuName);
        } catch (Exception e) {
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }
    }

    /**
     * 根据userid获取用户权限。（说白了就是获取sys_menu表中type=1和type=2的perms），这就是我们访问任何接口和菜单的权限
     */
    @PermitAll
    @GetMapping(path = "/getUserPermissionByUserId")
    public ResponseResult<List<String>> getUserPermissionByUserId(@RequestParam("userid") long userid){

        try {
            List<String> permission = sysMenuService.getUserPermissionByUserId(userid);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),permission);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }

    }



}
