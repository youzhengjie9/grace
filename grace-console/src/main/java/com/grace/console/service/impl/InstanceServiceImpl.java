package com.grace.console.service.impl;

import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.utils.SnowId;
import com.grace.console.service.InstanceService;
import com.grace.console.service.NamespaceService;
import com.grace.console.service.SvcService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * instance service impl
 *
 * @author youzhengjie
 * @date 2023/07/13 17:19:51
 */
@org.springframework.stereotype.Service
public class InstanceServiceImpl implements InstanceService {

    private static final Logger log = LoggerFactory.getLogger(InstanceServiceImpl.class);

    private SvcService svcService;

    private NamespaceService namespaceService;

    /**
     * 存储instance的map（key=service的id,value=instance的集合）
     */
    private static final Map<Long, List<Instance>> instanceMap = new ConcurrentHashMap<>();

    @Autowired
    public void setSvcService(SvcService svcService) {
        this.svcService = svcService;
    }

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @Override
    public boolean registerInstance(Instance instance) {

        String serviceName = instance.getServiceName();
        // 如果命名空间名称为空
        if(StringUtils.isBlank(namespace)){
            log.error("传入的namespace名称为空");
            return false;
        }
        // 如果service名称为空
        if(StringUtils.isBlank(serviceName)){
            log.error("传入的service名称为空");
            return false;
        }
        boolean hasService = svcService.hasService(namespace, serviceName);
        // TODO: 2023/7/19 如果传入的service不存在则创建该service
        if(!hasService){
            log.error("传入的service不存在");
            return false;
        }
        // 查询service
        Service service = svcService.getService(namespace, serviceName);
        long instanceId = SnowId.nextId();
        Long serviceId = service.getId();
        instance.setId(instanceId)
                .setServiceId(serviceId)
                .setCreateTime(LocalDateTime.now());
        List<Instance> instances = instanceMap.get(serviceId);
        // 防止instances集合为空，如果instances集合为空则进行初始化集合
        if(instances == null){
            instances = new CopyOnWriteArrayList<>();
        }
        if(instances.size()>0){
            String ipAddr = instance.getIpAddr();
            int port = instance.getPort();
            for (Instance ins : instances) {
                if(ins.getIpAddr().equals(ipAddr) && ins.getPort() == port){
                    log.error("该instance已经存在");
                    return false;
                }
            }
        }
        try {
            instances.add(instance);
            // 将数据保存起来
            instanceMap.put(serviceId,instances);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Instance> getAllInstances(String namespace, String serviceName) {

        // 如果命名空间名称为空
        if(StringUtils.isBlank(namespace)){
            log.error("传入的namespace名称为空");
            return null;
        }
        // 如果service名称为空
        if(StringUtils.isBlank(serviceName)){
            log.error("传入的service名称为空");
            return null;
        }
        boolean hasService = svcService.hasService(namespace, serviceName);
        if(!hasService){
            log.error("传入的service不存在");
            return null;
        }
        // 查询service
        Service service = svcService.getService(namespace, serviceName);

        return instanceMap.get(service.getId());
    }

    @Override
    public Instance getInstance(String namespace, String serviceName, String ipAddr, int port) {

        // 如果命名空间名称为空
        if(StringUtils.isBlank(namespace)){
            log.error("传入的namespace名称为空");
            return null;
        }
        // 如果service名称为空
        if(StringUtils.isBlank(serviceName)){
            log.error("传入的service名称为空");
            return null;
        }
        boolean hasService = svcService.hasService(namespace, serviceName);
        if(!hasService){
            log.error("传入的service不存在");
            return null;
        }
        // 查询service
        Service service = svcService.getService(namespace, serviceName);

        return instanceMap.get(service.getId())
                .stream()
                .filter(instance -> instance.getIpAddr().equals(ipAddr) && instance.getPort() == port)
                .findFirst()
                .orElse(null);
    }


}
