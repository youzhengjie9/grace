package com.grace.client.service.impl;

import com.grace.client.service.NamespaceService;
import com.grace.client.service.remote.NamespaceClientProxy;
import com.grace.client.service.remote.NamespaceClientProxyDelegate;
import com.grace.common.constant.PropertiesKeyConstant;
import com.grace.common.entity.Instance;
import com.grace.common.utils.CollectionUtils;
import com.grace.common.utils.ListView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GraceNamespaceService implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(GraceNamespaceService.class);

    private static final String DEFAULT_NAMESPACE = "DEFAULT";

    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    private String namespace;

    private NamespaceClientProxy clientProxy;


    public GraceNamespaceService(String serverAddr) {
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstant.SERVER_ADDR,serverAddr);
        properties.setProperty(PropertiesKeyConstant.NAMESPACE,DEFAULT_NAMESPACE);
        init(properties);
    }

    public GraceNamespaceService(String serverAddr,String namespace) {
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstant.SERVER_ADDR,serverAddr);
        properties.setProperty(PropertiesKeyConstant.NAMESPACE,namespace);
        init(properties);
    }

    public GraceNamespaceService(Properties properties) {
        init(properties);
    }

    /**
     * 初始化方法
     *
     * @param properties 属性
     */
    private void init(Properties properties) {
        this.namespace = properties.getProperty(PropertiesKeyConstant.NAMESPACE);
//        String autoCreateNamespace = properties.getProperty(PropertiesKeyConstant.AUTO_CREATE_NAMESPACE, PropertiesValueConstant.OFF);
//        //如果开启了自动创建命名空间
//        if(autoCreateNamespace.equals(PropertiesValueConstant.ON)) {
//            //如果该namespace没有被创建则创建
//            if (!hasNamespace(namespace)) {
//                this.namespaceDesc = properties.getProperty(PropertiesKeyConstant.NAMESPACE_DESC, "");
//                boolean createNamespaceSuccess = createNamespace(namespace, namespaceDesc);
//                log.info("命名空间:" + namespace + "创建" + (createNamespaceSuccess ? "成功" : "失败"));
//            }
//        }
        this.clientProxy = new NamespaceClientProxyDelegate(this.namespace, properties);
    }


    @Override
    public void registerInstance(String serviceName, String ipAddr, int port) {
        registerInstance(serviceName, DEFAULT_GROUP, ipAddr, port);
    }

    @Override
    public void registerInstance(String serviceName, String groupName, String ipAddr, int port) {
        registerInstance(serviceName, groupName, ipAddr, port,null);
    }

    @Override
    public void registerInstance(String serviceName, String ipAddr, int port, Map<String, String> metadata) {
        registerInstance(serviceName, DEFAULT_GROUP, ipAddr, port,metadata);
    }

    @Override
    public void registerInstance(String serviceName, String groupName, String ipAddr, int port, Map<String, String> metadata) {
        Instance instance = Instance.builder()
                .ipAddr(ipAddr)
                .port(port)
                .metadata(metadata)
                .build();
        registerInstance(serviceName,groupName,instance);
    }

    @Override
    public void registerInstance(String serviceName, Instance instance) {
        registerInstance(serviceName, DEFAULT_GROUP, instance);
    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) {
        clientProxy.registerInstance(serviceName, groupName, instance);
    }

    @Override
    public void batchRegisterInstance(String serviceName, List<Instance> instances) {
        clientProxy.batchRegisterInstance(serviceName, DEFAULT_GROUP, instances);
    }

    @Override
    public void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances) {
        clientProxy.batchRegisterInstance(serviceName, groupName, instances);
    }

    @Override
    public void batchDeregisterInstance(String serviceName, List<Instance> instances) {
        clientProxy.batchDeregisterInstance(serviceName, DEFAULT_GROUP, instances);
    }

    @Override
    public void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances) {
        clientProxy.batchDeregisterInstance(serviceName, groupName, instances);
    }

    @Override
    public void deregisterInstance(String serviceName, String ipAddr, int port) {
        deregisterInstance(serviceName, DEFAULT_GROUP, ipAddr, port, null);
    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, String ipAddr, int port) {
        deregisterInstance(serviceName, groupName, ipAddr, port, null);
    }

    @Override
    public void deregisterInstance(String serviceName, String ipAddr, int port, Map<String, String> metadata) {
        deregisterInstance(serviceName, DEFAULT_GROUP, ipAddr, port, metadata);
    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, String ipAddr, int port, Map<String, String> metadata) {

        Instance instance = Instance.builder()
                .ipAddr(ipAddr)
                .port(port)
                .metadata(metadata)
                .build();

        deregisterInstance(serviceName, groupName, instance);
    }


    @Override
    public void deregisterInstance(String serviceName, Instance instance) {
        deregisterInstance(serviceName, DEFAULT_GROUP, instance);
    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, Instance instance) {
        clientProxy.deregisterInstance(serviceName, groupName, instance);
    }

    @Override
    public List<Instance> getAllInstances(String serviceName) {
        return clientProxy.getAllInstances(serviceName, DEFAULT_GROUP,false);
    }

    @Override
    public List<Instance> getAllInstances(String serviceName, String groupName) {
        return clientProxy.getAllInstances(serviceName, groupName,false);
    }

    @Override
    public List<Instance> selectInstances(String serviceName, boolean healthy) {

        return selectInstances(serviceName,DEFAULT_GROUP,healthy);
    }

    @Override
    public List<Instance> selectInstances(String serviceName, String groupName, boolean healthy) {
        List<Instance> instances = clientProxy.getAllInstances(serviceName, groupName, false);
        // TODO: 2023/8/7 从查询出来的实例中挑选符合自己条件的实例
        List<Instance> selectedInstances;
        if(CollectionUtils.isEmpty(selectedInstances = instances)){
            return new ArrayList<>();
        }
        // 如果instances不为空
        Iterator<Instance> iterator = selectedInstances.iterator();
        while (iterator.hasNext()){
            Instance instance = iterator.next();
            // 符合这些条件将会被挑选出来
            if (healthy != instance.isHealthy() || instance.getWeight() <= 0) {
                iterator.remove();
            }
        }
        return selectedInstances;
    }

    @Override
    public Instance selectOneHealthyInstance(String serviceName) {
        return selectOneHealthyInstance(serviceName,DEFAULT_GROUP);
    }

    @Override
    public Instance selectOneHealthyInstance(String serviceName, String groupName) {
        List<Instance> instances = clientProxy.getAllInstances(serviceName, groupName, false);

        return null;
    }

    @Override
    public ListView<String> getServiceNameList(int page, int size) {
        return getServiceNameList(page, size , DEFAULT_GROUP);
    }

    @Override
    public ListView<String> getServiceNameList(int page, int size, String groupName) {
        return clientProxy.getServiceNameList(page,size,groupName);
    }
}
