package com.grace.client.registry;

import com.grace.common.dto.HeartBeat;
import com.grace.common.dto.RegisterInstanceDTO;

/**
 * 注册中心的服务,用于操作注册中心
 *
 * @author youzhengjie
 * @date 2023-09-27 23:33:58
 */
public interface RegistryService {

    /**
     * 注册实例
     *
     * @param registerInstanceDTO registerInstanceDTO
     * @return {@link Boolean}
     */
    Boolean registerInstance(RegisterInstanceDTO registerInstanceDTO);

    /**
     * 发送put类型的请求的心跳
     *
     * @param heartBeat 某个实例的心跳请求
     * @return {@link Boolean}
     */
    Boolean sendHeartBeat(HeartBeat heartBeat);

}
