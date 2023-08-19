package com.grace.client.service.remote;

import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.exception.GraceException;
import com.grace.common.utils.ListView;

import java.util.List;

public interface NamespaceClientProxy {

    void registerInstance(String serviceName, String groupName, Instance instance) throws GraceException;

    void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances);

    void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances);

    void deregisterInstance(String serviceName, String groupName, Instance instance);

    /**
     * Update instance to service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @param instance    instance
     */
    void updateInstance(String serviceName, String groupName, Instance instance);

    /**
     * Query Service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @return service
     */
    Service queryService(String serviceName, String groupName);

    /**
     * Create service.
     *
     * @param service  service
     */
    void createService(Service service);

    /**
     * Delete service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @return true if delete ok
     */
    boolean deleteService(String serviceName, String groupName);


    /**
     * Update service.
     *
     * @param service  service
     */
    void updateService(Service service);

    /**
     * 分页获取服务名称列表
     *
     * @param page      页面
     * @param size      大小
     * @param groupName 分组名称
     * @return {@link ListView}<{@link String}>
     */
    ListView<String> getServiceNameList(int page, int size, String groupName);

    /**
     * 获取所有实例
     *
     * @param serviceName 服务名称
     * @param groupName   分组名称
     * @param healthyOnly 是否只挑选健康的实例
     * @return {@link List}<{@link Instance}>
     */
    List<Instance> getAllInstances(String serviceName, String groupName , boolean healthyOnly);


}
