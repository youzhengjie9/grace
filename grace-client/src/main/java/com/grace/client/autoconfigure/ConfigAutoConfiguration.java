package com.grace.client.autoconfigure;

import com.grace.client.config.core.ClientAddressBindConfig;
import com.grace.client.config.core.SchedulePullConfig;
import com.grace.client.config.endpoint.DynamicRefreshConfigEndpoint;
import com.grace.client.properties.GraceConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置中心自动配置类
 *
 * @author youzhengjie
 * @date 2023/10/26 20:31:33
 */
@Configuration
@EnableConfigurationProperties(GraceConfigProperties.class)
public class ConfigAutoConfiguration {

    @Bean
    public SchedulePullConfig schedulePullConfig(){
        return new SchedulePullConfig();
    }

    @Bean
    public DynamicRefreshConfigEndpoint dynamicRefreshConfigEndpoint(){
        return new DynamicRefreshConfigEndpoint();
    }

    @Bean
    public ClientAddressBindConfig clientAddressBindConfig(){
        return new ClientAddressBindConfig();
    }

}
