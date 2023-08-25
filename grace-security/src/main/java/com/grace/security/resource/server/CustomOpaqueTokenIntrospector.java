package com.grace.security.resource.server;

import cn.hutool.extra.spring.SpringUtil;
import com.grace.common.constant.Oauth2Constants;
import com.grace.security.service.CustomUserDetailsService;
import com.grace.security.service.SecurityOauth2User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.security.Principal;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


/**
 * 自定义token自省器
 * <p>
 * 作用: 携带token去访问认证服务端的token自省端点（/oauth2/introspect）,获取对应的有效用户信息，验证token并返回其属性，表明验证成功。
 *
 * @author youzhengjie
 * @date 2023/08/25 16:35:40
 */
public class CustomOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

	private static final Logger log = LoggerFactory.getLogger(CustomOpaqueTokenIntrospector.class);

	private final OAuth2AuthorizationService authorizationService;

	public CustomOpaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Override
	public OAuth2AuthenticatedPrincipal introspect(String token) {
		// 根据token从Redis中查询认证信息（这个是旧的认证信息）
		OAuth2Authorization oldAuthorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
		if (Objects.isNull(oldAuthorization)) {
			throw new InvalidBearerTokenException(token);
		}

		// 客户端模式默认返回
		if (AuthorizationGrantType.CLIENT_CREDENTIALS.equals(oldAuthorization.getAuthorizationGrantType())) {
			return new DefaultOAuth2AuthenticatedPrincipal(oldAuthorization.getPrincipalName(),
					oldAuthorization.getAttributes(), AuthorityUtils.NO_AUTHORITIES);
		}

		Map<String, CustomUserDetailsService> userDetailsServiceMap = SpringUtil
			.getBeansOfType(CustomUserDetailsService.class);

		// =============使用CustomUserDetailsService再次查询最新的认证信息（这个是最新的认证信息）================
		Optional<CustomUserDetailsService> optional = userDetailsServiceMap.values()
			.stream()
			.filter(service -> service.support(Objects.requireNonNull(oldAuthorization).getRegisteredClientId(),
					oldAuthorization.getAuthorizationGrantType().getValue()))
			.max(Comparator.comparingInt(Ordered::getOrder));

		UserDetails userDetails = null;
		try {
			Object principal = Objects.requireNonNull(oldAuthorization).getAttributes().get(Principal.class.getName());
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
			Object tokenPrincipal = usernamePasswordAuthenticationToken.getPrincipal();
			userDetails = optional.get().loadUserBySecurityOauth2User((SecurityOauth2User) tokenPrincipal);

		}
		catch (UsernameNotFoundException notFoundException) {
			log.warn("用户不不存在 {}", notFoundException.getLocalizedMessage());
			throw notFoundException;
		}
		catch (Exception ex) {
			log.error("自省token失败: {}", ex.getLocalizedMessage());
		}
		// ==============================================

		// 注入扩展属性,方便上下文获取客户端ID
		SecurityOauth2User securityOauth2User = (SecurityOauth2User) userDetails;
		Objects.requireNonNull(securityOauth2User)
			.getAttributes()
			.put(Oauth2Constants.CLIENT_ID, oldAuthorization.getRegisteredClientId());
		return securityOauth2User;
	}

}
