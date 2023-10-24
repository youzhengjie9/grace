package com.grace.client.registry.core;

import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.registry.RegistryService;
import com.grace.common.constant.Constants;
import com.grace.common.dto.RegisterInstanceDTO;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

/**
 * 当SpringBoot容器启动完成之后,就执行一些有关注册中心的代码（比如: 注册实例）
 *
 * @author youzhengjie
 * @date 2023/10/23 21:29:27
 */
public class GraceRegistryApplicationRunner implements ApplicationRunner, EnvironmentAware {

    private static final Logger log = LoggerFactory.getLogger(GraceRegistryApplicationRunner.class);
    @Autowired
    private GraceRegistryProperties graceRegistryProperties;

    @Autowired
    private RegistryService registryService;

    private Environment environment;

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        log.info("environment加载成功");
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info(">>>>>>>>>注册实例开始<<<<<<<<<");

        // 创建一个注册实例dto对象
        RegisterInstanceDTO registerInstanceDTO = new RegisterInstanceDTO();
        registerInstanceDTO.setNamespaceId(graceRegistryProperties.getNamespaceId());
        registerInstanceDTO.setGroupName(graceRegistryProperties.getGroupName());
        registerInstanceDTO.setServiceName(graceRegistryProperties.getServiceName());
        // 获取当前应用的ip
        registerInstanceDTO.setIpAddr(InetAddress.getLocalHost().getHostAddress());
        // 获取当前应用的端口
        registerInstanceDTO.setPort(Integer.valueOf(environment.getProperty("server.port", String.valueOf(Constants.DEFAULT_CLIENT_PORT))));
        registerInstanceDTO.setWeight(graceRegistryProperties.getWeight());
        registerInstanceDTO.setHealthy(Constants.DEFAULT_HEALTHY);
        registerInstanceDTO.setOnline(Constants.DEFAULT_ONLINE);
        registerInstanceDTO.setEphemeral(graceRegistryProperties.getEphemeral());
        registerInstanceDTO.setMetadata("");
        // 注册实例
        Boolean registerInstanceResult = registryService.registerInstance(registerInstanceDTO);

        log.info(">>>>>>>>>注册实例结束<<<<<<<<<");
    }

}
