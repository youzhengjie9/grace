package com.grace.server.controller;

import com.grace.common.constant.ParentMappingConstant;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.Instance;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ParentMappingConstant.INSTANCE_CONTROLLER)
public class InstanceController {

    private InstanceService instanceService;

    @Autowired
    public void setInstanceService(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    @PostMapping(path = "/registerInstance")
    public ResponseResult<Boolean> registerInstance(@RequestBody RegisterInstanceDTO registerInstanceDTO){

        return ResponseResult.ok(instanceService.registerInstance(registerInstanceDTO));
    }


    @GetMapping(path = "/getAllInstances/{namespace}/{serviceName}")
    public ResponseResult<List<Instance>> getAllInstances(@PathVariable("namespace") String namespace,
                                                          @PathVariable("serviceName") String serviceName){

        return ResponseResult.ok(instanceService.getAllInstances(namespace, serviceName));
    }


    @GetMapping(path = "/getInstance/{namespace}/{serviceName}/{ipAddr}/{port}")
    public ResponseResult<Instance> getInstance(@PathVariable("namespace") String namespace,
                                                @PathVariable("serviceName") String serviceName,
                                                @PathVariable("ipAddr") String ipAddr,
                                                @PathVariable("port") int port){

        return ResponseResult.ok(instanceService.getInstance(namespace, serviceName, ipAddr, port));
    }

}
