package com.grace.client.registry;

import com.grace.common.entity.Instance;
import com.grace.common.utils.PageData;

import java.util.List;
import java.util.Map;

/**
 * 注册中心的服务,用于操作注册中心
 *
 * @author youzhengjie
 * @date 2023-09-27 23:33:58
 */
public interface RegistryService {

    void registerInstance(String serviceName, String ipAddr, int port);

    void registerInstance(String serviceName, String groupName, String ipAddr, int port);

    void registerInstance(String serviceName, String ipAddr, int port, Map<String,String> metadata);

    void registerInstance(String serviceName, String groupName, String ipAddr, int port, Map<String,String> metadata);

    void registerInstance(String serviceName, Instance instance);

    /**
     * 将实例注册到注册中心上
     *
     * @param serviceName 服务名称
     * @param groupName 分组名称
     * @param instance 实例
     */
    void registerInstance(String serviceName, String groupName, Instance instance);

    void batchRegisterInstance(String serviceName, List<Instance> instances);

    void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances);

    void batchDeregisterInstance(String serviceName, List<Instance> instances);

    void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances);

    void deregisterInstance(String serviceName, String ipAddr, int port);

    void deregisterInstance(String serviceName, String groupName, String ipAddr, int port);

    void deregisterInstance(String serviceName, String ipAddr, int port,Map<String,String> metadata);

    void deregisterInstance(String serviceName, String groupName, String ipAddr, int port,Map<String,String> metadata);

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

    /**
     * 分页获取服务名称列表
     *
     * @param page   page index
     * @param size page size
     * @return list of service names
     */
    PageData<String> getServiceNameList(int page, int size);

    /**
     * 分页获取服务名称列表
     *
     * @param page   page index
     * @param size page size
     * @param groupName group name
     * @return list of service names
     */
    PageData<String> getServiceNameList(String groupName,int page, int size);



}
