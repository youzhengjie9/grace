package com.grace.order;

import com.grace.client.annotation.EnableGraceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableGraceClient
public class GraceOrder6001Application {
    public static void main(String[] args) {
        SpringApplication.run(GraceOrder6001Application.class);
    }
}