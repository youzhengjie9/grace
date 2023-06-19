package com.grace.client.autoconfigure;

import com.grace.client.properties.GraceRegistryProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        GraceRegistryProperties.class
})
public class GraceRegistryConfiguration {


}
