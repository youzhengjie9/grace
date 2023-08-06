package com.grace.server.controller;

import com.grace.common.constant.ParentMappingConstant;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.entity.Service;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.SvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * service控制器
 *
 * @author youzhengjie
 * @date 2023/07/07 12:59:58
 */
@RestController
@RequestMapping(path = ParentMappingConstant.SERVICE_CONTROLLER)
public class ServiceController {

    private SvcService svcService;

    @Autowired
    public void setSvcService(SvcService svcService) {
        this.svcService = svcService;
    }

    @PostMapping(path = "/createService")
    public ResponseResult<Boolean> createService(@RequestBody Service service){
        return ResponseResult.ok(svcService.createService(service));
    }

    @GetMapping(path = "/getAllServices/{namespace}")
    public ResponseResult<List<Service>> getAllServices(@PathVariable("namespace") String namespace){
        return ResponseResult.ok(svcService.getAllServices(namespace));
    }

}
