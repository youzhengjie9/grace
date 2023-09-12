package com.grace.security.filter;

import com.grace.security.JwtConstants;
import com.grace.security.service.UserService;
import com.grace.security.token.TokenManagerDelegate;
import io.jsonwebtoken.Claims;
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
 * <p>
 * 作用: 因为SecurityContextHolder维护的上下文（用户登录的信息）是存储在ThreadLocal中的,而ThreadLocal只可以同一个线程去获取,
 * 而用户每一次请求后端接口都是一个新的线程,导致我们即使已经登录过了（SecurityContextHolder.getContext().setAuthentication(authentication)）,
 * 但是只要用户每一次请求后端接口都会导致SecurityContextHolder维护的上下文（用户登录的信息）丢失,这个JwtAuthenticationFilter过滤器
 * 的作用就是用户每一次请求都会优先到达这个过滤器,通过请求中携带的accessToken去accessTokenMap缓存中
 * 获取对应的用户登录信息（Authentication对象）,并重新执行SecurityContextHolder.getContext().setAuthentication(authentication)方法,就可以
 * 告诉SpringSecurity我们已经登录过了,这样就能根据用户已有的权限从而可以访问后端受保护（需要权限）的接口了,
 * 反而如果获取到的Authentication对象为null,说明没有登录成功,这样就会被后面的过滤器（UsernamePasswordAuthenticationFilter）拦截,
 * 从而只能访问配置在SecurityConfig类中的PERMIT_ALL数组中的接口,除了这些接口外,其他接口都无法访问。
 * <p>
 * 简而言之,JwtAuthenticationFilter类的作用就是把所有携带了accessToken的请求,通过这个accessToken获取到Authentication对象,
 * 并把这个Authentication对象设置进SecurityContextHolder的上下文（context）中。
 *
 * @author youzhengjie
 * @date 2023/09/07 16:41:03
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private TokenManagerDelegate tokenManagerDelegate;

    @Autowired
    private UserService userService;

    /**
     * 实现过程（逻辑）: <br/>
     * (1)如果请求中携带了accessToken: <br/>
     * &emsp;(1.1) 校验accessToken,如果accessToken过期了或者格式不正确: <br/>
     * &emsp;&emsp;(1.1.1) 会自动抛出异常,并进入CustomAuthenticationEntryPoint类或CustomAccessDeniedHandler类中。<br/>
     * &emsp;(1.2) 校验accessToken,如果accessToken没有过期并且格式正确: <br/>
     * &emsp;&emsp;(1.2.1) 根据accessToken从accessTokenMap缓存中获取Authentication对象,并将Authentication对象设置
     * 到SecurityContextHolder的上下文（context）中,此时就算登录成功了,然后
     * 调用filterChain.doFilter(request,response)方法进行过滤器放行,便可以访问目标接口了（前提是有对应的权限）。<br/>
     * (2)如果请求中没有携带accessToken: <br/>
     * &emsp;(2.1) 直接放行。<br/>
     * &emsp;&emsp;(2.1.1) 如果所访问的接口配置在SecurityConfig类中的PERMIT_ALL数组中,则可以访问目标接口。<br/>
     * &emsp;&emsp;(2.1.2) 如果所访问的接口没有配置在SecurityConfig类中的PERMIT_ALL数组中,则会被后面的
     * 过滤器（UsernamePasswordAuthenticationFilter）所拦截,无法访问目标接口。<br/>
     *
     * @param request     request
     * @param response    response
     * @param filterChain filterChain
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
            // 将Authentication对象设置到SecurityContextHolder的上下文（context）中,
            // 告诉spring security我们已经成功登录了。（前提是获取到的authentication对象不为null）
            // 这里注意: 如果获取到的authentication对象为null,则说明用户没有登录,同时如果访问的接口不在SecurityConfig类中的
            // PERMIT_ALL数组中的话,则会被UsernamePasswordAuthenticationFilter过滤器拦截,并提示用户未登录
            // ,所以不需要对authentication对象是否为null进行判断。
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        // 不管accessToken是否为空过滤器都放行
        filterChain.doFilter(request,response);


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
