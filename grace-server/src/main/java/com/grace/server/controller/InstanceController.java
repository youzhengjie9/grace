package com.grace.server.controller;

import com.grace.common.constant.ParentMappingConstant;
import com.grace.common.entity.Instance;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.InstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = ParentMappingConstant.INSTANCE_CONTROLLER)
public class InstanceController {

    private InstanceService instanceService;

    @Autowired
    public void setInstanceService(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

//    @PostMapping(path = "/registerInstance")
//    public ResponseResult<Boolean> registerInstance(@RequestBody Instance instance){
//
//        return ResponseResult.ok(instanceService.registerInstance(instance));
//    }
//
//
//    @GetMapping(path = "/getAllInstances/{namespace}/{serviceName}")
//    public ResponseResult<List<Instance>> getAllInstances(@PathVariable("namespace") String namespace,
//                                                          @PathVariable("serviceName") String serviceName){
//
//        return ResponseResult.ok(instanceService.getAllInstances(namespace, serviceName));
//    }
//
//
//    @GetMapping(path = "/getInstance/{namespace}/{serviceName}/{ipAddr}/{port}")
//    public ResponseResult<Instance> getInstance(@PathVariable("namespace") String namespace,
//                                                @PathVariable("serviceName") String serviceName,
//                                                @PathVariable("ipAddr") String ipAddr,
//                                                @PathVariable("port") int port){
//
//        return ResponseResult.ok(instanceService.getInstance(namespace, serviceName, ipAddr, port));
//    }


    @PostMapping(path = "/registerInstance")
    public ResponseResult<Boolean> registerInstance(HttpServletRequest request){

        return ResponseResult.ok(instanceService.registerInstance(instance));
    }


    @GetMapping(path = "/getAllInstances")
    public ResponseResult<List<Instance>> getAllInstances(HttpServletRequest request){

        return ResponseResult.ok(instanceService.getAllInstances(namespace, serviceName));
    }


    @GetMapping(path = "/getInstance")
    public ResponseResult<Instance> getInstance(HttpServletRequest request){

        return ResponseResult.ok(instanceService.getInstance(namespace, serviceName, ipAddr, port));
    }


}
