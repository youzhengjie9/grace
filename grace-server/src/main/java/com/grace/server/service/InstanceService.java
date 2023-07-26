package com.grace.server.service;

import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.Instance;

import java.util.List;

/**
 * instance service
 *
 * @author youzhengjie
 * @date 2023/07/13 17:19:14
 */
public interface InstanceService {


    /**
     * 注册实例
     *
     * @param registerInstanceDTO 注册实例dto
     * @return boolean
     */
    boolean registerInstance(RegisterInstanceDTO registerInstanceDTO);


    /**
     * 获取所有实例
     *
     * @param namespace   名称空间
     * @param serviceName 服务名称
     * @return {@link List}<{@link Instance}>
     */
    List<Instance> getAllInstances(String namespace, String serviceName);


    /**
     * 获取实例
     *
     * @param namespace   名称空间
     * @param serviceName 服务名称
     * @param ipAddr      ip addr
     * @param port        港口
     * @return {@link Instance}
     */
    Instance getInstance(String namespace, String serviceName, String ipAddr, int port);


}
