package com.grace.client.autoconfigure;

import com.grace.client.registry.GraceRegistryService;
import com.grace.client.registry.RegistryService;
import com.grace.client.registry.core.GraceRegistryApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegistryAutoConfiguration {

    @Bean
    public RegistryService registryService(){
        return new GraceRegistryService();
    }

    @Bean
    public GraceRegistryApplicationRunner graceRegistryApplicationRunner(){
        return new GraceRegistryApplicationRunner();
    }

}
