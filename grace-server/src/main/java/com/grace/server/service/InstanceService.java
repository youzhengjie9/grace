package com.grace.server.service;

import com.grace.common.dto.RegisterInstanceDTO;

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


}
