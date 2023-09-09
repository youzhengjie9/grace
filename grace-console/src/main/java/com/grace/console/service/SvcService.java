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
     * @param namespace   命名空间名称
     * @param serviceName 服务名称
     * @return boolean
     */
    boolean hasService(String namespace,String serviceName);


    /**
     * 获取所有service
     *
     * @param namespace 命名空间名称
     * @return {@link List}<{@link Service}>
     */
    List<Service> getAllServices(String namespace);


    /**
     * 获取service
     *
     * @param namespace   命名空间名称
     * @param serviceName 服务名称
     * @return {@link Service}
     */
    Service getService(String namespace,String serviceName);

}