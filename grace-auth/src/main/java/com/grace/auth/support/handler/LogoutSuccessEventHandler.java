package com.grace.auth.support.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

/**
 * 退出成功事件处理器
 *
 * @author youzhengjie
 * @date 2023/05/09 22:52:18
 */
@Slf4j
@Component
public class LogoutSuccessEventHandler implements ApplicationListener<LogoutSuccessEvent> {

	@Override
	public void onApplicationEvent(LogoutSuccessEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		if (authentication instanceof PreAuthenticatedAuthenticationToken) {
			handle(authentication);
		}
	}

	/**
	 * 处理退出成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 */
	public void handle(Authentication authentication) {
		log.info("用户：{} 退出成功", authentication.getPrincipal());

		// TODO: 2023/5/24 保存日志

//		SysLog logVo = SysLogUtils.getSysLog();
//		logVo.setTitle("退出成功");
//		// 发送异步日志事件
//		Long startTime = System.currentTimeMillis();
//		Long endTime = System.currentTimeMillis();
//		logVo.setTime(endTime - startTime);
//
//		// 设置对应的token
//		WebUtil.getRequest().ifPresent(request -> logVo.setParams(request.getHeader(HttpHeaders.AUTHORIZATION)));
//
//		// 这边设置ServiceId
//		if (authentication instanceof PreAuthenticatedAuthenticationToken) {
//			logVo.setServiceId(authentication.getCredentials().toString());
//		}
//		logVo.setCreateBy(authentication.getName());
//		logVo.setUpdateBy(authentication.getName());
//		SpringContextHolder.publishEvent(new SysLogEvent(logVo));
	}

}
