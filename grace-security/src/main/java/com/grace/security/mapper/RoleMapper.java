package com.grace.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grace.security.entity.Role;
import com.grace.security.entity.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色映射器
 *
 * @author youzhengjie
 * @date 2023-11-05 18:20:31
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 查询所有角色并分页（并对分页功能进行性能调优）
     */
    List<Role> getRoleList(@Param("page") int page, @Param("size") int size);

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
    List<Role> getUserCheckedRoleByUserId(@Param("userid") long userid);


    /**
     * 删除角色所有菜单
     *
     * @param roleId 角色id
     * @return int
     */
    int deleteRoleAllMenu(@Param("roleId") Long roleId);

    /**
     * 给角色添加所有菜单
     *
     * @param roleMenuList 角色菜单列表
     * @return int
     */
    int addMenuToRole(@Param("roleMenuList") List<RoleMenu> roleMenuList);

    /**
     * 通过角色名称获取角色列表
     *
     * @param roleName 角色名
     * @param page     页面
     * @param size     大小
     * @return {@link List}<{@link Role}>
     */
    List<Role> getRoleListByRoleName(@Param("roleName") String roleName,
                                     @Param("page") int page,
                                     @Param("size") int size);

    /**
     * 通过角色名称获取角色数量
     *
     * @param roleName 角色名
     * @return int
     */
    int getRoleCountByRoleName(@Param("roleName") String roleName);

}
