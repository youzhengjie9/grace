package com.grace.client.service;

import com.grace.common.entity.Instance;

import java.util.List;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023/06/19 20:53:09
 */
public interface NamespaceService {

    boolean createNamespace(String namespace);

    boolean createNamespace(String namespace,String desc);

    boolean createService(String serviceName);

    boolean createService(String serviceName,String namespace);

    boolean createService(String serviceName,String namespace,String groupName);

    boolean createService(String serviceName,String namespace,String groupName,String metaData);

    boolean registerInstance(String serviceName, String ipAddr, int port);

    boolean registerInstance(String serviceName, String ipAddr, int port,int weight);

    boolean registerInstance(String serviceName, String ipAddr, int port,int weight,String metaData);


    /**
     * 获取所有实例
     *
     * @param namespace   名称空间
     * @param serviceName 服务名称
     * @return {@link List}<{@link Instance}>
     */
    List<Instance> getAllInstances(String namespace,String serviceName);


    /**
     * 获取实例
     *
     * @param namespace   名称空间
     * @param serviceName 服务名称
     * @param ipAddr      ip addr
     * @param port        港口
     * @return {@link Instance}
     */
    Instance getInstance(String namespace,String serviceName,String ipAddr,int port);

}
