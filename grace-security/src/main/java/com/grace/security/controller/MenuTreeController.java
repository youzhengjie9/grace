package com.grace.security.controller;

import com.petal.common.base.annotation.OperLog;
import com.petal.common.base.enums.ResponseType;
import com.petal.common.base.utils.ResponseResult;
import com.petal.system.service.SysMenuTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单树控制器
 *
 * @author youzhengjie
 * @date 2022/10/17 21:10:14
 */
@RestController
@RequestMapping(path = "/sys/menutree")
@Api("菜单树接口")
public class MenuTreeController {

    private SysMenuTreeService sysMenuTreeService;

    @Autowired
    public void setSysMenuTreeService(SysMenuTreeService sysMenuTreeService) {
        this.sysMenuTreeService = sysMenuTreeService;
    }

    /**
     * 根据用户的userid来构建前端的后台管理系统侧边栏菜单
     * @return 菜单的json串
     */
    @OperLog("根据用户的userid来构建前端的后台管理系统侧边栏菜单")
    @GetMapping(path = "/buildTreeByUserId")
    @ApiOperation("根据用户的userid来构建前端的后台管理系统侧边栏菜单")
    public ResponseResult<String> buildTreeByUserId(@RequestParam("userid") String userid){
        try {
            String menuTree = sysMenuTreeService.buildTreeByUserId(Long.parseLong(userid));
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),menuTree);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }

    }

    /**
     * 将系统所有菜单权限构建成一棵树（应用于菜单管理表格数据）
     * @return
     */
    @OperLog("将系统所有菜单权限构建成一棵树")
    @GetMapping(path = "/buildAllMenuPermissionTree")
    @ApiOperation("将系统所有菜单权限构建成一棵树")
    public ResponseResult<String> buildAllMenuPermissionTree(){
        try {
            String menuTree = sysMenuTreeService.buildAllMenuPermissionTree();
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),menuTree);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }

    }

    /**
     * 构建分配菜单的树（和上面buildAllMenuPermissionTree方法区别仅仅是这个方法只展示部分需要的字段、而buildAllMenuPermissionTree方法展示所有字段）
     *
     * @return {@link String}
     */
    @OperLog("构建分配菜单的树")
    @GetMapping(path = "/buildAssignMenuTree")
    @ApiOperation("构建分配菜单的树")
    public ResponseResult<String> buildAssignMenuTree(){
        try {
            String menuTree = sysMenuTreeService.buildAssignMenuTree();
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),menuTree);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }

    }

    /**
     * 根据新增的菜单的菜单类型来构建可以选择的菜单树（应用于菜单管理中的所属菜单上）
     * <p>
     * 如果新增的是目录type=0（可以选择的所属菜单有）：顶层目录（也就是第一层目录）、其他目录
     * 如果新增的是菜单type=1（可以选择的所属菜单有）：顶层目录（也就是第一层目录）、其他目录
     * 如果新增的是按钮type=2（可以选择的所属菜单有）：菜单
     *
     */
    @OperLog("根据新增的菜单的菜单类型来构建可以选择的菜单树")
    @GetMapping(path = "/buildCanChooseMenuTreeByNewMenuType")
    @ApiOperation("根据新增的菜单的菜单类型来构建可以选择的菜单树")
    public ResponseResult<String> buildCanChooseMenuTreeByNewMenuType(@RequestParam("type") int type){
        try {
            String menuTree = sysMenuTreeService.buildCanChooseMenuTreeByNewMenuType(type);
            return ResponseResult.build(ResponseType.SUCCESS.getCode(),
                    ResponseType.SUCCESS.getMessage(),menuTree);
        }catch (Exception e){
            return ResponseResult.build(ResponseType.ERROR.getCode(),
                    ResponseType.ERROR.getMessage(),null);
        }

    }

}
