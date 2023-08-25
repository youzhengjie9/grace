package com.grace.auth.support.core;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.petal.common.base.constant.Oauth2Constant;
import com.petal.common.base.utils.WebUtil;
import com.petal.common.security.service.CustomUserDetailsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;


/**
 * 作用: 拿到用户信息(UserDetails),并手动验证密码是否正确（只有时帐号密码登录才需要校验,手机号登录直接跳过）
 *
 * @author youzhengjie
 * @date 2023/05/25 11:34:04
 */
@Slf4j
public class CustomDaoAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

	private final static BasicAuthenticationConverter basicConvert = new BasicAuthenticationConverter();

	private PasswordEncoder passwordEncoder;


	private volatile String userNotFoundEncodedPassword;

	private UserDetailsService userDetailsService;

	private UserDetailsPasswordService userDetailsPasswordService;

	public CustomDaoAuthenticationProvider() {
		setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
	}

	/**
	 * =============(被AbstractUserDetailsAuthenticationProvider类第 1 个执行)=============
	 * 在这个方法中调用了loadUserByUsername方法拿到用户信息(UserDetails)
	 *
	 * @param username       用户名
	 * @param authentication 身份验证
	 * @return {@link UserDetails}
	 */
	@SneakyThrows
	@Override
	protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
		prepareTimingAttackProtection();
		HttpServletRequest request = WebUtil.getRequest()
				.orElseThrow(
						(Supplier<Throwable>) () ->
								new InternalAuthenticationServiceException("request不能为空"));

		//获取请求的参数map
		Map<String, String> paramMap = ServletUtil.getParamMap(request);
		String grantType = paramMap.get(OAuth2ParameterNames.GRANT_TYPE);
		String clientId = paramMap.get(OAuth2ParameterNames.CLIENT_ID);

		// 因为我们的clientId是通过请求头Authorization设置Basic传递的,所以这里一定为空
		if (StrUtil.isBlank(clientId)) {
			// 从请求头Authorization中拿到Basic (base64加密的clientId),并解密base64从而获取到真正的clientId
			clientId = basicConvert.convert(request).getName();
			log.info("获取到clientId={}",clientId);
		}

		Map<String, CustomUserDetailsService> customUserDetailsServiceMap = SpringUtil
				.getBeansOfType(CustomUserDetailsService.class);

		String finalClientId = clientId;
		Optional<CustomUserDetailsService> optional = customUserDetailsServiceMap.values()
				.stream()
				.filter(service -> service.support(finalClientId, grantType))
				.max(Comparator.comparingInt(Ordered::getOrder));

		if (!optional.isPresent()) {
			throw new InternalAuthenticationServiceException("CustomUserDetailsService没有注册到Spring容器中");
		}

		try {
			//调用loadUserByUsername方法获取UserDetails
			UserDetails loadedUser = optional.get().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"CustomUserDetailsService返回了空值");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException ex) {
			mitigateAgainstTimingAttack(authentication);
			throw ex;
		}
		catch (InternalAuthenticationServiceException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	/**
	 *  =============(被AbstractUserDetailsAuthenticationProvider类第 2 个执行)=============
	 * 手动验证密码是否正确（只有时帐号密码登录才需要校验,手机号登录直接跳过）
	 *
	 * @param userDetails    userDetails是UserDetailsServiceImpl从数据库里面获取的数据（也就是该用户正确的帐号密码）
	 * @param authentication authentication是用户在表单输入的帐号密码等数据（等待和 userDetails这个正确的内容进行比较）
	 * @throws AuthenticationException 身份验证异常
	 */
	@Override
	@SuppressWarnings("deprecation")
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		// =========如果是SMS_GRANT_TYPE模式则不用校验密码=========
		String grantType = WebUtil.getRequest().get().getParameter(OAuth2ParameterNames.GRANT_TYPE);
		System.out.println("additionalAuthenticationChecks");
		System.out.println(grantType);
		if (StrUtil.equals(Oauth2Constant.SMS_GRANT_TYPE, grantType)) {
			return;
		}
		// ==========如果不是SMS_GRANT_TYPE模式============

		//如果密码为空
		if (authentication.getCredentials() == null) {
			this.logger.error("输入的密码为空");
			throw new BadCredentialsException("输入的密码为空");
		}
		// 用户在表单输入的密码
		String userInputPassword = authentication.getCredentials().toString();
		// 该用户在数据库中查询出来的正确的密码
		String correctPassword = userDetails.getPassword();
		//如果密码错误
		if (!this.passwordEncoder.matches(userInputPassword, correctPassword)) {
			this.logger.error("密码错误");
			throw new BadCredentialsException("密码错误");
		}
		this.logger.info("密码正确");
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
			UserDetails user) {
		boolean upgradeEncoding = this.userDetailsPasswordService != null
				&& this.passwordEncoder.upgradeEncoding(user.getPassword());
		if (upgradeEncoding) {
			String presentedPassword = authentication.getCredentials().toString();
			String newPassword = this.passwordEncoder.encode(presentedPassword);
			user = this.userDetailsPasswordService.updatePassword(user, newPassword);
		}
		return super.createSuccessAuthentication(principal, authentication, user);
	}

	private void prepareTimingAttackProtection() {
		if (this.userNotFoundEncodedPassword == null) {
			this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
		}
	}

	private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() != null) {
			String presentedPassword = authentication.getCredentials().toString();
			this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
		}
	}
	/**
	 * Sets the PasswordEncoder instance to be used to encode and validate passwords. If
	 * not set, the password will be compared using
	 * {@link PasswordEncoderFactories#createDelegatingPasswordEncoder()}
	 * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder}
	 * types.
	 */
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		Assert.notNull(passwordEncoder, "passwordEncoder不能为空");
		this.passwordEncoder = passwordEncoder;
		this.userNotFoundEncodedPassword = null;
	}

	protected PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}

	public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
		this.userDetailsPasswordService = userDetailsPasswordService;
	}

}
