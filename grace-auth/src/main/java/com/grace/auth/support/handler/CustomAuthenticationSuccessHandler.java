package com.grace.auth.support.handler;

import cn.hutool.core.map.MapUtil;
import com.petal.common.base.constant.Oauth2Constant;
import com.petal.common.base.entity.SysLoginLog;
import com.petal.common.base.utils.*;
import com.petal.common.log.event.SysLoginLogEvent;
import com.petal.common.security.service.SecurityOauth2User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * 自定义登录成功处理器
 *
 * @author youzhengjie
 * @date 2023/05/08 11:46:30
 */
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter =
			new OAuth2AccessTokenResponseHttpMessageConverter();

	/**
	 * Called when a user has been successfully authenticated.
	 * @param request the request which caused the successful authentication
	 * @param response the response
	 * @param authentication the <tt>Authentication</tt> object which was created during
	 * the authentication process.
	 */
	@SneakyThrows
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;
		Map<String, Object> map = accessTokenAuthentication.getAdditionalParameters();
		if (MapUtil.isNotEmpty(map)) {
			// 发送异步日志事件
			SecurityOauth2User userInfo = (SecurityOauth2User) map.get(Oauth2Constant.USER_INFO);
			log.info("用户：{} 登录成功", userInfo.getName());
			// 将SecurityContext放到SecurityContextHolder中，说明登录成功
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			context.setAuthentication(accessTokenAuthentication);
			SecurityContextHolder.setContext(context);
			//获取ip
			String ipAddr = IpUtil.getIpAddrByHttpServletRequest(request);
			//获取ip所在的地址
			String address = IpToAddressUtil.getCityInfo(ipAddr);
			//获取用户使用的浏览器
			String browserName = BrowserUtil.getBrowserName(request);
			//获取用户使用的操作系统
			String osName = BrowserUtil.getOsName(request);
			SysLoginLog sysLoginLog = SysLoginLog.builder()
					.id(SnowId.nextId())
					.username(userInfo.getName())
					.ip(ipAddr)
					.address(address)
					.browser(browserName)
					.os(osName)
					.loginTime(LocalDateTime.now())
					.build();
			// 发布登录成功日志，由监听器异步处理任务，将数据添加到数据库中
			SpringContextHolder.publishEvent(new SysLoginLogEvent(sysLoginLog));
		}

		// 输出token
		sendAccessTokenResponse(request, response, authentication);
	}

	private void sendAccessTokenResponse(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		OAuth2AccessTokenAuthenticationToken accessTokenAuthentication = (OAuth2AccessTokenAuthenticationToken) authentication;

		OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken();
		OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken();
		Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters();

		OAuth2AccessTokenResponse.Builder builder = OAuth2AccessTokenResponse.withToken(accessToken.getTokenValue())
			.tokenType(accessToken.getTokenType())
			.scopes(accessToken.getScopes());
		if (accessToken.getIssuedAt() != null && accessToken.getExpiresAt() != null) {
			builder.expiresIn(ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt()));
		}
		if (refreshToken != null) {
			builder.refreshToken(refreshToken.getTokenValue());
		}
		if (!CollectionUtils.isEmpty(additionalParameters)) {
			builder.additionalParameters(additionalParameters);
		}
		OAuth2AccessTokenResponse accessTokenResponse = builder.build();
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

		// 删除SecurityContextHolder的context的信息
		SecurityContextHolder.clearContext();
		this.accessTokenHttpResponseConverter.write(accessTokenResponse, null, httpResponse);
	}

}
