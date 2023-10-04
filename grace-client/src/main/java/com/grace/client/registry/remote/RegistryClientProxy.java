package com.grace.client.registry.remote;

import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.utils.PageData;

import java.util.List;

/**
 * 注册中心的客户端代理（作用是: 向注册中心发送api请求），注意: 所谓的客户端代理就是用来发送api请求的。
 *
 * @author youzhengjie
 * @date 2023/09/28 00:50:24
 */
public interface RegistryClientProxy {

    /**
     * 注册实例
     *
     * @param serviceName serviceName
     * @param groupName groupName
     * @param instance instance
     */
    void registerInstance(String serviceName, String groupName, Instance instance);

    /**
     * 批量注册实例
     *
     * @param serviceName serviceName
     * @param groupName groupName
     * @param instances instances
     */
    void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances);

    /**
     * 注销实例
     *
     * @param serviceName serviceName
     * @param groupName groupName
     * @param instance instance
     */
    void deregisterInstance(String serviceName, String groupName, Instance instance);

    /**
     * 批量注销实例
     *
     * @param serviceName serviceName
     * @param groupName groupName
     * @param instances instances
     */
    void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances);

    /**
     * 修改实例
     *
     * @param serviceName service name
     * @param groupName   group name
     * @param instance    instance
     */
    void updateInstance(String serviceName, String groupName, Instance instance);

    /**
     * 获取所有实例
     *
     * @param serviceName 服务名称
     * @param groupName   分组名称
     * @param onlyHealthy 是否只挑选健康的实例
     * @return {@link List}<{@link Instance}>
     */
    List<Instance> getAllInstances(String serviceName, String groupName , boolean onlyHealthy);

    /**
     * 查询Service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @return service
     */
    Service queryService(String serviceName, String groupName);

    /**
     * 创建Service
     *
     * @param service  service
     */
    void createService(Service service);

    /**
     * 删除Service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @return true if delete ok
     */
    boolean deleteService(String serviceName, String groupName);


    /**
     * 修改Service
     *
     * @param service  service
     */
    void updateService(Service service);

    /**
     * 分页获取服务名称列表
     *
     * @param groupName 分组名称
     * @param page      页面
     * @param size      大小
     * @return {@link PageData}<{@link String}>
     */
    PageData<String> getServiceNameList(String groupName, int page, int size);


}
