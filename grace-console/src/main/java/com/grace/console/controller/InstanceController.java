package com.grace.console.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.entity.Instance;
import com.grace.common.entity.builder.InstanceBuilder;
import com.grace.common.utils.Result;
import com.grace.console.dto.InstanceDTO;
import com.grace.console.service.InstanceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping(path = "/registerInstance")
    public Result<Boolean> registerInstance(InstanceDTO instanceDTO){
        // 校验InstanceDTO对象的必填属性是否为空,为空则抛出异常
        instanceDTO.validateRequired();
        // 填充默认值（注意: 不会填充必填属性！）
        instanceDTO.fillDefaultValue();
        // 通过InstanceDTO对象构建实例（Instance）
        Instance instance = buildInstance(instanceDTO);

        return Result.ok(instanceService.registerInstance(instance));
    }

    /**
     * 通过InstanceDTO对象构建实例（Instance）
     *
     * @param instanceDTO instanceDTO
     * @return {@link Instance}
     */
    private Instance buildInstance(InstanceDTO instanceDTO) {
        return InstanceBuilder.newBuilder()
                .serviceName(instanceDTO.getServiceName())
                .ipAddr(instanceDTO.getIpAddr())
                .port(instanceDTO.getPort())
                .weight(instanceDTO.getWeight())
                .healthy(instanceDTO.getHealthy())
                .ephemeral(instanceDTO.getEphemeral())
                .metadata(parseMetadata(instanceDTO.getMetadata()))
                .createTime(LocalDateTime.now())
                .build();
    }

    /**
     * 将String类型的metadata转成Map类型的metadata
     */
    public static Map<String, String> parseMetadata(String metadata) {

        Map<String, String> metadataMap = new HashMap<>(16);

        if (StringUtils.isBlank(metadata)) {
            return metadataMap;
        }

        // TODO: 2023/10/5 暂未实现

        return metadataMap;
    }

}
