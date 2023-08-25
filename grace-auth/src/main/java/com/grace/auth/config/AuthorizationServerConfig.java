package com.grace.auth.config;

import com.petal.auth.support.CustomeOAuth2AccessTokenGenerator;
import com.petal.auth.support.core.CustomDaoAuthenticationProvider;
import com.petal.auth.support.core.CustomeOAuth2TokenCustomizer;
import com.petal.auth.support.core.FormIdentityLoginConfigurer;
import com.petal.auth.support.handler.CustomAuthenticationFailureHandler;
import com.petal.auth.support.handler.CustomAuthenticationSuccessHandler;
import com.petal.auth.support.password.PasswordAuthenticationConverter;
import com.petal.auth.support.password.PasswordAuthenticationProvider;
import com.petal.auth.support.sms.SmsAuthenticationConverter;
import com.petal.auth.support.sms.SmsAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.*;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.util.Arrays;

/**
 * Spring Authorization Server认证服务器配置（帐号密码、手机号登录）
 *
 * @author youzhengjie
 * @date 2023-05-05 09:31:53
 */
@Configuration
public class AuthorizationServerConfig {

    private OAuth2AuthorizationService authorizationService;

    /**
     * 自定义oauth2授权页面跳转接口（也可以使用oauth2自带的）
     */
    private static final String CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent";

    /**
     * 认证服务器的地址
     */
    private static final String ISSUER = "http://petal.oauth2.com:5020";

    @Autowired
    public void setAuthorizationService(OAuth2AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    /**
     * Spring Authorization Server 授权配置
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {

        // 应用OAuth2默认配置
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        // 获取OAuth2AuthorizationServerConfigurer对象
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = http
                .getConfigurer(OAuth2AuthorizationServerConfigurer.class);

        authorizationServerConfigurer
                // 自定义认证授权端点
                .tokenEndpoint((tokenEndpoint) -> {
                    // 注入自定义的授权认证Converter
                    tokenEndpoint.accessTokenRequestConverter(accessTokenRequestConverter())
                            // 自定义登录成功处理器
                            .accessTokenResponseHandler(new CustomAuthenticationSuccessHandler())
                            // 自定义登录失败处理器
                            .errorResponseHandler(new CustomAuthenticationFailureHandler());
                })
                // 自定义客户端认证
                .clientAuthentication(oAuth2ClientAuthenticationConfigurer ->
                        oAuth2ClientAuthenticationConfigurer
                                // 自定义登录失败处理器
                                .errorResponseHandler(new CustomAuthenticationFailureHandler()))
                .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                        // 自定义授权页
                        .consentPage(CUSTOM_CONSENT_PAGE_URI));

        DefaultSecurityFilterChain securityFilterChain = authorizationServerConfigurer
                // redis存储token的实现
                .authorizationService(authorizationService)
                .authorizationServerSettings(
                        AuthorizationServerSettings.builder().issuer(ISSUER).build())
                .and()
                // 配置表单个性化登录配置
                .apply(new FormIdentityLoginConfigurer())
                .and()
                .build();
        // 注入自定义授权模式实现
        addCustomOAuth2GrantAuthenticationProvider(http);
        return securityFilterChain;
    }

    /**
     * 令牌生成规则实现
     * </br>
     * client:username:uuid
     * @return OAuth2TokenGenerator
     */
    @Bean
    public OAuth2TokenGenerator oAuth2TokenGenerator() {
        // 自定义Oauth2的accessToken生成规则对象
        CustomeOAuth2AccessTokenGenerator accessTokenGenerator = new CustomeOAuth2AccessTokenGenerator();
        // 设置Token增加关联用户信息
        accessTokenGenerator.setAccessTokenCustomizer(new CustomeOAuth2TokenCustomizer());
        return new DelegatingOAuth2TokenGenerator(accessTokenGenerator, new OAuth2RefreshTokenGenerator());
    }

    /**
     * 配置各种请求转换器
     * @return DelegatingAuthenticationConverter
     */
    private AuthenticationConverter accessTokenRequestConverter() {
        // DelegatingAuthenticationConverter 在解析请求时会遍历 AuthenticationConverter 列表，
        // 当某个 AuthenticationConverter 解析成功时，立即返回，这也能确定此请求是什么认证方式，后续再执行对应的认证逻辑
        return new DelegatingAuthenticationConverter(Arrays.asList(
                // 密码登录转换器
                new PasswordAuthenticationConverter(),
                // 手机验证码登录转换器
                new SmsAuthenticationConverter(),
                // 将 HttpServletRequest中提取的OAuth2的refreshToken授予的访问令牌请求 ===> OAuth2RefreshTokenAuthenticationToken
                new OAuth2RefreshTokenAuthenticationConverter(),
                // 将 HttpServletRequest中提取的OAuth2的客户端凭据授予的访问令牌请求 ===>  OAuth2RefreshTokenAuthenticationToken
                new OAuth2ClientCredentialsAuthenticationConverter(),
                // 将 HttpServletRequest中提取OAuth2的授权码授予的访问令牌请求 ===> OAuth2AuthorizationCodeAuthenticationToken
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                // 将 HttpServletRequest中提取OAuth2的授权码授予的授权请求 ===> OAuth2AuthorizationCodeRequestAuthenticationToken
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }

    /**
     * 注入授权模式实现提供方
     * <p>
     * 1. 密码登录模式 </br>
     * 2. 手机短信登录模式 </br>
     */
    @SuppressWarnings("unchecked")
    private void addCustomOAuth2GrantAuthenticationProvider(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);

        PasswordAuthenticationProvider passwordAuthenticationProvider = new PasswordAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator());

        SmsAuthenticationProvider smsAuthenticationProvider = new SmsAuthenticationProvider(
                authenticationManager, authorizationService, oAuth2TokenGenerator());

        // 处理 UsernamePasswordAuthenticationToken
        http.authenticationProvider(new CustomDaoAuthenticationProvider());
        // 处理 PasswordAuthenticationToken
        http.authenticationProvider(passwordAuthenticationProvider);
        // 处理 SmsAuthenticationToken
        http.authenticationProvider(smsAuthenticationProvider);
    }


}
