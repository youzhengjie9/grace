package com.grace.auth.support.password;

import com.petal.auth.support.base.OAuth2AuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;


/**
 * 密码登录的token的信息（密码登录授权认证对象）
 *
 * @author youzhengjie
 * @date 2023/05/08 13:12:14
 */
public class PasswordAuthenticationToken extends OAuth2AuthenticationToken {

	public PasswordAuthenticationToken(AuthorizationGrantType authorizationGrantType,
									   Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
		super(authorizationGrantType, clientPrincipal, scopes, additionalParameters);
	}

}
