package com.grace.examples.springboot;

import com.grace.client.annotation.EnableGraceRegistry;
import com.grace.client.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGraceRegistry
public class NamespaceSpringBootExample {

    private static NamespaceService namespaceService;

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    public static void main(String[] args) {

        SpringApplication.run(NamespaceSpringBootExample.class,args);

        boolean result = namespaceService.createNamespace("names", "123");
        System.out.println(result);
    }

}
