package com.grace.common.constant;


/**
 * oauth2错误码常量（ org.springframework.security.oauth2.core.OAuth2Error ）
 *
 * @author youzhengjie
 * @date 2023/05/25 09:07:20
 */
public final class OAuth2ErrorCodeConstants {

	/** 用户名未找到 */
	public static final String USERNAME_NOT_FOUND = "用户名未找到";

	/** 错误凭证 */
	public static final String BAD_CREDENTIALS = "错误凭证";

	/** 用户被锁 */
	public static final String USER_LOCKED = "用户被锁";

	/** 用户被禁用 */
	public static final String USER_DISABLE = "用户被禁用";

	/** 用户过期 */
	public static final String USER_EXPIRED = "用户过期";

	/** 证书过期 */
	public static final String CREDENTIALS_EXPIRED = "证书过期";

	/** scope域为空 */
	public static final String SCOPE_IS_EMPTY = "scope域为空";

	/**
	 * 没有传递accessToken参数
	 */
	public static final String ACCESS_TOKEN_MISSING = "没有传递accessToken参数";

	/** 未知的登录异常 */
	public static final String UN_KNOW_LOGIN_ERROR = "未知的登录异常";

	/**
	 * 不存在该accessToken
	 */
	public static final String ACCESS_TOKEN_NOT_EXIST = "不存在该accessToken";

}
