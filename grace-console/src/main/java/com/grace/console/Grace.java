package com.grace.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * grace
 *
 * @author youzhengjie
 * @date 2023/06/14 10:58:29
 */
@SpringBootApplication
@EnableScheduling //开启定时任务
public class Grace {

    public static void main(String[] args) {
        SpringApplication.run(Grace.class,args);
    }

}