package com.grace.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.SysInstance;
import com.grace.common.utils.ResponseResult;

/**
 * instance service
 *
 * @author youzhengjie
 * @date 2023/07/13 17:19:14
 */
public interface InstanceService extends IService<SysInstance> {


    ResponseResult<Boolean> registerInstance(RegisterInstanceDTO registerInstanceDTO);

}
