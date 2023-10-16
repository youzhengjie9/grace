package com.grace.console.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.Namespace;
import com.grace.common.utils.Result;
import com.grace.console.dto.CreateNamespaceDTO;
import com.grace.console.dto.ModifyNamespaceDTO;
import com.grace.console.service.NamespaceService;
import com.grace.console.vo.NamespaceVO;
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
    public Result<List<NamespaceVO>> getNamespaceList() {
        return Result.ok(namespaceService.getNamespaceList());
    }

    @PostMapping("/createNamespace")
    public Result<Boolean> createNamespace(CreateNamespaceDTO createNamespaceDTO){
        // 校验必填项
        createNamespaceDTO.validateRequired();
        // 填充默认值
        createNamespaceDTO.fillDefaultValue();
        // 构建Namespace对象
        Namespace namespace = createNamespaceDTO.buildNamespaceByCreateNamespaceDTO();
        return Result.ok(namespaceService.createNamespace(namespace));
    }

    @PutMapping("/modifyNamespace")
    public Result<Boolean> modifyNamespace(ModifyNamespaceDTO modifyNamespaceDTO){
        // 校验必填项
        modifyNamespaceDTO.validateRequired();
        // 填充默认值
        modifyNamespaceDTO.fillDefaultValue();
        // 构建Namespace对象
        Namespace namespace = modifyNamespaceDTO.buildNamespaceByModifyNamespaceDTO();
        return null;
    }
    @DeleteMapping("/deleteNamespace")
    public Result<Boolean> deleteNamespace(@RequestParam("namespaceId") String namespaceId){

        return null;
    }


}
