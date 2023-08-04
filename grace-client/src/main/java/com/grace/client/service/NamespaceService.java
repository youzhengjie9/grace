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


    void registerInstance(String serviceName, String ipAddr, int port);

    void registerInstance(String serviceName, String groupName, String ipAddr, int port);

    void registerInstance(String serviceName, String ipAddr, int port,int weight);

    void registerInstance(String serviceName, String groupName, String ipAddr, int port,int weight);

    void registerInstance(String serviceName, String ipAddr, int port,int weight,String metaData);

    void registerInstance(String serviceName, String groupName, String ipAddr, int port,int weight,String metaData);

    void registerInstance(String serviceName, Instance instance);

    void registerInstance(String serviceName, String groupName, Instance instance);

    void batchRegisterInstance(String serviceName, List<Instance> instances);

    void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances);

    void batchDeregisterInstance(String serviceName, List<Instance> instances);

    void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances);

    void deregisterInstance(String serviceName, String ipAddr, int port);

    void deregisterInstance(String serviceName, String groupName, String ipAddr, int port);

    void deregisterInstance(String serviceName, String ipAddr, int port,int weight);

    void deregisterInstance(String serviceName, String groupName, String ipAddr, int port,int weight);

    void deregisterInstance(String serviceName, String ipAddr, int port,int weight,String metaData);

    void deregisterInstance(String serviceName, String groupName, String ipAddr, int port,int weight,String metaData);

    void deregisterInstance(String serviceName, Instance instance);

    void deregisterInstance(String serviceName, String groupName, Instance instance);

    /**
     * 获取所有实例
     *
     * @param serviceName 服务名称
     * @return {@link List}<{@link Instance}>
     */
    List<Instance> getAllInstances(String serviceName);

    /**
     * 获取所有实例
     *
     * @param serviceName 服务名称
     * @return {@link List}<{@link Instance}>
     */
    List<Instance> getAllInstances(String serviceName, String groupName);

    /**
     * 获取实例
     *
     * @param serviceName 服务名称
     * @param ipAddr      ip addr
     * @param port        港口
     * @return {@link Instance}
     */
    Instance getInstance(String serviceName, String groupName ,String ipAddr,int port);


    /**
     * Get qualified instances of service.
     *
     * @param serviceName name of service.
     * @param healthy     a flag to indicate returning healthy or unhealthy instances
     * @return A qualified list of instance
     */
    List<Instance> selectInstances(String serviceName, boolean healthy);

    /**
     * Get qualified instances of service.
     *
     * @param serviceName name of service
     * @param groupName   group of service
     * @param healthy     a flag to indicate returning healthy or unhealthy instances
     * @return A qualified list of instance
     */
    List<Instance> selectInstances(String serviceName, String groupName, boolean healthy);


    /**
     * Select one healthy instance of service using predefined load balance strategy.
     *
     * @param serviceName name of service
     * @return qualified instance
     */
    Instance selectOneHealthyInstance(String serviceName);

    /**
     * Select one healthy instance of service using predefined load balance strategy.
     *
     * @param serviceName name of service
     * @param groupName   group of service
     * @return qualified instance
     */
    Instance selectOneHealthyInstance(String serviceName, String groupName);


//    void createNamespace(String namespace);
//
//    void createNamespace(String namespace,String desc);
//
//    boolean createService(String serviceName);
//
//    boolean createService(String serviceName,String namespace);
//
//    boolean createService(String serviceName,String namespace,String groupName);
//
//    boolean createService(String serviceName,String namespace,String groupName,String metaData);


}
