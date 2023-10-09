package com.grace.security.users;

import com.alibaba.fastjson2.annotation.JSONField;
import com.grace.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 自定义spring security所需要的UserDetails实现类
 *
 * @author youzhengjie
 * @date 2023/08/27 15:39:54
 */
public class GraceUser implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    private final User user;

    /**
     * 权限列表。
     */
    private final List<String> permissons;


    /**
     * 框架所需要的权限集合
     */
    @JSONField(serialize = false) //禁止序列化该属性
    private Set<SimpleGrantedAuthority> grantedAuthoritySet;

    public GraceUser(User user, List<String> permissons) {
        this.user=user;
        //loadUserByUsername方法中从数据库查询出来的权限列表
        this.permissons=permissons;
    }
    public User getUser() {
        return user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //如果grantedAuthoritySet为null才去生成GrantedAuthority类型的权限集合
        if(Objects.isNull(grantedAuthoritySet)){
            this.grantedAuthoritySet=permissons
                    .stream()
                    .distinct()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
            return grantedAuthoritySet;
        }
        return grantedAuthoritySet;
    }

    @Override
    public String getPassword() {
        // 密码在这里没有作用,返回空即可
        return "";
    }

    /**
     * 获取用户id
     *
     * @return {@link Long}
     */
    public Long getUserId(){
        return user.getId();
    }

    /**
     * 获取用户名
     *
     * @return {@link String}
     */
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        return ((user.getDelFlag())==0 && user.getStatus()==0);
    }
}
