package com.grace.client.registry.remote;

import com.grace.client.registry.core.GraceConsoleAddressManager;
import com.grace.client.registry.remote.http.RegistryHttpClientProxy;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.utils.CollectionUtils;
import com.grace.common.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 注册中心的客户端代理的委托（作用是: 这个类会根据条件选择一个RegistryClientProxy的实现类的对象进行方法调用）
 *
 * @author youzhengjie
 * @date 2023-09-28 00:57:50
 */
public class RegistryClientProxyDelegate implements RegistryClientProxy {

    private final Logger log = LoggerFactory.getLogger(RegistryClientProxyDelegate.class);
    
    private final GraceConsoleAddressManager graceConsoleAddressManager;
    
    private final RegistryHttpClientProxy httpClientProxy;
    
    private ScheduledExecutorService executorService;

    public RegistryClientProxyDelegate(String namespaceId, Properties properties) {
        this.graceConsoleAddressManager = new GraceConsoleAddressManager(properties, namespaceId);
        this.httpClientProxy =
                new RegistryHttpClientProxy(namespaceId, graceConsoleAddressManager, properties);
    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) {
        getExecuteClientProxy(instance).registerInstance(serviceName, groupName, instance);
    }

    @Override
    public void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances) {
        log.info("batchRegisterInstance instances: {} ,serviceName: {} begin.", instances, serviceName);
        if (CollectionUtils.isEmpty(instances)) {
            log.warn("batchRegisterInstance instances is Empty:{}", instances);
        }
        httpClientProxy.batchRegisterInstance(serviceName, groupName, instances);
        log.info("batchRegisterInstance instances: {} ,serviceName: {} finish.", instances, serviceName);
    }

    @Override
    public void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances) {
        log.info("batch DeregisterInstance instances: {} ,serviceName: {} begin.", instances, serviceName);
        if (CollectionUtils.isEmpty(instances)) {
            log.warn("batch DeregisterInstance instances is Empty:{}", instances);
        }
        httpClientProxy.batchDeregisterInstance(serviceName, groupName, instances);
        log.info("batch DeregisterInstance instances: {} ,serviceName: {} finish.", instances, serviceName);
    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, Instance instance) {
        getExecuteClientProxy(instance).deregisterInstance(serviceName, groupName, instance);
    }

    @Override
    public void updateInstance(String serviceName, String groupName, Instance instance) {

    }

    @Override
    public Service queryService(String serviceName, String groupName) {
        return null;
    }

    @Override
    public void createService(Service service) {

    }

    @Override
    public boolean deleteService(String serviceName, String groupName) {
        return false;
    }

    @Override
    public void updateService(Service service) {

    }

    @Override
    public PageData<String> getServiceNameList(String groupName,int page, int size) {
        return httpClientProxy.getServiceNameList(groupName,page,size);
    }

    @Override
    public List<Instance> getAllInstances(String serviceName, String groupName, boolean healthyOnly) {
        return httpClientProxy.getAllInstances(serviceName, groupName, healthyOnly);
    }

    // TODO: 2023/8/5 instance.isEphemeral()
//    private NamespaceClientProxy getExecuteClientProxy(Instance instance) {
//        return instance.isEphemeral() ? grpcClientProxy : httpClientProxy;
//    }

    private RegistryClientProxy getExecuteClientProxy(Instance instance) {
        return httpClientProxy;
    }

}
