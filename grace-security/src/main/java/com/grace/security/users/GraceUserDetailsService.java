package com.grace.security.users;

import com.grace.security.entity.User;
import com.grace.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 自定义UserDetailsService实现类
 *
 * @author youzhengjie
 * @date 2023/10/30 23:32:20
 */
@Service
public class GraceUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 根据用户名去查询用户
        User user = userMapper.getUserByUserName(username);

        // 如果用户不存在或者用户的状态（status）是停用中
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        // 如果用户存在,则返回这个自定义UserDetails对象（用于和前端传来的账号密码进行比对）
        return new GraceUser(user);
    }
}
