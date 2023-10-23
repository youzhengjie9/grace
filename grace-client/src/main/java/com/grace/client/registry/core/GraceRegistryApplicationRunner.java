package com.grace.client.registry.core;

import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.registry.RegistryService;
import com.grace.common.dto.RegisterInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 当SpringBoot容器启动完成之后,就执行一些有关注册中心的代码（比如: 注册实例）
 *
 * @author youzhengjie
 * @date 2023/10/23 21:29:27
 */
public class GraceRegistryApplicationRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(GraceRegistryApplicationRunner.class);
    @Autowired
    private GraceRegistryProperties graceRegistryProperties;
    @Autowired
    private RegistryService registryService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("=======================================");
        log.info("GraceRegistryApplicationRunner");

    }
}
