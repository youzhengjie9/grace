package com.grace.server.controller;

import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return namespaceService.createNamespace(createNamespaceDTO);
    }
    @GetMapping(path = "/hasNamespace/{namespaceName}")
    public ResponseResult<Boolean> hasNamespace(@PathVariable("namespaceName") String namespaceName){
        return namespaceService.hasNamespace(namespaceName);
    }

}
