package com.grace.client.annotation;

import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.service.impl.RestTemplateNamespaceServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启grace注册中心功能
 *
 * @author youzhengjie
 * @date 2023/06/19 20:39:12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties({
        GraceRegistryProperties.class
})
@Import({
        RestTemplateNamespaceServiceImpl.class
})
public @interface EnableGraceRegistry {



}
