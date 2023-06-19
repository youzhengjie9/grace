package com.grace.server.service;

import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.utils.ResponseResult;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023-06-19 22:42:58
 */
public interface NamespaceService {


    ResponseResult<Boolean> createNamespace(CreateNamespaceDTO createNamespaceDTO);


}
