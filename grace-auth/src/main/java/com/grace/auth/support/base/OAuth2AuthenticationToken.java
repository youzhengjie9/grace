package com.grace.auth.support.base;

import lombok.Getter;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.util.*;


/**
 * 自定义授权模式抽象类
 *
 * @author youzhengjie
 * @date 2023/05/05 23:50:17
 */
public abstract class OAuth2AuthenticationToken extends AbstractAuthenticationToken {

	@Getter
	private final AuthorizationGrantType authorizationGrantType;

	@Getter
	private final Authentication clientPrincipal;

	@Getter
	private final Set<String> scopes;

	@Getter
	private final Map<String, Object> additionalParameters;

	public OAuth2AuthenticationToken(AuthorizationGrantType authorizationGrantType,
									 Authentication clientPrincipal, @Nullable Set<String> scopes,
									 @Nullable Map<String, Object> additionalParameters) {
		super(Collections.emptyList());
		Assert.notNull(authorizationGrantType, "authorizationGrantType不能为空");
		Assert.notNull(clientPrincipal, "clientPrincipal不能为空");
		this.authorizationGrantType = authorizationGrantType;
		this.clientPrincipal = clientPrincipal;
		this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
		this.additionalParameters = Collections.unmodifiableMap(
				additionalParameters != null ? new HashMap<>(additionalParameters) : Collections.emptyMap());
	}

	/**
	 * 扩展模式一般不需要密码
	 */
	@Override
	public Object getCredentials() {
		return "";
	}

	/**
	 * 获取用户名
	 */
	@Override
	public Object getPrincipal() {
		return this.clientPrincipal;
	}

}
