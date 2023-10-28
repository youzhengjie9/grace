package com.grace.client.autoconfigure;

import com.grace.client.config.GraceConfigService;
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

//    @Bean
//    public GraceConfigService graceConfigService(){
//        return new GraceConfigService();
//    }


}
