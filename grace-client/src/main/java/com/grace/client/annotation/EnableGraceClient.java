package com.grace.client.annotation;

import com.grace.client.autoconfigure.RegistryAutoConfiguration;
import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.registry.GraceRegistryService;
import com.grace.client.registry.core.GraceRegistryApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 只要在springboot项目中加上这个注解就能操作grace注册中心和配置中心（注意: 要被SpringBoot扫描到这个注解）
 *
 * @author youzhengjie
 * @date 2023/10/23 17:59:35
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableConfigurationProperties(GraceRegistryProperties.class)
@Import({
        RegistryAutoConfiguration.class
})
public @interface EnableGraceClient {

}
