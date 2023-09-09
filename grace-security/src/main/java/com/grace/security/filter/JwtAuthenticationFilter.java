package com.grace.security.filter;

import com.grace.security.JwtConstants;
import com.grace.security.service.UserService;
import com.grace.security.token.TokenManagerDelegate;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
 * 作用: 因为SecurityContextHolder维护的上下文（用户登录的信息）是存储在ThreadLocal中的,而ThreadLocal只可以同一个线程去获取,
 * 而用户每一次请求后端接口都是一个新的线程,导致我们即使已经登录过了（SecurityContextHolder.getContext().setAuthentication(authentication)）,
 * 但是只要用户每一次请求后端接口都会导致SecurityContextHolder维护的上下文（用户登录的信息）丢失,这个JwtAuthenticationFilter过滤器
 * 的作用就是用户每一次请求都会优先到达这个过滤器,通过请求中携带的accessToken去accessTokenMap缓存中
 * 获取对应的用户登录信息（Authentication对象）,并重新执行SecurityContextHolder.getContext().setAuthentication(authentication)方法,就可以
 * 告诉SpringSecurity我们已经登录过了,这样就能根据用户已有的权限从而可以访问后端受保护（需要权限）的接口了,
 * 反而如果获取到的Authentication对象为null,说明没有登录成功,这样就会被后面的过滤器（UsernamePasswordAuthenticationFilter）拦截,
 * 从而只能访问配置在SecurityConfig类中的PERMIT_ALL数组中的接口,除了这些接口外,其他接口都无法访问。
 *
 * @author youzhengjie
 * @date 2023/09/07 16:41:03
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

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

    /**
     *
     * 实现过程:
     * (1)获取的accessToken不为空
     *
     * (2)获取的accessToken为空
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 从request中获取accessToken
        String accessToken = getTokenFromRequest(request, JwtConstants.ACCESS_TOKEN);

        // 如果accessToken不为空
        if(StringUtils.isNotBlank(accessToken)){

            // 校验accessToken（例如accessToken格式是否正确、accessToken是否过期等）,异常不需要进行捕获,直接让它抛出即可,
            // 这里抛出的异常会自动进入CustomAuthenticationEntryPoint类或CustomAccessDeniedHandler类中,在这两个类中处理异常
            Claims claims = tokenManagerDelegate.parseAccessToken(accessToken);

            // 校验accessToken成功之后,根据accessToken从accessTokenMap缓存中获取Authentication对象
            Authentication authentication =
                    tokenManagerDelegate.getAuthentication(accessToken, JwtConstants.ACCESS_TOKEN);
            log.info("authentication="+authentication);
            // 将Authentication对象设置到SecurityContextHolder的上下文中,
            // 告诉spring security我们已经成功登录了。（前提是获取到的authentication对象不为null）
            // 这里注意: 如果获取到的authentication对象为null,则说明用户没有登录,同时如果访问的接口不在SecurityConfig类中的
            // PERMIT_ALL数组中的话,则会被UsernamePasswordAuthenticationFilter过滤器拦截,并提示用户未登录
            // ,所以不需要对authentication对象是否为null进行判断。
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        filterChain.doFilter(request,response);

//        /*
//        如果accessToken为空:
//          (场景1) 所访问的接口配置在SecurityConfig类中的PERMIT_ALL数组中（允许所有人访问）
//            (1.1) 后面的过滤器将不再拦截,直接可以访问到此接口（例如登录接口会走这步）。
//          (场景2) 所访问的接口没有配置在SecurityConfig类中的PERMIT_ALL数组中（必须要有accessToken才能访问,也就是此接口为受保护的接口）
//            (2.2) 将会被后面的过滤器拦截,例如会经过UsernamePasswordAuthenticationFilter之类过滤器进行帐号密码
//                  的校验,如果校验失败则无法访问到此接口
//         */
//        if(StringUtils.isBlank(accessToken)){
//            filterChain.doFilter(request,response);
//            return;
//        }
//        // 如果accessToken不为空
//
//        // 通过token去缓存中获取Authentication对象（UsernamePasswordAuthenticationToken对象）
//
//        SecurityContextHolder.getContext().setAuthentication();


    }

    /**
     * 从request中获取token
     *
     * @param request
     * @return {@link String}
     */
    private String getTokenFromRequest(HttpServletRequest request,String tokenType) {

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
