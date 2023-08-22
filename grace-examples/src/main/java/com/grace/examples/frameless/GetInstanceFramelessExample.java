package com.grace.examples.frameless;

import com.grace.client.service.NamespaceService;
import com.grace.client.service.impl.DefaultNamespaceServiceImpl;
import com.grace.common.constant.PropertiesKeyConstants;
import com.grace.common.constant.PropertiesValueConstants;
import com.grace.common.entity.Instance;

import java.util.List;
import java.util.Properties;

public class GetInstanceFramelessExample {

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstants.SERVER_ADDR,"localhost:8500");
        properties.setProperty(PropertiesKeyConstants.NAMESPACE,"ns_1002");
        properties.setProperty(PropertiesKeyConstants.AUTO_CREATE_NAMESPACE, PropertiesValueConstants.OFF);
        NamespaceService namespaceService = new DefaultNamespaceServiceImpl(properties);

        // 1: 查询所有实例（成功案例）
        List<Instance> instances1 =
                namespaceService.getAllInstances("ns_1002", "abc-service");

        if(instances1 != null){
            int size1 = instances1.size();
            if(size1>0){
                System.out.println("size1="+size1);
                System.out.println(instances1);
            }else {
                System.out.println("size1="+0);
            }
        }else {
            System.out.println("instances1为null");
        }

        System.out.println("=================");

        // 2: 查询所有实例（失败案例）
        List<Instance> instances2 =
                namespaceService.getAllInstances("ns666999", "abc-service2");

        if(instances2 != null){
            int size2 = instances2.size();
            if(size2>0){
                System.out.println("size2="+size2);
                instances2.forEach(System.out::println);
            }else {
                System.out.println("size2="+0);
            }
        }else {
            System.out.println("instances2为null");
        }

        System.out.println("=================");

        // 3: 查询指定实例（成功案例）

        Instance instance1 = namespaceService.getInstance("ns_1002", "abc-service", "192.168.184.100", 1001);

        System.out.println((instance1 != null)?instance1:"instance1为空");

        System.out.println("=================");

        // 4: 查询指定实例（失败案例）

        Instance instance2 = namespaceService.getInstance("666123aa", "22121-service", "192.168.184.1002", 10012);

        System.out.println((instance2 != null)?instance2:"instance2为空");

        System.out.println("=================");

    }

}
