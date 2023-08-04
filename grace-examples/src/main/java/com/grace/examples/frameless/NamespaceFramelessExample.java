package com.grace.examples.frameless;

import com.grace.client.service.NamespaceService;
import com.grace.client.service.impl.DefaultNamespaceServiceImpl;
import com.grace.common.constant.PropertiesKeyConstant;
import com.grace.common.constant.PropertiesValueConstant;

import java.util.Properties;

public class NamespaceFramelessExample {

    public static void main(String[] args) {

        //配置NamespaceService的基本属性
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstant.SERVER_ADDR,"localhost:8500");
        properties.setProperty(PropertiesKeyConstant.NAMESPACE,"ns_1002");
        properties.setProperty(PropertiesKeyConstant.AUTO_CREATE_NAMESPACE, PropertiesValueConstant.ON);
        NamespaceService namespaceService = new DefaultNamespaceServiceImpl(properties);
        //创建service
        namespaceService.createService("abc-service");
        //注册实例
        namespaceService.registerInstance("abc-service","192.168.184.100",1001);
        namespaceService.registerInstance("abc-service","192.168.184.200",2001);


    }

}
