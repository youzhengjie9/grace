package com.grace.client.service;

import com.grace.common.utils.ResponseResult;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023/06/19 20:53:09
 */
public interface NamespaceService {

    ResponseResult<Boolean> createNamespace(String namespace);

    ResponseResult<Boolean> createNamespace(String namespace,String desc);

    ResponseResult<Boolean> createService(String serviceName);

    ResponseResult<Boolean> createService(String serviceName,String namespace);

    ResponseResult<Boolean> createService(String serviceName,String namespace,String groupName);

    ResponseResult<Boolean> createService(String serviceName,String namespace,String groupName,String metaData);

    ResponseResult<Boolean> registerInstance(String serviceName, String ipAddr, int port);

    ResponseResult<Boolean> registerInstance(String serviceName, String ipAddr, int port,int weight);

    ResponseResult<Boolean> registerInstance(String serviceName, String ipAddr, int port,int weight,String metaData);

}
