package com.grace.security.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.security.dto.RoleFormDTO;
import com.grace.security.entity.Role;
import com.grace.security.entity.RoleMenu;

import java.util.List;

/**
 * 角色服务
 *
 * @author youzhengjie
 * @date 2023-11-06 19:53:39
 */
public interface RoleService extends IService<Role> {

    /**
     * 查询所有角色并分页（并对分页功能进行性能调优）
     */
    List<Role> getRoleList(int page, int size);

    /**
     * 查询总角色数
     */
    int getRoleCount();

    /**
     * 查询所有角色
     */
    List<Role> getAllRole();

    /**
     * 通过userid来查询指定用户当前所拥有的role角色列表
     *
     * @param userid 用户标识
     * @return {@link List}<{@link Role}>
     */
    List<Role> getUserCheckedRoleByUserId(long userid);

    /**
     * 添加角色
     *
     * @param roleFormDTO 角色表单dto
     * @return int
     */
    int addRole(RoleFormDTO roleFormDTO);

    /**
     * 更新角色
     *
     * @param roleFormDTO 角色表单dto
     * @return int
     */
    int modifyRole(RoleFormDTO roleFormDTO);


    /**
     * 删除角色
     *
     * @param roleId roleId
     * @return int
     */
    boolean deleteRole(long roleId);

    /**
     * 给角色分配菜单权限
     *
     * @param roleId 角色id
     * @param roleMenuList 角色菜单列表
     * @return boolean
     */
    boolean assignMenuToRole(Long roleId,List<RoleMenu> roleMenuList);

    /**
     * 通过角色名称获取角色列表
     *
     * @param roleName 角色名
     * @param page     页面
     * @param size     大小
     * @return {@link List}<{@link Role}>
     */
    List<Role> getRoleListByRoleName(String roleName, int page, int size);

    /**
     * 通过角色名称获取角色数量
     *
     * @param roleName 角色名
     * @return int
     */
    int getRoleCountByRoleName(String roleName);
}
