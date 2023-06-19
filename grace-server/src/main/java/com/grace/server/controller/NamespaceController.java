package com.grace.server.controller;

import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 命名空间控制器
 *
 * @author youzhengjie
 * @date 2023/06/19 22:23:38
 */
@RestController
@RequestMapping(path = "/grace/server/namespace")
public class NamespaceController {

    private NamespaceService namespaceService;

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @PostMapping(path = "/createNamespace")
    public ResponseResult<Boolean> createNamespace(@RequestBody CreateNamespaceDTO createNamespaceDTO){
        System.out.println("=============");
        System.out.println(createNamespaceDTO);
        return namespaceService.createNamespace(createNamespaceDTO);
    }


}
