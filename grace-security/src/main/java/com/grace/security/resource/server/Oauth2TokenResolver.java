package com.grace.security.resource.server;

import com.grace.security.properties.IgnoreAuthenticationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * oauth2的Token的解析器
 * <p>
 * 作用是: 拿到请求里的accessToken,对accessToken进行校验
 *
 * @author youzhengjie
 * @date 2023/08/25 16:50:23
 */
public class Oauth2TokenResolver implements BearerTokenResolver {

	/**
	 * 校验token用的正则表达式
	 */
	private static final Pattern authorizationPattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$",
			Pattern.CASE_INSENSITIVE);

	private boolean allowFormEncodedBodyParameter = false;

	private boolean allowUriQueryParameter = true;

	private String bearerTokenHeaderName = HttpHeaders.AUTHORIZATION;

	private final PathMatcher pathMatcher = new AntPathMatcher();

	private final IgnoreAuthenticationProperties ignoreAuthenticationProperties;

	public Oauth2TokenResolver(IgnoreAuthenticationProperties ignoreAuthenticationProperties) {
		this.ignoreAuthenticationProperties = ignoreAuthenticationProperties;
	}

	/**
	 * 解析request
	 *
	 * @param request 请求
	 * @return {@link String} 返回解析出来的token
	 */
	@Override
	public String resolve(HttpServletRequest request) {
		//当前请求的uri
		String currentRequestURI = request.getRequestURI();
		//判断当前请求的uri是否在ignoreUrls集合中（也就是该请求的uri是否可以不需要鉴权就可以被访问）
		boolean match = ignoreAuthenticationProperties.getIgnoreUrls()
			.stream()
			.anyMatch(ignoreUrl -> pathMatcher.match(ignoreUrl,currentRequestURI));

		//如果匹配成功直接return跳出该方法，不进行后面的token校验
		if (match) {
			return null;
		}
		//从请求头解析accessToken
		final String authorizationHeaderToken = resolveFromAuthorizationHeader(request);
		//从请求参数解析accessToken
		final String parameterToken = isParameterTokenSupportedForRequest(request)
				? resolveFromRequestParameters(request) : null;
		if (authorizationHeaderToken != null) {
			if (parameterToken != null) {
				final BearerTokenError error = BearerTokenErrors
					.invalidRequest("请求中存在多个accessToken");
				throw new OAuth2AuthenticationException(error);
			}
			return authorizationHeaderToken;
		}
		if (parameterToken != null && isParameterTokenEnabledForRequest(request)) {
			return parameterToken;
		}
		return null;
	}

	/**
	 * 从请求头Authorization中解析accessToken
	 *
	 * @param request 请求
	 * @return {@link String}
	 */
	private String resolveFromAuthorizationHeader(HttpServletRequest request) {
		//从请求头Authorization中解析accessToken
		String authorization = request.getHeader(this.bearerTokenHeaderName);
		//如果请求头上的Authorization的token没有以bearer开头则直接返回null，校验失败
		if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
			return null;
		}
		//校验token
		Matcher matcher = authorizationPattern.matcher(authorization);
		//如果校验token失败，直接抛出异常
		if (!matcher.matches()) {
			BearerTokenError error = BearerTokenErrors.invalidToken("accessToken格式不正确");
			throw new OAuth2AuthenticationException(error);
		}
		//校验token成功
		return matcher.group("token");
	}

	/**
	 * 从请求参数中解析accessToken
	 *
	 * @param request 请求
	 * @return {@link String}
	 */
	private static String resolveFromRequestParameters(HttpServletRequest request) {
		// 从请求参数中解析accessToken
		String[] values = request.getParameterValues("access_token");
		// 没有找到accessToken
		if (values == null || values.length == 0) {
			return null;
		}
		// 找到一个accessToken
		if (values.length == 1) {
			return values[0];
		}
		// 找到多个accessToken（这里就要抛出异常了）
		BearerTokenError error = BearerTokenErrors.invalidRequest("请求参数中存在多个accessToken");
		throw new OAuth2AuthenticationException(error);
	}

	/**
	 * 返回true的条件.（满足下面其中一个条件即可返回true）,当这个返回true才会去请求参数中检查是否有accessToken
	 * 1: 如果是GET请求，
	 * 2: 如果是POST请求并且content-type为application/x-www-form-urlencoded类型
	 *
	 * @param request 请求
	 * @return boolean
	 */
	private boolean isParameterTokenSupportedForRequest(final HttpServletRequest request) {
		return (("POST".equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| "GET".equals(request.getMethod()));
	}

	private boolean isParameterTokenEnabledForRequest(final HttpServletRequest request) {
		return ((this.allowFormEncodedBodyParameter && "POST".equals(request.getMethod())
				&& MediaType.APPLICATION_FORM_URLENCODED_VALUE.equals(request.getContentType()))
				|| (this.allowUriQueryParameter && "GET".equals(request.getMethod())));
	}

}
