package com.grace.console.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.Service;
import com.grace.common.utils.Result;
import com.grace.console.service.SvcService;
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
@RequestMapping(path = ParentMappingConstants.SERVICE_CONTROLLER)
public class ServiceController {

    private SvcService svcService;

    @Autowired
    public void setSvcService(SvcService svcService) {
        this.svcService = svcService;
    }

    @PostMapping(path = "/createService")
    public Result<Boolean> createService(@RequestBody Service service){
        return Result.ok(svcService.createService(service));
    }

    @GetMapping(path = "/getAllServices/{namespace}")
    public Result<List<Service>> getAllServices(@PathVariable("namespace") String namespace){
        return Result.ok(svcService.getAllServices(namespace));
    }

}