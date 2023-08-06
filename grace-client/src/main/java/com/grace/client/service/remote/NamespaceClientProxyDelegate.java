package com.grace.client.service.remote;

import com.grace.client.core.ServerListManager;
import com.grace.client.service.remote.http.NamespaceHttpClientProxy;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.utils.CollectionUtils;
import com.grace.common.utils.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 命名空间客户端代理的委托
 *
 * @author youzhengjie
 * @date 2023/08/06 00:41:51
 */
public class NamespaceClientProxyDelegate implements NamespaceClientProxy {

    private final Logger log = LoggerFactory.getLogger(NamespaceClientProxyDelegate.class);
    
    private final ServerListManager serverListManager;
    
    private final NamespaceHttpClientProxy httpClientProxy;
    
    private ScheduledExecutorService executorService;

    public NamespaceClientProxyDelegate(String namespace, Properties properties) {

        this.serverListManager = new ServerListManager(properties, namespace);
        this.httpClientProxy = new NamespaceHttpClientProxy(namespace, serverListManager, properties);
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
    public ListView<String> getServiceList(int page, int size, String groupName) {
        return httpClientProxy.getServiceList(page, size, groupName);
    }

    // TODO: 2023/8/5 instance.isEphemeral()
//    private NamespaceClientProxy getExecuteClientProxy(Instance instance) {
//        return instance.isEphemeral() ? grpcClientProxy : httpClientProxy;
//    }

    private NamespaceClientProxy getExecuteClientProxy(Instance instance) {
        return httpClientProxy;
    }

}
