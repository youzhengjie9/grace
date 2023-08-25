package com.grace.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.util.Assert;

import java.util.concurrent.TimeUnit;

/**
 * redis的oauth2认证授权业务类
 *
 * @author youzhengjie
 * @date 2023/08/25 17:02:50
 */
public class RedisOAuth2AuthorizationConsentService implements OAuth2AuthorizationConsentService {

	private RedisTemplate redisTemplate;

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	private final static Long TIMEOUT = 10L;

	@Override
	public void save(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent不能为空");

		redisTemplate.opsForValue()
			.set(buildKey(authorizationConsent), authorizationConsent, TIMEOUT, TimeUnit.MINUTES);

	}

	@Override
	public void remove(OAuth2AuthorizationConsent authorizationConsent) {
		Assert.notNull(authorizationConsent, "authorizationConsent不能为空");
		redisTemplate.delete(buildKey(authorizationConsent));
	}

	@Override
	public OAuth2AuthorizationConsent findById(String registeredClientId, String principalName) {
		Assert.hasText(registeredClientId, "registeredClientId不能为空");
		Assert.hasText(principalName, "principalName不能为空");
		return (OAuth2AuthorizationConsent) redisTemplate.opsForValue()
			.get(buildKey(registeredClientId, principalName));
	}

	private static String buildKey(String registeredClientId, String principalName) {
		return "token:consent:" + registeredClientId + ":" + principalName;
	}

	private static String buildKey(OAuth2AuthorizationConsent authorizationConsent) {
		return buildKey(authorizationConsent.getRegisteredClientId(), authorizationConsent.getPrincipalName());
	}

}
