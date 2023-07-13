package com.grace.server.controller;

import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/grace/server/instance")
public class InstanceController {

    private InstanceService instanceService;

    @Autowired
    public void setInstanceService(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping(path = "/registerInstance")
    public ResponseResult<Boolean> registerInstance(@RequestBody RegisterInstanceDTO registerInstanceDTO){
        return instanceService.registerInstance(registerInstanceDTO);
    }


}
