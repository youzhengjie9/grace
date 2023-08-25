package com.grace.auth.support.core;

import com.petal.common.base.constant.Oauth2Constant;
import com.petal.common.security.service.SecurityOauth2User;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;


/**
 * 增强Oauth2的Token
 *
 * @author youzhengjie
 * @date 2023/05/08 12:08:46
 */
public class CustomeOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {

	private static final String LICENSE = "http://petal.oauth2.com:5020";

	@Override
	public void customize(OAuth2TokenClaimsContext context) {

		OAuth2TokenClaimsSet.Builder claims = context.getClaims();
		claims.claim(Oauth2Constant.DETAILS_LICENSE, LICENSE);
		String clientId = context.getAuthorizationGrant().getName();
		claims.claim(Oauth2Constant.CLIENT_ID, clientId);
		// 客户端模式不返回具体用户信息
		if (Oauth2Constant.CLIENT_CREDENTIALS.equals(context.getAuthorizationGrantType().getValue())) {
			return;
		}
		SecurityOauth2User securityOauth2User = (SecurityOauth2User) context.getPrincipal().getPrincipal();

		claims.claim(Oauth2Constant.USER_INFO, securityOauth2User);
	}

}
