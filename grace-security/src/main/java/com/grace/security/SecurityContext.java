package com.grace.security;

import com.grace.security.users.GraceUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 封装SpringSecurity上下文
 *
 * @author youzhengjie
 * @date 2023/08/27 17:07:55
 */
public class SecurityContext {

    /**
     * 禁止用new创建SecurityContext对象
     */
    private SecurityContext(){
    }

    /**
     * 获取当前UserDetails对象
     *
     * @return {@link GraceUser}
     */
    public static GraceUser getCurrentUserDetails(){
        return (GraceUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }


    /**
     * 获得当前GraceUserDetails中的SysUser对象
     *
     * @return {@link SysUser}
     */
    public static SysUser getCurrentSysUser(){
        return SecurityContext.getCurrentUserDetails().getSysUser();
    }

    /**
     * 获得当前用户id
     *
     * @return {@link Long}
     */
    public static Long getCurrentUserId(){
        return SecurityContext.getCurrentSysUser().getId();
    }

    /**
     * 获得当前用户名
     *
     * @return {@link String}
     */
    public static String getCurrentUserName(){
        return SecurityContext.getCurrentSysUser().getUserName();
    }

}
