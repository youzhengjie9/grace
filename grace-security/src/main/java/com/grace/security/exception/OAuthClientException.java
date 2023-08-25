package com.grace.security.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;


/**
 * oauth客户端异常
 *
 * @author youzhengjie
 * @date 2023/08/25 01:01:11
 */
public class OAuthClientException extends OAuth2AuthenticationException {


	public OAuthClientException(String msg) {
		super(new OAuth2Error(msg), msg);
	}

	public OAuthClientException(String msg, Throwable cause) {
		super(new OAuth2Error(msg), cause);
	}

}
