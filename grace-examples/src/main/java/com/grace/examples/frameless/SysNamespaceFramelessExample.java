package com.grace.examples.frameless;

import com.grace.client.service.NamespaceService;
import com.grace.client.service.impl.DefaultNamespaceServiceImpl;

public class SysNamespaceFramelessExample {

    public static void main(String[] args) {

        NamespaceService namespaceService = new DefaultNamespaceServiceImpl();

        Boolean result = namespaceService.createNamespace("name111", "desc222").getData();

        System.out.println(result);

    }

}
