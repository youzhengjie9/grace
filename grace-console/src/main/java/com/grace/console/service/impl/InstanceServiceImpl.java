package com.grace.console.service.impl;

import com.grace.common.entity.Instance;
import com.grace.console.core.GroupManager;
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

    private final GroupManager groupManager = GroupManager.getGroupManagerSingleton();

    @Override
    public boolean registerInstance(String namespaceId,String groupName,String serviceName,Instance instance) {
        return false;
    }

    @Override
    public boolean modifyInstance(String namespaceId,String groupName,String serviceName,Instance instance) {
        String instanceId = instance.getInstanceId();
        Instance ins = groupManager.getInstance(namespaceId, groupName, serviceName, instanceId);
        // 如果找到该instance
        if(ins != null){
            // 修改实例
            ins.setWeight(instance.getWeight());
            ins.setHealthy(instance.getHealthy());
            ins.setOnline(instance.getOnline());
            ins.setMetadata(instance.getMetadata());
            return true;
        }
        log.warn("修改instance失败。namespaceId={},groupName={},serviceName={},{}",
                namespaceId,groupName,serviceName,instance);
        return false;
    }

    @Override
    public List<Instance> getAllInstances(String namespaceId,String groupName, String serviceName) {

      return null;
    }

    @Override
    public Instance getInstance(String namespaceId,String groupName, String serviceName, String ipAddr, int port) {

      return null;
    }

    @Override
    public Instance getInstance(String namespaceId, String groupName, String serviceName, String instanceId) {
        return null;
    }


}
