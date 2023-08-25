package com.grace.security.annotation;

import com.grace.security.resource.server.ResourceServerAutoConfiguration;
import com.grace.security.resource.server.ResourceServerConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;


/**
 * 开启Oauth2资源服务器
 * <p>
 *
 * @author youzhengjie
 * @date 2023/08/25 00:54:34
 */
@Documented
@Inherited
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ ResourceServerAutoConfiguration.class, ResourceServerConfiguration.class })
public @interface EnableGraceOauth2ResourceServer {



}
