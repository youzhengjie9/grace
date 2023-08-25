package com.grace.auth.support.sms;

import com.petal.auth.support.base.OAuth2AuthenticationConverter;
import com.petal.common.base.constant.Oauth2Constant;
import com.petal.common.security.utils.OAuth2EndpointUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;


/**
 * 手机验证码登录转换器
 * 作用: 根据请求中的参数（sms）和授权类型组装成（手机验证码登录）的授权认证对象（SmsAuthenticationToken）
 * @author youzhengjie
 * @date 2023/05/08 13:11:45
 */
public class SmsAuthenticationConverter
		extends OAuth2AuthenticationConverter<SmsAuthenticationToken> {

	/**
	 * 是否支持此convert
	 * @param grantType 授权类型
	 * @return
	 */
	@Override
	public boolean support(String grantType) {
		return Oauth2Constant.SMS_GRANT_TYPE.equals(grantType);
	}

	@Override
	public SmsAuthenticationToken buildToken(Authentication clientPrincipal, Set requestedScopes,
											 Map additionalParameters) {
		return new SmsAuthenticationToken(new AuthorizationGrantType(Oauth2Constant.SMS_GRANT_TYPE),
				clientPrincipal, requestedScopes, additionalParameters);
	}

	/**
	 * 校验扩展参数 密码模式密码必须不为空
	 * @param request 参数列表
	 */
	@Override
	public void checkParams(HttpServletRequest request) {
		MultiValueMap<String, String> parameters = OAuth2EndpointUtil.getParameters(request);
		// 获取请求参数sms（必须要有）
		String phone = parameters.getFirst(Oauth2Constant.PHONE_PARAMETER_NAME);
		if (!StringUtils.hasText(phone) || parameters.get(Oauth2Constant.PHONE_PARAMETER_NAME).size() != 1) {
			OAuth2EndpointUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, Oauth2Constant.PHONE_PARAMETER_NAME,
					OAuth2EndpointUtil.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}
	}

}
