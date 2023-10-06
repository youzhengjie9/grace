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
    boolean createService(Service service);


    /**
     * 是否在该命名空间中有该service名称
     *
     * @param namespaceId   命名空间id
     * @param serviceName 服务名称
     * @return boolean
     */
    boolean hasService(long namespaceId,String serviceName);

    /**
     * 分页获取service
     *
     * @param namespaceId 命名空间id
     * @param page page
     * @param size size
     * @return {@link List}<{@link Service}>
     */
    List<Service> getAllServiceByPage(long namespaceId, int page, int size);

    /**
     * 分页获取所有service名称
     *
     * @param namespaceId 命名空间id
     * @param page      page
     * @param size      size
     * @return {@link List}<{@link String}>
     */
    List<String> getAllServiceNameByPage(long namespaceId,int page,int size);

    /**
     * 获取service的总记录数
     *
     * @return int
     */
    int getServiceTotalCount();

    /**
     * 获取service
     *
     * @param namespaceId   命名空间id
     * @param serviceName 服务名称
     * @return {@link Service}
     */
    Service getService(long namespaceId,String serviceName);

}
