package com.grace.console.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.utils.Result;
import com.grace.console.dto.PublishConfigDTO;
import com.grace.console.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 配置中心控制器
 *
 * @author youzhengjie
 * @date 2023/10/17 20:35:37
 */
@RestController
@RequestMapping(ParentMappingConstants.CONFIG_CONTROLLER)
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 发布配置（创建配置和修改配置都是调用这个接口）
     *
     * @param publishConfigDTO publishConfigDTO
     * @return {@link Boolean}
     */
    @PostMapping("/publishConfig")
    public Result<Boolean> publishConfig(@RequestBody PublishConfigDTO publishConfigDTO, HttpServletRequest request){
        // 校验必填项
        publishConfigDTO.validateRequired();
        // 填充默认值
        publishConfigDTO.fillDefaultValue();
        // 发布配置
        return Result.ok(configService.publishConfig(publishConfigDTO,request));
    }




}
