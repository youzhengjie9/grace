package com.grace.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.entity.Service;
import com.grace.common.utils.ResponseResult;

import java.util.List;
import java.util.Set;

public interface SvcService{

    /**
     * 创建service
     *
     * @param createServiceDTO 创建服务dto
     * @return {@link ResponseResult}<{@link Boolean}>
     */
    ResponseResult<Boolean> createService(CreateServiceDTO createServiceDTO);


    /**
     * 是否在该命名空间中有该service名称
     *
     * @param namespace   命名空间名称
     * @param serviceName 服务名称
     * @return {@link ResponseResult}<{@link Boolean}>
     */
    ResponseResult<Boolean> hasService(String namespace,String serviceName);

    /**
     * 得到所有service
     *
     * @param namespace 名称空间
     * @return {@link ResponseResult}<{@link List}<{@link Service}>>
     */
    ResponseResult<List<Service>> getAllServices(String namespace);


    /**
     * 得到service
     *
     * @param namespace   名称空间
     * @param serviceName 服务名称
     * @return {@link ResponseResult}<{@link Service}>
     */
    ResponseResult<Service> getService(String namespace,String serviceName);

}
