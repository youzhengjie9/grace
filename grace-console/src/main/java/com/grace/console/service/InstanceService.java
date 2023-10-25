package com.grace.console.service;

import com.grace.common.dto.HeartBeat;
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
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param serviceName serviceName
     * @param instance    实例
     * @return boolean
     */
    boolean registerInstance(String namespaceId,String groupName,String serviceName,Instance instance);

    /**
     * 处理心跳请求
     *
     * @param heartBeat
     */
    void processHeartBeat(HeartBeat heartBeat);

    /**
     * 修改实例
     *
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param serviceName serviceName
     * @param instance 实例
     * @return boolean
     */
    boolean modifyInstance(String namespaceId,String groupName,String serviceName,Instance instance);

    /**
     * 获取所有实例
     *
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param serviceName serviceName
     * @return {@link List}<{@link Instance}>
     */
    List<Instance> getAllInstances(String namespaceId,String groupName,String serviceName);


    /**
     * 获取实例
     *
     * @param namespaceId namespaceId
     * @param groupName groupName
     * @param serviceName 服务名称
     * @param ipAddr      ip addr
     * @param port        港口
     * @return {@link Instance}
     */
    Instance getInstance(String namespaceId,String groupName,String serviceName,String ipAddr,int port);

    /**
     * 获取实例
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName 服务名称
     * @param instanceId instanceId
     * @return {@link Instance}
     */
    Instance getInstance(String namespaceId,String groupName,String serviceName,String instanceId);

}
