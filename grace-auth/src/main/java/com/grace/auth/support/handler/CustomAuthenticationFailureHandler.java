package com.grace.auth.support.handler;

import cn.hutool.core.util.StrUtil;
import com.petal.common.base.constant.Oauth2Constant;
import com.petal.common.base.utils.ResponseResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 自定义登录失败处理器
 *
 * @author youzhengjie
 * @date 2023/05/08 11:47:31
 */
@Slf4j
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private final MappingJackson2HttpMessageConverter errorHttpResponseConverter =
			new MappingJackson2HttpMessageConverter();

	@Override
	@SneakyThrows
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) {
		String username = request.getParameter(OAuth2ParameterNames.USERNAME);
		log.info("用户：{} 登录失败，异常：{}", username, exception.getLocalizedMessage());
		// TODO: 2023/5/24 登录失败日志
//		SysLog logVo = SysLogUtils.getSysLog();
//		logVo.setTitle("登录失败");
//		logVo.setType(LogTypeEnum.ERROR.getType());
//		logVo.setException(exception.getLocalizedMessage());
//		// 发送异步日志事件
//		String startTimeStr = request.getHeader(CommonConstants.REQUEST_START_TIME);
//		if (StrUtil.isNotBlank(startTimeStr)) {
//			Long startTime = Long.parseLong(startTimeStr);
//			Long endTime = System.currentTimeMillis();
//			logVo.setTime(endTime - startTime);
//		}
//
//		logVo.setServiceId(WebUtil.getClientId());
//		logVo.setCreateBy(username);
//		logVo.setUpdateBy(username);
//		SpringContextHolder.publishEvent(new SysLogEvent(logVo));
		// 写出错误信息
		sendErrorResponse(request, response, exception);
	}

	private void sendErrorResponse(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);
		httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
		String errorMessage;

		if (exception instanceof OAuth2AuthenticationException) {
			OAuth2AuthenticationException authorizationException = (OAuth2AuthenticationException) exception;
			errorMessage = StrUtil.isBlank(authorizationException.getError().getDescription())
					? authorizationException.getError().getErrorCode()
					: authorizationException.getError().getDescription();
		}
		else {
			errorMessage = exception.getLocalizedMessage();
		}

		// 如果是手机验证码登录
		String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
		if (Oauth2Constant.SMS_GRANT_TYPE.equals(grantType)) {
			errorMessage = "手机号不存在,登录失败";
		}

		this.errorHttpResponseConverter.write(ResponseResult.fail(errorMessage), MediaType.APPLICATION_JSON, httpResponse);
	}

}
