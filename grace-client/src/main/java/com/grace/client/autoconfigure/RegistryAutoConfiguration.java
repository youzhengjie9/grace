package com.grace.client.autoconfigure;

import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.registry.GraceRegistryService;
import com.grace.client.registry.RegistryService;
import com.grace.client.registry.core.GraceRegistryApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author youzhengjie
 * @date 2023/10/24 11:20:33
 */
@Configuration
@EnableConfigurationProperties(GraceRegistryProperties.class)
public class RegistryAutoConfiguration {

    @Bean
    public GraceRegistryService graceRegistryService(){
        return new GraceRegistryService();
    }

    @Bean
    public GraceRegistryApplicationRunner graceRegistryApplicationRunner(){
        return new GraceRegistryApplicationRunner();
    }

}
