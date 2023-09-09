package com.grace.security.filter;

import com.grace.security.JwtConstants;
import com.grace.security.service.UserService;
import com.grace.security.token.TokenManagerDelegate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤器
 *
 * @author youzhengjie
 * @date 2023/09/07 16:41:03
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private TokenManagerDelegate tokenManagerDelegate;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTokenManagerDelegate(TokenManagerDelegate tokenManagerDelegate) {
        this.tokenManagerDelegate = tokenManagerDelegate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = resolveToken(request, JwtConstants.ACCESS_TOKEN);

        /*
        如果accessToken为空:
          (场景1) 所访问的接口配置在permitAll数组中（允许所有人访问）
            (1.1) 后面的过滤器将不再拦截,直接可以访问到此接口（例如登录接口会走这步）。
          (场景2) 所访问的接口没有配置在permitAll数组中（必须要有accessToken才能访问,也就是此接口为受保护的接口）
            (2.2) 将会被后面的过滤器拦截,例如会经过UsernamePasswordAuthenticationToken之类过滤器进行帐号密码
                  的校验,如果校验失败则无法访问到此接口
         */
        if(StringUtils.isBlank(accessToken)){
            filterChain.doFilter(request,response);
            return;
        }
        // 如果accessToken不为空

        // 通过token去缓存中获取Authentication对象（UsernamePasswordAuthenticationToken对象）

        SecurityContextHolder.getContext().setAuthentication();


    }

    /**
     * 解析token
     *
     * @param request
     * @return {@link String}
     */
    private String resolveToken(HttpServletRequest request,String tokenType) {

        if(tokenType.equalsIgnoreCase("accessToken")){
            // 从请求头中拿到accessToken
            String accessToken = request.getHeader(JwtConstants.ACCESS_TOKEN);
            // 如果accessToken不为空,则直接返回
            if(StringUtils.isNotBlank(accessToken)){
                return accessToken;
            }

            // 如果请求头没有accessToken,则去请求参数中获取accessToken
            accessToken = request.getParameter(JwtConstants.ACCESS_TOKEN);
            if(StringUtils.isNotBlank(accessToken)) {
                return accessToken;
            }
        } else if (tokenType.equalsIgnoreCase("refreshToken")) {
            // 从请求头中拿到refreshToken
            String refreshToken = request.getHeader(JwtConstants.REFRESH_TOKEN);
            // 如果refreshToken不为空,则直接返回
            if(StringUtils.isNotBlank(refreshToken)){
                return refreshToken;
            }

            // 如果请求头没有refreshToken,则去请求参数中获取refreshToken
            refreshToken = request.getParameter(JwtConstants.REFRESH_TOKEN);
            if(StringUtils.isNotBlank(refreshToken)){
                return refreshToken;
            }
        }
        return null;
    }


}
