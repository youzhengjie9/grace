package com.grace.security.properties;

import cn.hutool.core.util.ReUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.grace.security.annotation.PermitAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 配置所有需要忽略权限校验的路径
 * <p>
 * 作用: 将会忽略 DEFAULT_IGNORE_URLS所有的路径 + 所有被@PermitAll注解标记的接口或controller类的路径,这里路径允许所有人访问,服务调用不需要鉴权。(也就是不需要accessToken)
 *
 * @author youzhengjie
 * @date 2023/08/25 01:04:34
 */
@ConfigurationProperties(prefix = "security.oauth2.ignore")
public class IgnoreAuthenticationProperties implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(IgnoreAuthenticationProperties.class);

	private static final Pattern PATTERN = Pattern.compile("\\{(.*?)\\}");

	/**
	 * 默认忽略权限校验的url（这些路径允许所有人访问,服务调用不需要鉴权(也就是不需要accessToken)）
	 */
	private static final String[] DEFAULT_IGNORE_URLS = new String[] {
			"/actuator/**",
			"/error",
			"/v3/api-docs",
			"/image/captcha",
			"/druid/**"
	};

	/**
	 * 这些路径允许所有人访问,服务调用不需要鉴权(也就是不需要accessToken)
	 */
	private List<String> ignoreUrls = new ArrayList<>();

	@Override
	public void afterPropertiesSet() {
		//把默认忽略权限校验的路径集合（DEFAULT_IGNORE_URLS）放到ignoreUrls集合中（这个集合是用来存储新的忽略权限校验的路径）
		ignoreUrls.addAll(Arrays.asList(DEFAULT_IGNORE_URLS));
		// 获取RequestMappingHandlerMapping的bean对象
		RequestMappingHandlerMapping mapping = SpringUtil.getBean("requestMappingHandlerMapping");
		// 获取所有类中被@RequestMapping（包括@GetMapping和@PostMapping等）标注过的方法的对象的Map集合
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

		map.keySet().forEach(info -> {
			// 拿到每个被@RequestMapping（包括@GetMapping和@PostMapping等）标注过的方法的对象
			HandlerMethod handlerMethod = map.get(info);
			// 获取（controller的接口方法）上面的@PermitAll注解
			PermitAll permitAllMethod = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), PermitAll.class);
			Optional.ofNullable(permitAllMethod)
					// info.getPathPatternsCondition().getPatternValues()获取到的就是接口的路径path集合（例如：{/user/getUser,/user/add}等等）
				.ifPresent(permitAll -> Objects.requireNonNull(info.getPatternsCondition())
						.getPatterns()
						// 使用正则表达式把@RequestMapping上每个PathVariable语法（{xxx}）转成（*）
						// 例如：/user/{username}/{password}
						// 经过下面代码变成: /user/*/*
					.forEach(ignoreUrl -> {
						ignoreUrls.add(ReUtil.replaceAll(ignoreUrl, PATTERN, "*"));
						log.info("路径: {} 已加入ignoreUrls,允许所有人访问,服务调用不需要鉴权",ignoreUrl);
					})
				);

			// 获取（controller类）上面的@PermitAll注解
			PermitAll permitAllController = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), PermitAll.class);
			Optional.ofNullable(permitAllController)
					// info.getPathPatternsCondition().getPatternValues()获取到的就是接口的路径path集合（例如：{/user/getUser,/user/add}等等）
				.ifPresent(permitAll -> Objects.requireNonNull(info.getPatternsCondition())
						.getPatterns()
						// 使用正则表达式把@RequestMapping上每个PathVariable语法（{xxx}）转成（*）
						// 例如：/user/{username}/{password}
						// 经过下面代码变成: /user/*/*
					.forEach(ignoreUrl -> {
						ignoreUrls.add(ReUtil.replaceAll(ignoreUrl, PATTERN, "*"));
						log.info("路径: {} 已加入ignoreUrls,允许所有人访问,服务调用不需要鉴权",ignoreUrl);
					})
				);

		});
	}

	public List<String> getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(List<String> ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}
}
