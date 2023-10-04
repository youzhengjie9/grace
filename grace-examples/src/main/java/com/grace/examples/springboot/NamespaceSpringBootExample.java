package com.grace.examples.springboot;

import com.grace.client.annotation.EnableGraceRegistry;
import com.grace.client.registry.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableGraceRegistry
public class NamespaceSpringBootExample {

    private static RegistryService registryService;

    @Autowired
    public void setNamespaceService(RegistryService registryService) {
        this.registryService = registryService;
    }

    public static void main(String[] args) {

        SpringApplication.run(NamespaceSpringBootExample.class,args);

        boolean result = registryService.createNamespace("names", "123");
        System.out.println(result);
    }

}
