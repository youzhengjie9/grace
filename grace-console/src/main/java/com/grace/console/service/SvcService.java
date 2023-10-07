package com.grace.console.service;

import com.grace.common.entity.Service;

import java.util.List;

public interface SvcService{


    /**
     * 创建service
     *
     * @param service service
     * @return boolean
     */
    Boolean createService(Service service);


    /**
     * 是否在该命名空间中有该service名称
     *
     * @param namespaceId 命名空间id
     * @param groupName 分组名称
     * @param serviceName 服务名称
     * @return boolean
     */
    Boolean hasService(String namespaceId,String groupName,String serviceName);

    /**
     * 分页获取service
     *
     * @param namespaceId 命名空间id
     * @param groupName 分组名称
     * @param page        page
     * @param size        size
     * @return {@link List}<{@link Service}>
     */
    List<Service> getAllServiceByPage(String namespaceId, String groupName, int page, int size);

    /**
     * 分页获取所有service名称
     *
     * @param namespaceId 命名空间id
     * @param groupName   分组名称
     * @param page        page
     * @param size        size
     * @return {@link List}<{@link String}>
     */
    List<String> getAllServiceNameByPage(long namespaceId, String groupName, int page,int size);

    /**
     * 获取service的总记录数
     *
     * @return int
     */
    int getServiceCount();

    /**
     * 获取service
     *
     * @param namespaceId 命名空间id
     * @param groupName 分组名称
     * @param serviceName 服务名称
     * @return {@link Service}
     */
    Service getService(String namespaceId,String groupName,String serviceName);

}
