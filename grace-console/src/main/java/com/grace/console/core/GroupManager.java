package com.grace.console.core;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Group;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.entity.builder.ServiceBuilder;
import com.grace.console.dto.ServiceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

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
     * key=命名空间id,value=group集合
     * 这里体现了一个命名空间可以有多个分组、一个分组可以有多个服务、一个服务可以有多个实例（分为临时实例和永久实例）
     */
    private final Map<String, Set<Group>> groupMap;


    private GroupManager() {
        // 初始化默认容量为1<<2（也就是为 4）
        groupMap = new ConcurrentHashMap<>(1 << 2);

//        // 初始化public命名空间到groupMap中
//        // TODO: 2023/10/9 将数据库中有关public命名空间的数据都加载到publicNamespaceGroups中
//        Set<Group> publicNamespaceGroups = new CopyOnWriteArraySet<>();
//        groupMap.put(Constants.DEFAULT_NAMESPACE_ID,publicNamespaceGroups);

        // TODO: 2023/10/9 暂时的模拟数据，到时要该成从数据库查询数据
        // public命名空间的分组group集合
        Set<Group> publicNamespaceGroups = new CopyOnWriteArraySet<>();
        // 默认分组(DEFAULT_GROUP)的service集合
        Set<Service> defaultGroupService = new CopyOnWriteArraySet<>();

        // userService
        Service userService = ServiceBuilder.newBuilder()
                .namespaceId(Constants.DEFAULT_NAMESPACE_ID)
                .groupName(Constants.DEFAULT_GROUP_NAME)
                .serviceName("userService")
                .protectThreshold(0.5F)
                .ephemeralInstances(new CopyOnWriteArraySet<>())
                .persistentInstances(new CopyOnWriteArraySet<>())
                // TODO: 2023/10/7 将String类型的metadata转成Map类型的metadata
//                .metadata(serviceDTO.getMetadata())
                .createTime(LocalDateTime.now())
                .lastUpdatedTime(LocalDateTime.now())
                .build();

        // systemService
        Service systemService = ServiceBuilder.newBuilder()
                .namespaceId(Constants.DEFAULT_NAMESPACE_ID)
                .groupName(Constants.DEFAULT_GROUP_NAME)
                .serviceName("systemService")
                .protectThreshold(0.3F)
                .ephemeralInstances(new CopyOnWriteArraySet<>())
                .persistentInstances(new CopyOnWriteArraySet<>())
                // TODO: 2023/10/7 将String类型的metadata转成Map类型的metadata
//                .metadata(serviceDTO.getMetadata())
                .createTime(LocalDateTime.now())
                .lastUpdatedTime(LocalDateTime.now())
                .build();
        defaultGroupService.add(userService);
        defaultGroupService.add(systemService);
        // 默认分组(DEFAULT_GROUP)
        Group defaultGroup = new Group();
        defaultGroup.setGroupName(Constants.DEFAULT_GROUP_NAME);
        defaultGroup.setServices(defaultGroupService);
        publicNamespaceGroups.add(defaultGroup);
        groupMap.put(Constants.DEFAULT_NAMESPACE_ID,publicNamespaceGroups);
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
     * 获取所有符合条件的service(不进行分页)
     *
     * @param namespaceId      namespaceId
     * @param hideEmptyService 是否隐藏空服务（也就是说不统计没有instance的service）
     * @return {@link Set}<{@link Service}>
     */
    public Set<Service> getAllService(String namespaceId,boolean hideEmptyService) {
        // 最终需要返回的集合
        Set<Service> services = new CopyOnWriteArraySet<>();
        // 根据namespaceId获取该命名空间下面的所有分组
        Set<Group> groups = groupMap.get(namespaceId);
        // 遍历分组集合
        for (Group group : groups) {
            // 获取每一个分组下面的所有service
            Set<Service> svc = group.getServices();
            for (Service service : svc) {
                // 如果隐藏空服务
                if(hideEmptyService){
                    // 获取该服务的临时实例数量
                    int ephemeralInstanceCount = service.getEphemeralInstances().size();
                    // 获取该服务的永久实例数量
                    int persistentInstanceCount = service.getPersistentInstances().size();
                    // 如果该服务的临时实例数量或者永久实例数量只要有一个大于0（说明该服务不是空服务,则可以将该服务统计进去）
                    if(ephemeralInstanceCount > 0 || persistentInstanceCount > 0){
                        services.add(service);
                    }
                }
                // 如果没有隐藏空服务
                else {
                    services.add(service);
                }
            }
        }
        return services;
    }

    /**
     * 根据namespaceId获取该命名空间下面的所有service名称
     *
     * @param namespaceId namespaceId
     * @return {@link Set}<{@link String}>
     */
    public Set<String> getAllServiceName(String namespaceId,String groupName) {
        Set<String> serviceNames = new CopyOnWriteArraySet<>();
        // 根据namespaceId获取该命名空间下面的所有分组
        Set<Group> groups = groupMap.get(namespaceId);
        // 遍历分组集合
        for (Group group : groups) {
            // 找到指定的分组
            if(group.getGroupName().equals(groupName)){
                // 获取该分组下面的所有service
                Set<Service> services = group.getServices();
                for (Service service : services) {
                    String serviceName = service.getServiceName();
                    // 将service名称放到一个集合中存储起来
                    serviceNames.add(serviceName);
                }
                break;
            }

        }
        return serviceNames;
    }

    /**
     * 根据ServiceDTO来创建Service
     *
     * @param serviceDTO serviceDTO
     * @return {@link Boolean}
     */
    public Boolean createServiceByServiceDTO(ServiceDTO serviceDTO) {
        String namespaceId = serviceDTO.getNamespaceId();
        String groupName = serviceDTO.getGroupName();
        String serviceName = serviceDTO.getServiceName();
        // 获取指定service
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果该service不存在
        if (service == null) {
            // 将ServiceDTO对象转成Service对象
            service = ServiceBuilder.newBuilder()
                    .namespaceId(serviceDTO.getNamespaceId())
                    .groupName(serviceDTO.getGroupName())
                    .serviceName(serviceDTO.getServiceName())
                    .protectThreshold(serviceDTO.getProtectThreshold())
                    .ephemeralInstances(new CopyOnWriteArraySet<>())
                    .persistentInstances(new CopyOnWriteArraySet<>())
                    // TODO: 2023/10/7 将String类型的metadata转成Map类型的metadata
//                .metadata(serviceDTO.getMetadata())
                    .createTime(LocalDateTime.now())
                    .lastUpdatedTime(LocalDateTime.now())
                    .build();
            Set<Group> groups = groupMap.get(namespaceId);
            for (Group group : groups) {
                // 找到我们的目标分组
                if (group.getGroupName().equals(groupName)) {
                    Set<Service> services = group.getServices();
                    // 将新创建的service放入分组对象的service列表中,并返回添加是否成功的信息
                    return services.add(service);
                }
            }
        }
        return false;
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
    public void deleteInstance(String namespaceId, String groupName, Instance instance) {

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
    public Instance getInstance(String namespaceId, String groupName, String serviceName, String ipAddr, int port) {

        // TODO: 2023/10/7

        return null;
    }

    /**
     * 获取所有命名空间的总数
     *
     * @return int
     */
    public int getAllNamespaceCount() {

        return getAllNamespaceId().size();
    }


}
