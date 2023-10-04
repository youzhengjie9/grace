package com.grace.examples.frameless;

import com.grace.client.registry.RegistryService;
import com.grace.client.registry.factory.RegistryServiceFactory;
import com.grace.common.constant.PropertiesKeyConstants;
import com.grace.common.constant.PropertiesValueConstants;

import java.util.Properties;

public class NamespaceFramelessExample {

    public static void main(String[] args) {

        //配置NamespaceService的基本属性
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstants.CONSOLE_ADDRESS,"localhost:8500");
        properties.setProperty(PropertiesKeyConstants.NAMESPACE_NAME,"ns_1002");
        // TODO: 2023/10/5
//        properties.setProperty(PropertiesKeyConstants.AUTO_CREATE_NAMESPACE, PropertiesValueConstants.ON);
        RegistryService registryService = RegistryServiceFactory.createRegistryService(properties);
        //创建service
//        registryService.createService("abc-service");
        //注册实例
        registryService.registerInstance("abc-service","192.168.184.100",1001);
        registryService.registerInstance("abc-service","192.168.184.200",2001);


    }

}
