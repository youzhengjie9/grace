package com.grace.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// 由于没有使用mysql,所以要排除DataSourceAutoConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GraceOrder6001Application {
    public static void main(String[] args) {
        SpringApplication.run(GraceOrder6001Application.class);
    }
}