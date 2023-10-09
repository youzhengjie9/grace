package com.grace.console.service.impl;

import com.grace.common.entity.Instance;
import com.grace.console.service.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * instance service impl
 *
 * @author youzhengjie
 * @date 2023/07/13 17:19:51
 */
@org.springframework.stereotype.Service
public class InstanceServiceImpl implements InstanceService {

    private static final Logger log = LoggerFactory.getLogger(InstanceServiceImpl.class);

    @Override
    public boolean registerInstance(Instance instance) {
        return false;
    }

    @Override
    public List<Instance> getAllInstances(String namespace, String serviceName) {

      return null;
    }

    @Override
    public Instance getInstance(String namespace, String serviceName, String ipAddr, int port) {

      return null;
    }


}
