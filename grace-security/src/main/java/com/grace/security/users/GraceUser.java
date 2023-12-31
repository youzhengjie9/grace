package com.grace.security.users;

import com.grace.security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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

    private final List<String> permissons;

    public GraceUser(User user, List<String> permissons) {
        this.user=user;
        this.permissons = permissons;
    }
    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissons
                .stream()
                .distinct()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        // 这个是存储在数据库并且使用BCrypt算法加密过的密码。
        // DaoAuthenticationProvider.additionalAuthenticationChecks方法需要通过这个密码和前端传来的密码进行比对
        return user.getPassword();
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
        return user.getUsername();
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

        return user.getStatus()==0;
    }
}
