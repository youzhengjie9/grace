package com.grace.security.autoconfigure;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.grace.security")
@MapperScan(basePackages = "com.grace.security.mapper")
public class GraceSecurityAutoConfiguration {

}
