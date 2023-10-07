package com.grace.console.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.Namespace;
import com.grace.common.utils.Result;
import com.grace.console.dto.NamespaceDTO;
import com.grace.console.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 命名空间控制器
 *
 * @author youzhengjie
 * @date 2023/06/19 22:23:38
 */
@RestController
@RequestMapping(path = ParentMappingConstants.NAMESPACE_CONTROLLER)
public class NamespaceController {

    @Autowired
    private NamespaceService namespaceService;

    /**
     * 获取所有命名空间
     *
     * @return {@link Result}<{@link List}<{@link Namespace}>>
     */
    @GetMapping("/getNamespaceList")
    public Result<List<Namespace>> getNamespaceList() {
        return Result.ok(namespaceService.getNamespaceList());
    }

    /**
     * 根据命名空间id获取命名空间
     *
     * @param namespaceId namespaceId
     * @return {@link Result}<{@link Namespace}>
     */
    @GetMapping("/getNamespace")
    public Result<Namespace> getNamespace(@RequestParam("namespaceId") String namespaceId) {
        return Result.ok(namespaceService.getNamespace(namespaceId));
    }

    @PostMapping("/createNamespace")
    public Result<Boolean> createNamespace(NamespaceDTO namespaceDTO){

//        return Result.ok(namespaceService.createNamespace(namespace));
        return null;
    }


    @PutMapping("/modifyNamespace")
    public Result<Boolean> modifyNamespace(NamespaceDTO namespaceDTO){

        return null;
    }
    @DeleteMapping("/deleteNamespace")
    public Result<Boolean> deleteNamespace(@RequestParam("namespaceId") String namespaceId){

        return null;
    }


}
