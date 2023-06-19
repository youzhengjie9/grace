package com.grace.client.service;

import com.grace.common.utils.ResponseResult;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023/06/19 20:53:09
 */
public interface NamespaceService {


    ResponseResult<Boolean> createNamespace(String name,String desc);



}
