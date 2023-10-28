package com.grace.order;

import com.grace.order.entity.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;

// 由于没有使用mysql,所以要排除DataSourceAutoConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(Product.class)
public class GraceOrder6001Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(GraceOrder6001Application.class);
    }
}