package com.grace.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.security.dto.RoleFormDTO;
import com.grace.security.entity.Role;
import com.grace.security.entity.RoleMenu;
import com.grace.security.mapper.RoleMapper;
import com.grace.security.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 角色服务impl
 *
 * @author youzhengjie
 * @date 2023-11-06 19:54:03
 */
@Service
@Transactional //开启事务
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectAllRoleByLimit(int page, int size) {
        return roleMapper.selectAllRoleByLimit(page, size);
    }

    @Override
    public int selectAllRoleCount() {
        return roleMapper.selectAllRoleCount();
    }

    @Override
    public List<Role> selectAllRole() {
        return roleMapper.selectAllRole();
    }

    @Override
    public List<Role> selectUserCheckedRoleByUserId(long userid) {
        return roleMapper.selectUserCheckedRoleByUserId(userid);
    }

    @Override
    public int addRole(RoleFormDTO roleFormDTO) {
        Role role = new Role();
        BeanUtils.copyProperties(roleFormDTO, role);
        if(roleFormDTO.getStatus()){
            role.setStatus(0);
        }else {
            role.setStatus(1);
        }
        //然后再补充一些前端没有传过来的属性
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        // 只有调用mybatis-plus内置的sql方法才会自动生成分布式主键id
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(RoleFormDTO roleFormDTO) {

        Role role = new Role();
        BeanUtils.copyProperties(roleFormDTO, role);
        if(roleFormDTO.getStatus()){
            role.setStatus(0);
        }else {
            role.setStatus(1);
        }
        // 然后再补充一些前端没有传过来的属性
        role.setUpdateTime(LocalDateTime.now());

        LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleLambdaQueryWrapper.eq(Role::getId, role.getId());
        return roleMapper.update(role,roleLambdaQueryWrapper);
    }

    @Override
    public boolean deleteRole(long id) {

        try {
            //删除角色
            LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleLambdaQueryWrapper.eq(Role::getId,id);
            roleMapper.delete(roleLambdaQueryWrapper);
            //删除角色对应的所有菜单
            roleMapper.deleteRoleAllMenu(id);
            return true;
        }catch (Exception e){
            throw new RuntimeException("删除角色失败");
        }
    }

    @Override
    public boolean assignMenuToRole(List<RoleMenu> roleMenuList) {
        try {
            //先删除角色对应的所有菜单
            roleMapper.deleteRoleAllMenu(roleMenuList.get(0).getRoleId());
            //再把所有新的菜单（包括以前选过的）都重新添加到数据库中
            roleMapper.addMenuToRole(roleMenuList);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("assignMenuToRole异常,事务已回滚。");
        }
    }

    @Override
    public List<Role> searchRoleByRoleNameAndLimit(String roleName, int page, int size) {
        return roleMapper.searchRoleByRoleNameAndLimit(roleName, page, size);
    }

    @Override
    public int searchRoleCountByRoleName(String roleName) {
        return roleMapper.searchRoleCountByRoleName(roleName);
    }
}
