package com.grace.examples.frameless;

import com.grace.client.service.NamespaceService;
import com.grace.client.service.impl.DefaultNamespaceServiceImpl;
import com.grace.common.constant.PropertiesKeyConstants;
import com.grace.common.constant.PropertiesValueConstants;

import java.util.Properties;

public class NamespaceFramelessExample {

    public static void main(String[] args) {

        //配置NamespaceService的基本属性
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstants.SERVER_ADDR,"localhost:8500");
        properties.setProperty(PropertiesKeyConstants.NAMESPACE,"ns_1002");
        properties.setProperty(PropertiesKeyConstants.AUTO_CREATE_NAMESPACE, PropertiesValueConstants.ON);
        NamespaceService namespaceService = new DefaultNamespaceServiceImpl(properties);
        //创建service
        namespaceService.createService("abc-service");
        //注册实例
        namespaceService.registerInstance("abc-service","192.168.184.100",1001);
        namespaceService.registerInstance("abc-service","192.168.184.200",2001);


    }

}
