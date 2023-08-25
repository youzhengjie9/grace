package com.grace.auth.support.core;

import com.petal.auth.support.handler.CustomFormAuthenticationFailureHandler;
import com.petal.auth.support.handler.CustomSsoLogoutSuccessHandler;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


/**
 * 表单个性化登录配置
 *
 * @author youzhengjie
 * @date 2023/05/06 00:35:52
 */
public final class FormIdentityLoginConfigurer
		extends AbstractHttpConfigurer<FormIdentityLoginConfigurer, HttpSecurity> {

	@Override
	public void init(HttpSecurity http) throws Exception {
		http.formLogin(formLogin -> {
			// 登录页路径
			formLogin.loginPage("/token/login");
			// 表单的登录请求要提交到的接口路径（注意）
			formLogin.loginProcessingUrl("/token/login_form");
			// 表单登录失败处理器
			formLogin.failureHandler(new CustomFormAuthenticationFailureHandler());

		})
			.logout()
			 // Sso单点退出成功处理
			.logoutSuccessHandler(new CustomSsoLogoutSuccessHandler())
			 // 删除cookies
			.deleteCookies("JSESSIONID")
			.invalidateHttpSession(true)
			.and()
			//关闭csrf
			.csrf()
			.disable();
	}

}
