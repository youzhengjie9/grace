package com.grace.console.controller;

import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.dto.HeartBeat;
import com.grace.common.entity.Instance;
import com.grace.common.utils.IpUtils;
import com.grace.common.utils.Result;
import com.grace.common.dto.ModifyInstanceDTO;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.console.service.InstanceService;
import com.grace.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作实例的控制器
 *
 * @author youzhengjie
 * @date 2023/10/05 14:58:28
 */
@RestController
@RequestMapping(path = ParentMappingConstants.INSTANCE_CONTROLLER)
public class InstanceController {

    private InstanceService instanceService;

    @Autowired
    public void setInstanceService(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    /**
     * 注册实例
     *
     * @param registerInstanceDTO instanceDTO
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping(path = "/registerInstance")
    public Result<Boolean> registerInstance(@RequestBody RegisterInstanceDTO registerInstanceDTO, HttpServletRequest request){
        // 校验InstanceDTO对象的必填属性是否为空,为空则抛出异常
        registerInstanceDTO.validateRequired();
        // 填充默认值（注意: 不会填充必填属性！）
        registerInstanceDTO.fillDefaultValue();
        // 检查权重值是否合法
        checkWeight(registerInstanceDTO.getWeight());
        // 如果metadata不为空我们才进行校验,为空就不校验了（因为metadata不是必填项）
        if(!StringUtils.isBlank(registerInstanceDTO.getMetadata())){
            // 校验metadata是否为JSON格式的字符串
            Boolean metadataIsJsonString = JsonUtils.isJsonString(registerInstanceDTO.getMetadata());
            // 如果metadata不是JSON格式的字符串,则创建失败
            if(!metadataIsJsonString){
//                log.error("metadata不是JSON字符串格式。创建失败");
                return Result.fail(null);
            }
        }
        String namespaceId = registerInstanceDTO.getNamespaceId();
        String groupName = registerInstanceDTO.getGroupName();
        String serviceName = registerInstanceDTO.getServiceName();
        // 通过RegisterInstanceDTO对象构建实例
        Instance instance = registerInstanceDTO.buildInstanceByRegisterInstanceDTO();
        return Result.ok(instanceService.registerInstance(namespaceId,groupName,serviceName,instance));
    }

    @PreAuthorize("@pms.hasPermission('instance:modify')")
    @PutMapping(path = "/modifyInstance")
    public Result<Boolean> modifyInstance(@RequestBody ModifyInstanceDTO modifyInstanceDTO){
        // 校验modifyInstanceDTO对象的必填属性是否为空,为空则抛出异常
        modifyInstanceDTO.validateRequired();
        // 填充默认值（注意: 不会填充必填属性！）
        modifyInstanceDTO.fillDefaultValue();
        // 检查权重值是否合法
        checkWeight(modifyInstanceDTO.getWeight());
        // 如果metadata不为空我们才进行校验,为空就不校验了（因为metadata不是必填项）
        if(!StringUtils.isBlank(modifyInstanceDTO.getMetadata())){
            // 校验metadata是否为JSON格式的字符串
            Boolean metadataIsJsonString = JsonUtils.isJsonString(modifyInstanceDTO.getMetadata());
            // 如果metadata不是JSON格式的字符串,则创建失败
            if(!metadataIsJsonString){
//                log.error("metadata不是JSON字符串格式。创建失败");
                return Result.fail(null);
            }
        }
        String namespaceId = modifyInstanceDTO.getNamespaceId();
        String groupName = modifyInstanceDTO.getGroupName();
        String serviceName = modifyInstanceDTO.getServiceName();
        // 通过ModifyInstanceDTO对象构建实例
        Instance instance = modifyInstanceDTO.buildInstanceByModifyInstanceDTO();
        return Result.ok(instanceService.modifyInstance(namespaceId,groupName,serviceName,instance));
    }

    /**
     * 接收put类型的请求的心跳,由于会涉及到更新实例对象的“最近心跳请求时间”,所以是put请求
     *
     * @param heartBeat 某个实例的心跳请求
     * @return {@link Result}<{@link Boolean}>
     */
    @PutMapping("/heartBeat")
    public Result<Boolean> heartBeat(@RequestBody HeartBeat heartBeat){
        // TODO: 2023/10/25 校验心跳必选项参数
        instanceService.processHeartBeat(heartBeat);
        return Result.ok(true);
    }

    /**
     * 获取指定服务下面的所有实例（也就是: 服务发现）
     *
     * @return {@link Result}<{@link List}<{@link Instance}>>
     */
    @GetMapping("/getAllInstance")
    public Result<List<Instance>> getAllInstance(@RequestParam(value = "namespaceId",required = false,defaultValue = Constants.DEFAULT_NAMESPACE_ID) String namespaceId,
                                                 @RequestParam(value = "groupName",required = false,defaultValue = Constants.DEFAULT_GROUP_NAME) String groupName,
                                                 @RequestParam(value = "serviceName") String serviceName){

        return Result.ok(instanceService.getAllInstance(namespaceId, groupName, serviceName));
    }

    /**
     * 检查权重值是否合法
     *
     * @param weight 权重
     */
    private void checkWeight(Double weight) {
        if(weight > Constants.MAX_WEIGHT_VALUE || weight < Constants.MIN_WEIGHT_VALUE){
            throw new RuntimeException("权重不合法。当前权重为:"+weight+
                            ",权重的最小值为"+Constants.MIN_WEIGHT_VALUE+
                            ",权重的最大值为"+Constants.MAX_WEIGHT_VALUE);
        }
    }

}
