package com.grace.auth.support.base;

import com.petal.common.security.utils.OAuth2EndpointUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Oauth2请求认证转换器
 *
 * @author youzhengjie
 * @date 2023/05/05 23:49:43
 */
public abstract class OAuth2AuthenticationConverter<T extends OAuth2AuthenticationToken>
		implements AuthenticationConverter {

	/**
	 * 是否支持此convert
	 * @param grantType 授权类型
	 * @return
	 */
	public abstract boolean support(String grantType);

	/**
	 * 校验参数
	 * @param request 请求
	 */
	public void checkParams(HttpServletRequest request) {

	}

	/**
	 * 构建具体类型的token
	 * @param clientPrincipal
	 * @param requestedScopes
	 * @param additionalParameters
	 * @return
	 */
	public abstract T buildToken(Authentication clientPrincipal, Set<String> requestedScopes,
			Map<String, Object> additionalParameters);

	@Override
	public Authentication convert(HttpServletRequest request) {

		// 拿到grant_type
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		// 如果不支持这个grantType，则返回空
		if (!support(grantType)) {
			return null;
		}

		MultiValueMap<String, String> parameters = OAuth2EndpointUtil.getParameters(request);
		// 拿到scope
		String scope = parameters.getFirst(OAuth2ParameterNames.SCOPE);
		if (StringUtils.hasText(scope) && parameters.get(OAuth2ParameterNames.SCOPE).size() != 1) {
			OAuth2EndpointUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.SCOPE,
					OAuth2EndpointUtil.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		Set<String> requestedScopes = null;
		if (StringUtils.hasText(scope)) {
			requestedScopes = new HashSet<>(Arrays.asList(StringUtils.delimitedListToStringArray(scope, " ")));
		}

		// 校验个性化参数
		checkParams(request);

		// 获取当前已经认证的客户端信息
		Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();
		if (clientPrincipal == null) {
			OAuth2EndpointUtil.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ErrorCodes.INVALID_CLIENT,
					OAuth2EndpointUtil.ACCESS_TOKEN_REQUEST_ERROR_URI);
		}

		// 扩展信息
		Map<String, Object> additionalParameters = parameters.entrySet()
			.stream()
			.filter(e -> !e.getKey().equals(OAuth2ParameterNames.GRANT_TYPE)
					&& !e.getKey().equals(OAuth2ParameterNames.SCOPE))
			.collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));

		// 创建token
		return buildToken(clientPrincipal, requestedScopes, additionalParameters);
	}

}
