package com.grace.examples;

import com.grace.client.annotation.EnableGraceRegistry;
import com.grace.client.service.NamespaceService;
import com.grace.common.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGraceRegistry
public class NamespaceExample {

    private static NamespaceService namespaceService;

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    public static void main(String[] args) {

        SpringApplication.run(NamespaceExample.class,args);

        ResponseResult<Boolean> res = namespaceService.createNamespace("names", "123");
        System.out.println(res.getData());
    }

}
