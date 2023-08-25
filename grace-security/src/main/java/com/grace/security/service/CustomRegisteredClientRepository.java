package com.grace.security.service;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.grace.common.entity.SysOauth2Client;
import com.grace.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;


/**
 * 自定义RegisteredClient存储库
 *
 * <p>
 * ==========================clientId传递格式=================================
 * 作用: 登录的Authorization请求头中会携带格式为 Basic base64加密的clientId:clientSecret 的内容
 * 那么首先OAuth2ClientAuthenticationFilter会通过调用RegisteredClientRepository类去数据库查询传入的客户端是否正确
 *
 * @author youzhengjie
 * @date 2023/08/25 16:59:11
 */
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

	/**
	 * 刷新令牌有效期默认 30 天
	 */
	private final static int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;

	/**
	 * 请求令牌有效期默认 12 小时
	 */
	private final static int accessTokenValiditySeconds = 60 * 60 * 12;

	private SysOauth2ClientFeign sysOauth2ClientFeign;

	@Autowired
	public void setSysOauth2ClientFeign(SysOauth2ClientFeign sysOauth2ClientFeign) {
		this.sysOauth2ClientFeign = sysOauth2ClientFeign;
	}

	/**
	 * 保存RegisteredClient
	 */
	@Override
	public void save(RegisteredClient registeredClient) {
		throw new UnsupportedOperationException();
	}


	@Override
	public RegisteredClient findById(String id) {
		throw new UnsupportedOperationException();
	}

	/**
	 * 重写原生方法支持redis缓存
	 * @param clientId
	 * @return
	 */
	@Override
	public RegisteredClient findByClientId(String clientId) {

		Result result = sysOauth2ClientFeign.getClientById(clientId,"123");

		if(result != null){

			Object data = result.getData();

			if(data == null){
				throw new OAuth2AuthorizationCodeRequestAuthenticationException
						(new OAuth2Error("客户端查询异常，请检查数据库链接"), null);
			}else {
				SysOauth2Client sysOauth2Client = JSON.parseObject((String) data,SysOauth2Client.class);
				RegisteredClient.Builder builder = RegisteredClient.withId(sysOauth2Client.getClientId())
						.clientId(sysOauth2Client.getClientId())
						.clientSecret("{noop}" + sysOauth2Client.getClientSecret())
						.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
						.clientAuthenticationMethods(clientAuthenticationMethods -> {
							clientAuthenticationMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
							clientAuthenticationMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_POST);
						});

				// 授权模式
				Optional.ofNullable(sysOauth2Client.getAuthorizedGrantTypes())
						.ifPresent(grants -> StringUtils.commaDelimitedListToSet(grants)
								.forEach(s -> builder.authorizationGrantType(new AuthorizationGrantType(s))));
				// 回调地址
				Optional.ofNullable(sysOauth2Client.getWebServerRedirectUri())
						.ifPresent(redirectUri -> Arrays.stream(redirectUri.split(StrUtil.COMMA))
								.filter(StrUtil::isNotBlank)
								.forEach(builder::redirectUri));

				// scope
				Optional.ofNullable(sysOauth2Client.getScope())
						.ifPresent(scope -> Arrays.stream(scope.split(StrUtil.COMMA))
								.filter(StrUtil::isNotBlank)
								.forEach(builder::scope));

				return builder
						.tokenSettings(TokenSettings.builder()
								.accessTokenFormat(OAuth2TokenFormat.REFERENCE)
								.accessTokenTimeToLive(Duration.ofSeconds(
										Optional.ofNullable(sysOauth2Client.getAccessTokenValidity()).orElse(accessTokenValiditySeconds)))
								.refreshTokenTimeToLive(Duration.ofSeconds(Optional.ofNullable(sysOauth2Client.getRefreshTokenValidity())
										.orElse(refreshTokenValiditySeconds)))
								.build())
						.clientSettings(ClientSettings.builder()
								.requireAuthorizationConsent(!BooleanUtil.toBoolean(sysOauth2Client.getAutoapprove()))
								.build())
						.build();
			}
		}else {
			throw new OAuth2AuthorizationCodeRequestAuthenticationException
					(new OAuth2Error("客户端查询异常,请检查数据库链接"), null);
		}

	}

}
