package com.grace.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.dto.CreateSysNamespaceDTO;
import com.grace.common.entity.SysNamespace;
import com.grace.common.utils.ResponseResult;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023-06-19 22:42:58
 */
public interface SysNamespaceService extends IService<SysNamespace> {


    ResponseResult<Boolean> createNamespace(CreateSysNamespaceDTO createSysNamespaceDTO);


}
