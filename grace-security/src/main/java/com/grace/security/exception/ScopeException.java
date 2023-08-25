package com.grace.security.exception;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;


/**
 * oauth2的scope异常
 *
 * @author youzhengjie
 * @date 2023/08/25 01:02:01
 */
public class ScopeException extends OAuth2AuthenticationException {


	public ScopeException(String msg) {
		super(new OAuth2Error(msg), msg);
	}


	public ScopeException(String msg, Throwable cause) {
		super(new OAuth2Error(msg), cause);
	}

}
