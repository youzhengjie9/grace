package com.grace.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * grace服务器端
 *
 * @author youzhengjie
 * @date 2023/06/14 10:58:29
 */
@SpringBootApplication
@EnableScheduling //开启定时任务
public class GraceServer {

    public static void main(String[] args) {
        SpringApplication.run(GraceServer.class,args);
    }

}