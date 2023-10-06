package com.grace.client.registry;

import com.grace.client.registry.remote.RegistryClientProxy;
import com.grace.client.registry.remote.RegistryClientProxyDelegate;
import com.grace.common.constant.Constants;
import com.grace.common.constant.PropertiesKeyConstants;
import com.grace.common.entity.Instance;
import com.grace.common.utils.CollectionUtils;
import com.grace.common.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 注册中心的服务的实现类（用户操作注册中心就是使用这个类！）
 *
 * @author youzhengjie
 * @date 2023/09/28 00:46:55
 */
public class GraceRegistryService implements RegistryService {

    private static final Logger log = LoggerFactory.getLogger(GraceRegistryService.class);

    /**
     * 当前操作的命名空间id
     * <p>
     * 基本思想就是用户传命名空间名称到GraceRegistryService类中,
     * 在GraceRegistryService类中的初始化方法通过命名空间名称查询命名空间id并保存起来传给clientProxy类
     */
    private Long namespaceId;

    /**
     * 注册中心的客户端代理（作用是: 向注册中心发送api请求）
     */
    private RegistryClientProxy clientProxy;


    public GraceRegistryService(String consoleAddress) {
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstants.CONSOLE_ADDRESS,consoleAddress);
        properties.setProperty(PropertiesKeyConstants.NAMESPACE_NAME, Constants.DEFAULT_NAMESPACE_NAME);
        init(properties);
    }

    public GraceRegistryService(String consoleAddress, String namespaceName) {
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstants.CONSOLE_ADDRESS,consoleAddress);
        properties.setProperty(PropertiesKeyConstants.NAMESPACE_DESC,namespaceName);
        init(properties);
    }

    public GraceRegistryService(Properties properties) {
        init(properties);
    }

    /**
     * 初始化方法
     *
     * @param properties 属性
     */
    private void init(Properties properties) {
        String namespaceName = properties.getProperty(PropertiesKeyConstants.NAMESPACE_NAME);
        // 如果命名空间名称为public
        if(namespaceName.equalsIgnoreCase(Constants.DEFAULT_NAMESPACE_NAME)){
            this.namespaceId = Constants.DEFAULT_NAMESPACE_ID;
        }
        // 如果命名空间名称不为public
        else {
            // TODO: 2023/9/28  根据命名空间名称去数据库中查询命名空间id
            this.namespaceId = 666666666L;
        }
        this.clientProxy = new RegistryClientProxyDelegate(this.namespaceId, properties);
    }


    @Override
    public void registerInstance(String serviceName, String ipAddr, int port) {
        registerInstance(serviceName, Constants.DEFAULT_GROUP_NAME, ipAddr, port);
    }

    @Override
    public void registerInstance(String serviceName, String groupName, String ipAddr, int port) {
        registerInstance(serviceName, groupName, ipAddr, port,null);
    }

    @Override
    public void registerInstance(String serviceName, String ipAddr, int port, Map<String, String> metadata){
        registerInstance(serviceName, Constants.DEFAULT_GROUP_NAME, ipAddr, port,metadata);
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
        registerInstance(serviceName, Constants.DEFAULT_GROUP_NAME, instance);
    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) {
        clientProxy.registerInstance(serviceName, groupName, instance);
    }

    @Override
    public void batchRegisterInstance(String serviceName, List<Instance> instances) {
        clientProxy.batchRegisterInstance(serviceName, Constants.DEFAULT_GROUP_NAME, instances);
    }

    @Override
    public void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances) {
        clientProxy.batchRegisterInstance(serviceName, groupName, instances);
    }

    @Override
    public void batchDeregisterInstance(String serviceName, List<Instance> instances) {
        clientProxy.batchDeregisterInstance(serviceName, Constants.DEFAULT_GROUP_NAME, instances);
    }

    @Override
    public void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances) {
        clientProxy.batchDeregisterInstance(serviceName, groupName, instances);
    }

    @Override
    public void deregisterInstance(String serviceName, String ipAddr, int port) {
        deregisterInstance(serviceName, Constants.DEFAULT_GROUP_NAME, ipAddr, port, null);
    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, String ipAddr, int port) {
        deregisterInstance(serviceName, groupName, ipAddr, port, null);
    }

    @Override
    public void deregisterInstance(String serviceName, String ipAddr, int port, Map<String, String> metadata) {
        deregisterInstance(serviceName, Constants.DEFAULT_GROUP_NAME, ipAddr, port, metadata);
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
        deregisterInstance(serviceName, Constants.DEFAULT_GROUP_NAME, instance);
    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, Instance instance) {
        clientProxy.deregisterInstance(serviceName, groupName, instance);
    }

    @Override
    public List<Instance> getAllInstances(String serviceName) {
        return clientProxy.getAllInstances(serviceName, Constants.DEFAULT_GROUP_NAME,false);
    }

    @Override
    public List<Instance> getAllInstances(String serviceName, String groupName) {
        return clientProxy.getAllInstances(serviceName, groupName,false);
    }

    @Override
    public List<Instance> selectInstances(String serviceName, boolean healthy) {

        return selectInstances(serviceName,Constants.DEFAULT_GROUP_NAME,healthy);
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
        return selectOneHealthyInstance(serviceName,Constants.DEFAULT_GROUP_NAME);
    }

    @Override
    public Instance selectOneHealthyInstance(String serviceName, String groupName) {
        List<Instance> instances = clientProxy.getAllInstances(serviceName, groupName, false);

        return null;
    }

    @Override
    public PageData<String> getServiceNameList(int page, int size) {
        return getServiceNameList(Constants.DEFAULT_GROUP_NAME,page,size);
    }

    @Override
    public PageData<String> getServiceNameList(String groupName,int page, int size) {
        return clientProxy.getServiceNameList(groupName, page, size);
    }
}
