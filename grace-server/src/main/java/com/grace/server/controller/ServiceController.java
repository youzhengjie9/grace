package com.grace.server.controller;

import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.SvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * service控制器
 *
 * @author youzhengjie
 * @date 2023/07/07 12:59:58
 */
@RestController
@RequestMapping(path = "/grace/server/service")
public class ServiceController {

    private SvcService svcService;

    @Autowired
    public void setSvcService(SvcService svcService) {
        this.svcService = svcService;
    }

    @PostMapping(path = "/createService")
    public ResponseResult<Boolean> createService(@RequestBody CreateServiceDTO createServiceDTO){
        return svcService.createService(createServiceDTO);
    }

}
