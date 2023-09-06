package com.grace.console.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.Namespace;
import com.grace.common.utils.Result;
import com.grace.console.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 命名空间控制器
 *
 * @author youzhengjie
 * @date 2023/06/19 22:23:38
 */
@RestController
@RequestMapping(path = ParentMappingConstants.NAMESPACE_CONTROLLER)
public class NamespaceController {

    private NamespaceService namespaceService;

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @PostMapping(path = "/createNamespace")
    public Result<Boolean> createNamespace(@RequestBody Namespace namespace){
        return Result.ok(namespaceService.createNamespace(namespace));
    }
    @GetMapping(path = "/hasNamespace/{namespaceName}")
    public Result<Boolean> hasNamespace(@PathVariable("namespaceName") String namespaceName){
        return Result.ok(namespaceService.hasNamespace(namespaceName));
    }

}
