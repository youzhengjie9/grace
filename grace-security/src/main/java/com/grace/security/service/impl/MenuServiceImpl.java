package com.grace.security.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.security.entity.Menu;
import com.grace.security.mapper.MenuMapper;
import com.grace.security.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * menu service impl
 *
 * @author youzhengjie
 * @date 2023/09/08 22:34:07
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private MenuMapper menuMapper;

    @Autowired
    public void setMenuMapper(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<String> getAllPermissionsByUserId(long userId) {
        return menuMapper.getAllPermissionsByUserId(userId);
    }
}
