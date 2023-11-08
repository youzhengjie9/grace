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
    public List<Role> getRoleList(int page, int size) {
        return roleMapper.getRoleList(page, size);
    }

    @Override
    public int getRoleCount() {
        return roleMapper.getRoleCount();
    }

    @Override
    public List<Role> getAllRole() {
        return roleMapper.getAllRole();
    }

    @Override
    public List<Role> getUserCheckedRoleByUserId(long userid) {
        return roleMapper.getUserCheckedRoleByUserId(userid);
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
    public int modifyRole(RoleFormDTO roleFormDTO) {

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
    public boolean deleteRole(long roleId) {

        try {
            //删除角色
            LambdaQueryWrapper<Role> roleLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roleLambdaQueryWrapper.eq(Role::getId,roleId);
            roleMapper.delete(roleLambdaQueryWrapper);
            //删除角色对应的所有菜单
            roleMapper.deleteRoleAllMenu(roleId);
            return true;
        }catch (Exception e){
            throw new RuntimeException("删除角色失败");
        }
    }

    @Override
    public boolean assignMenuToRole(Long roleId, List<RoleMenu> roleMenuList) {
        try {
            //先删除角色对应的所有菜单
            roleMapper.deleteRoleAllMenu(roleId);
            // 如果需要给角色分配菜单
            if(roleMenuList != null && roleMenuList.size()>0) {
                //再把所有新的菜单（包括以前选过的）都重新添加到数据库中
                roleMapper.addMenuToRole(roleMenuList);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("assignMenuToRole异常,事务已回滚。");
        }
    }

    @Override
    public List<Role> getRoleListByRoleName(String roleName, int page, int size) {
        return roleMapper.getRoleListByRoleName(roleName, page, size);
    }

    @Override
    public int getRoleCountByRoleName(String roleName) {
        return roleMapper.getRoleCountByRoleName(roleName);
    }
}
