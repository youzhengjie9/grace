package com.grace.auth.support.password;

import com.petal.auth.support.base.OAuth2AuthenticationConverter;
import com.petal.common.base.constant.Oauth2Constant;
import com.petal.common.security.utils.OAuth2EndpointUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;


/**
 * 密码登录转换器
 * 作用: 根据请求中的参数（username、password）和授权类型组装成（密码登录）的授权认证对象（PasswordAuthenticationToken）
 * @author youzhengjie
 * @date 2023/05/08 13:11:58
 */
public class PasswordAuthenticationConverter
		extends OAuth2AuthenticationConverter<PasswordAuthenticationToken> {

	/**
	 * 支持密码模式
	 * @param grantType 授权类型
	 */
	@Override
	public boolean support(String grantType) {
		return AuthorizationGrantType.PASSWORD.getValue().equals(grantType);
	}

	@Override
	public PasswordAuthenticationToken buildToken(Authentication clientPrincipal,
												  Set requestedScopes, Map additionalParameters) {
		return new PasswordAuthenticationToken(AuthorizationGrantType.PASSWORD, clientPrincipal,
				requestedScopes, additionalParameters);
	}

	/**
	 * 校验扩展参数 密码模式密码必须不为空
	 * @param request 参数列表
	 */
	@Override
	public void checkParams(HttpServletRequest request) {
		MultiValueMap<String, String> parameters = OAuth2EndpointUtil.getParameters(request);
		// 获取username请求参数（必须要有）
		String username = parameters.getFirst(Oauth2Constant.USERNAME_PARAMETER_NAME);
		if (!StringUtils.hasText(username) || parameters.get(OAuth2ParameterNames.USERNAME).size() != 1) {
			OAuth2EndpointUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.USERNAME,
					OAuth2EndpointUtil.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		// 获取password请求参数（必须要有）
		String password = parameters.getFirst(Oauth2Constant.PASSWORD_PARAMETER_NAME);
		if (!StringUtils.hasText(password) || parameters.get(OAuth2ParameterNames.PASSWORD).size() != 1) {
			OAuth2EndpointUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.PASSWORD,
					OAuth2EndpointUtil.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
	}

}
