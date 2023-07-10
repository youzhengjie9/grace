package com.grace.examples.frameless;

import com.grace.client.service.NamespaceService;
import com.grace.client.service.impl.DefaultNamespaceServiceImpl;
import com.grace.common.constant.PropertiesKeyConstant;
import com.grace.common.constant.PropertiesValueConstant;

import java.util.Properties;

public class NamespaceFramelessExample {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstant.SERVER_ADDR,"localhost:8500");
        properties.setProperty(PropertiesKeyConstant.NAMESPACE,"ns_1001");
        properties.setProperty(PropertiesKeyConstant.AUTO_CREATE_NAMESPACE, PropertiesValueConstant.ON);
        NamespaceService namespaceService = new DefaultNamespaceServiceImpl(properties);

        namespaceService.createService("abc-service");




    }

}
