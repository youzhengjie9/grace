package com.grace.examples.springboot;

import com.grace.client.annotation.EnableGraceRegistry;
import com.grace.client.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGraceRegistry
public class SysNamespaceSpringBootExample {

    private static NamespaceService namespaceService;

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    public static void main(String[] args) {

        SpringApplication.run(SysNamespaceSpringBootExample.class,args);

        Boolean result = namespaceService.createNamespace("names", "123").getData();
        System.out.println(result);
    }

}
