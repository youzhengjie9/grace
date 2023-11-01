package com.grace.client.autoconfigure;

import com.grace.client.properties.GraceProperties;
import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.registry.core.GraceRegistryApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注册中心自动配置类
 *
 * @author youzhengjie
 * @date 2023/10/24 11:20:33
 */
@Configuration
@EnableConfigurationProperties({
        GraceProperties.class,
        GraceRegistryProperties.class
})
public class RegistryAutoConfiguration {

    @Bean
    public GraceRegistryApplicationRunner graceRegistryApplicationRunner(){
        return new GraceRegistryApplicationRunner();
    }

}
