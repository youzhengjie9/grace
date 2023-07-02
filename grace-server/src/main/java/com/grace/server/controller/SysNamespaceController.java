package com.grace.server.controller;

import com.grace.common.dto.CreateSysNamespaceDTO;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.SysNamespaceService;
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
public class SysNamespaceController {

    private SysNamespaceService sysNamespaceService;

    @Autowired
    public void setNamespaceService(SysNamespaceService sysNamespaceService) {
        this.sysNamespaceService = sysNamespaceService;
    }

    @PostMapping(path = "/createNamespace")
    public ResponseResult<Boolean> createNamespace(@RequestBody CreateSysNamespaceDTO createSysNamespaceDTO){
        return sysNamespaceService.createNamespace(createSysNamespaceDTO);
    }


}
