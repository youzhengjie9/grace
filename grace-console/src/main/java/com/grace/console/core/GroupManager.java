package com.grace.console.core;

import com.grace.common.entity.Group;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.entity.builder.ServiceBuilder;
import com.grace.common.executor.NameThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 分组管理器（用于管理group）
 *
 * @author youzhengjie
 * @date 2023-10-06 15:23:45
 */
public class GroupManager {

    private static final Logger log = LoggerFactory.getLogger(GroupManager.class);

    private static final GroupManager INSTANCE = new GroupManager();

    /**
     * 这里体现了一个命名空间可以有多个分组、一个分组可以有多个服务、一个服务可以有多个实例（分为临时实例和永久实例）
     */
    private final Map<String, Set<Group>> groupMap;


    private GroupManager() {
        // 初始化默认容量为1<<2（也就是为 4）
        groupMap = new ConcurrentHashMap<>(1 << 2);
    }

    public static GroupManager getInstance() {
        return INSTANCE;
    }

    /**
     * 获取所有命名空间的id
     *
     * @return {@link Set}<{@link String}>
     */
    public Set<String> getAllNamespaceId() {
        return groupMap.keySet();
    }

    /**
     * 根据namespaceId获取该命名空间下面的所有分组名称
     *
     * @param namespaceId
     * @return {@link Set}<{@link String}>
     */
    public Set<String> getAllGroupName(String namespaceId) {
        Set<String> groupNames = new CopyOnWriteArraySet<>();
        Set<Group> groups = groupMap.get(namespaceId);
        for (Group group : groups) {
            groupNames.add(group.getGroupName());
        }
        return groupNames;
    }

    /**
     * 根据namespaceId获取该命名空间下面的所有service名称
     *
     * @param namespaceId namespaceId
     * @return {@link Set}<{@link String}>
     */
    public Set<String> getAllServiceName(String namespaceId) {
        Set<String> serviceNames = new CopyOnWriteArraySet<>();
        // 根据namespaceId获取该命名空间下面的所有分组
        Set<Group> groups = groupMap.get(namespaceId);
        // 遍历分组集合
        for (Group group : groups) {
            // 获取每一个分组下面的所有service
            Set<Service> services = group.getServices();
            for (Service service : services) {
                String serviceName = service.getServiceName();
                // 将service名称放到一个集合中存储起来
                serviceNames.add(serviceName);
            }
        }
        return serviceNames;
    }

    /**
     * 获取所有命名空间下面的所有service名称
     *
     * @return {@link Map}<{@link String},{@link Set}<{@link String}>>
     */
    public Map<String, Set<String>> getAllServiceName() {
        // key=namespaceId,value=该命名空间下面的所有service名称
        Map<String, Set<String>> allServiceNames = new ConcurrentHashMap<>();
        // 获取所有命名空间id
        Set<String> namespaceIds = getAllNamespaceId();
        // 遍历所有命名空间id
        for (String namespaceId : namespaceIds) {
            // 根据namespaceId获取该命名空间下面的所有service名称
            Set<String> serviceNames = getAllServiceName(namespaceId);
            allServiceNames.put(namespaceId, serviceNames);
        }
        return allServiceNames;
    }

    /**
     * 如果该service不存在才会创建该空的service
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     */
    public void createEmptyServiceIfAbsent(String namespaceId, String groupName, String serviceName) {
        // 获取指定service
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果该service不存在
        if (service == null) {
            service = ServiceBuilder.newBuilder()
                    .namespaceId(namespaceId)
                    .groupName(groupName)
                    .serviceName(serviceName)
                    .createTime(LocalDateTime.now())
                    .build();

            Set<Group> groups = groupMap.get(namespaceId);
            for (Group group : groups) {
                // 找到我们的目标分组
                if (group.getGroupName().equals(groupName)) {
                    Set<Service> services = group.getServices();
                    // 将新创建的service放入分组对象的service列表中
                    services.add(service);
                    break;
                }
            }
        }
    }

    /**
     * 注册实例
     *
     * @param namespaceId namespaceId
     * @param instance    instance
     */
    public void registerInstance(String namespaceId, String groupName, Instance instance) {

        String serviceName = instance.getServiceName();
        // 如果该service不存在才会创建该空的service
        createEmptyServiceIfAbsent(namespaceId, groupName, serviceName);
        // 获取指定service
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果该service不存在
        if (service == null) {
            throw new RuntimeException("service不存在, namespaceId: " + namespaceId
                    + ", groupName: " + groupName + ", serviceName: " + serviceName);
        }
        // 向指定service中添加instance（临时实例或者永久实例）
        addInstanceToService(service, instance);
    }

    /**
     * 向指定service中添加instance（临时实例或者永久实例）
     *
     * @param service  service
     * @param instance instance
     */
    public void addInstanceToService(Service service, Instance instance) {

        boolean ephemeral = instance.getEphemeral();

        // 如果该实例为“临时实例”
        if (ephemeral) {
            // 将实例放到service中的临时实例集合中
            service.getEphemeralInstances().add(instance);
        }
        // 如果该实例为“永久实例”
        else {
            // 将实例放到service中的永久实例集合中
            service.getPersistentInstances().add(instance);
        }
    }

    /**
     * 修改instance
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param instance    instance
     */
    public void updateInstance(String namespaceId, String groupName, Instance instance) {

        // TODO: 2023/10/7

    }

    /**
     * 删除指定的instance
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param instance    instance
     */
    public void deleteInstance(String namespaceId, String groupName, Instance instance){

        // TODO: 2023/10/7

    }


    /**
     * 修改instance的元数据
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     * @param instance    instance
     * @param metadata    metadata
     * @return {@link List}<{@link Instance}> 修改成功后的实例
     */
    public Instance updateInstanceMetadata(String namespaceId, String groupName, String serviceName,
                                           Instance instance, Map<String, String> metadata) {

        // TODO: 2023/10/7
        return null;

    }


    /**
     * 获取指定的service
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     * @return {@link Service}
     */
    public Service getService(String namespaceId, String groupName, String serviceName) {
        Set<Group> groups = groupMap.get(namespaceId);
        for (Group group : groups) {
            if (group.getGroupName().equals(groupName)) {
                Set<Service> services = group.getServices();
                for (Service service : services) {
                    // 找到service
                    if (service.getServiceName().equals(serviceName)) {
                        return service;
                    }
                }
            }
        }
        // 没有找到就返回null
        return null;
    }

    /**
     * 获取指定的instance
     *
     * @param namespaceId
     * @param groupName
     * @param serviceName
     * @param ipAddr
     * @param port
     * @return {@link Instance}
     */
    public Instance getInstance(String namespaceId, String groupName, String serviceName, String ipAddr, int port){

        // TODO: 2023/10/7

        return null;
    }


    public int getNamespaceCount() {

        return 0;
    }

    public int getGroupCount() {

        return 0;
    }

    public int getServiceCount() {

        return 0;
    }

    public int getInstanceCount() {

        return 0;
    }

}
