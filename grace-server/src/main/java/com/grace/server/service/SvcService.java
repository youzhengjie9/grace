package com.grace.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.entity.SysService;
import com.grace.common.utils.ResponseResult;
import org.springframework.web.bind.annotation.RequestBody;

public interface SvcService extends IService<SysService> {

    ResponseResult<Boolean> createService(CreateServiceDTO createServiceDTO);

}
