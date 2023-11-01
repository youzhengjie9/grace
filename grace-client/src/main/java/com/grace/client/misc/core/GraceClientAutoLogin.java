package com.grace.client.misc.core;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * grace客户端自动登录
 *
 * @author youzhengjie
 * @date 2023/11/01 01:07:35
 */
public class GraceClientAutoLogin implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {


    }

}
