package com.grace.security.autoconfigure;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Import({
//        TokenManagerDelegate.class,
//        JwtTokenManager.class,
//        CachedJwtTokenManager.class,
//        UserServiceImpl.class,
//        SecurityConfig.class,
//        CustomAuthenticationEntryPoint.class,
//        CustomAccessDeniedHandler.class,
//        JwtAuthenticationFilter.class,
//        UserServiceImpl.class,
//        UserController.class
//})
@ComponentScan(basePackages = "com.grace.security")
public class GraceSecurityAutoConfigure {


}
