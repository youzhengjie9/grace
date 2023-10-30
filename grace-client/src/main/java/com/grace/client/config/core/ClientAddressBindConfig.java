package com.grace.client.config.core;

import com.grace.client.config.ConfigService;
import com.grace.client.config.factory.ConfigServiceFactory;
import com.grace.client.properties.GraceConfigProperties;
import com.grace.common.constant.Constants;
import com.grace.common.dto.ClientAddressBindConfigDTO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 当springboot容器启动之后,就将“当前客户端的地址”绑定“客户端正在使用的配置中心的配置”
 *
 * @author youzhengjie
 * @date 2023/10/30 16:34:23
 */
public class ClientAddressBindConfig implements ApplicationRunner, EnvironmentAware {

    private ConfigService configService;

    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    private GraceConfigProperties graceConfigProperties;

    @Override
    public void setEnvironment(@NotNull Environment environment) {
        this.configurableEnvironment = (ConfigurableEnvironment) environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String consoleAddress = graceConfigProperties.getConsoleAddress();
        configService = ConfigServiceFactory.createConfigService(consoleAddress);
        String namespaceId = graceConfigProperties.getNamespaceId();
        String groupName = graceConfigProperties.getGroupName();
        String dataId = getDataId();
        String clientAddress = getClientIp() + ":" + getClientPort();
        // 创建ClientAddressBindConfigDTO对象
        ClientAddressBindConfigDTO clientAddressBindConfigDTO =
                new ClientAddressBindConfigDTO(namespaceId,groupName,dataId,clientAddress);
        // 将该客户端地址绑定对应的配置
        configService.clientAddressBindConfig(clientAddressBindConfigDTO);
    }

    /**
     * 获取dataId
     *
     * @return {@link String}
     */
    private String getDataId(){
        String dataId;
        String springApplicationName = configurableEnvironment.getProperty("spring.application.name");
        String springProfilesActive = configurableEnvironment.getProperty("spring.profiles.active");
        String configType = graceConfigProperties.getConfigType().getType();
        // 如果springProfilesActive不为null，说明“配置了”spring.profiles.active
        if(springProfilesActive != null){
            // 格式为: ${spring.application.name}-${spring.profiles.active}.${grace-config.config-type}
            dataId = springApplicationName + "-" + springProfilesActive + "." + configType;
        }
        // 如果springProfileActive为null，说明“没有配置”spring.profiles.active
        else {
            // 格式为: ${spring.application.name}.${grace-config.config-type}
            dataId = springApplicationName + "." + configType;
        }
        return dataId;
    }

    /**
     * 获取客户端的ip
     *
     * @return {@link String}
     */
    public String getClientIp() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }

    /**
     * 获取客户端的端口
     *
     * @return {@link String}
     */
    public String getClientPort(){
        return configurableEnvironment.getProperty("server.port",
                String.valueOf(Constants.DEFAULT_CLIENT_PORT));
    }

}
