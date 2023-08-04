package com.grace.client.service.impl;

import com.grace.client.service.NamespaceService;
import com.grace.common.constant.PropertiesKeyConstant;
import com.grace.common.constant.PropertiesValueConstant;
import com.grace.common.entity.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;

public class GraceNamespaceService implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(GraceNamespaceService.class);

    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    private static final int DEFAULT_WEIGHT = 1;

    private String serverAddr;

    private String namespace;

    private String namespaceDesc;

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
        log.info(">>>>>>GraceNamespaceService开始初始化<<<<<<");
        this.serverAddr = properties.getProperty(PropertiesKeyConstant.SERVER_ADDR);
        this.namespace = properties.getProperty(PropertiesKeyConstant.NAMESPACE);
        String autoCreateNamespace = properties.getProperty(PropertiesKeyConstant.AUTO_CREATE_NAMESPACE, PropertiesValueConstant.OFF);
        //如果开启了自动创建命名空间
        if(autoCreateNamespace.equals(PropertiesValueConstant.ON)) {
            //如果该namespace没有被创建则创建
            if (!hasNamespace(namespace)) {
                this.namespaceDesc = properties.getProperty(PropertiesKeyConstant.NAMESPACE_DESC, "");
                boolean createNamespaceSuccess = createNamespace(namespace, namespaceDesc);
                log.info("命名空间:" + namespace + "创建" + (createNamespaceSuccess ? "成功" : "失败"));
            }
        }
        log.info(">>>>>>GraceNamespaceService初始化成功<<<<<<");
    }

    @Override
    public void registerInstance(String serviceName, String ipAddr, int port) {

    }

    @Override
    public void registerInstance(String serviceName, String groupName, String ipAddr, int port) {

    }

    @Override
    public void registerInstance(String serviceName, String ipAddr, int port, int weight) {

    }

    @Override
    public void registerInstance(String serviceName, String groupName, String ipAddr, int port, int weight) {

    }

    @Override
    public void registerInstance(String serviceName, String ipAddr, int port, int weight, String metaData) {

    }

    @Override
    public void registerInstance(String serviceName, String groupName, String ipAddr, int port, int weight, String metaData) {

    }

    @Override
    public void registerInstance(String serviceName, Instance instance) {

    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) {

    }

    @Override
    public void batchRegisterInstance(String serviceName, List<Instance> instances) {

    }

    @Override
    public void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public void batchDeregisterInstance(String serviceName, List<Instance> instances) {

    }

    @Override
    public void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public void deregisterInstance(String serviceName, String ipAddr, int port) {

    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, String ipAddr, int port) {

    }

    @Override
    public void deregisterInstance(String serviceName, String ipAddr, int port, int weight) {

    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, String ipAddr, int port, int weight) {

    }

    @Override
    public void deregisterInstance(String serviceName, String ipAddr, int port, int weight, String metaData) {

    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, String ipAddr, int port, int weight, String metaData) {

    }

    @Override
    public void deregisterInstance(String serviceName, Instance instance) {

    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, Instance instance) {

    }

    @Override
    public List<Instance> getAllInstances(String serviceName) {
        return null;
    }

    @Override
    public List<Instance> getAllInstances(String serviceName, String groupName) {
        return null;
    }

    @Override
    public Instance getInstance(String serviceName, String groupName, String ipAddr, int port) {
        return null;
    }

    @Override
    public List<Instance> selectInstances(String serviceName, boolean healthy) {
        return null;
    }

    @Override
    public List<Instance> selectInstances(String serviceName, String groupName, boolean healthy) {
        return null;
    }

    @Override
    public Instance selectOneHealthyInstance(String serviceName) {
        return null;
    }

    @Override
    public Instance selectOneHealthyInstance(String serviceName, String groupName) {
        return null;
    }
}
