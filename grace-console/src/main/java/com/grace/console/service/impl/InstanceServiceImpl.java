package com.grace.console.service.impl;

import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.console.core.GroupManager;
import com.grace.console.service.InstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

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
        // 如果命名空间不存在则创建该命名空间和分组
        groupManager.createNamespaceIfAbsent(namespaceId);
        // 如果服务不存在则创建服务
        groupManager.createEmptyServiceIfAbsent(namespaceId, groupName, serviceName);
        // 走到这里,命名空间、分组、服务全都有了,我们可以直接获取对应的服务
        Service service = groupManager.getService(namespaceId, groupName, serviceName);
        // 如果找到服务
        if(service != null){
            // 判断该实例是否是临时实例
            boolean ephemeral = instance.getEphemeral();
            // 如果是临时实例
            if(ephemeral){
                // 将该实例放到对应service的临时实例列表中
                Set<Instance> ephemeralInstances = service.getEphemeralInstances();
                return ephemeralInstances.add(instance);
            }
            // 如果是永久实例
            else {
                // 将该实例放到对应service的永久实例列表中
                Set<Instance> persistentInstances = service.getPersistentInstances();
                return persistentInstances.add(instance);
            }
        }
        // 如果失败则返回false
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
