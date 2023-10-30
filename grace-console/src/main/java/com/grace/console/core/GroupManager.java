package com.grace.console.core;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Group;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.entity.builder.ServiceBuilder;
import com.grace.common.dto.ServiceDTO;
import com.grace.common.utils.JsonUtils;
import com.grace.console.vo.ServiceDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

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
        // 初始化默认的public命名空间到groupMap中
        createNamespaceIfAbsent(Constants.DEFAULT_NAMESPACE_ID);
    }

    /**
     * GroupManager的单例对象
     *
     * @return {@link GroupManager}
     */
    public static GroupManager getGroupManagerSingleton() {
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
     * 给groupMap创建指定的命名空间（前提是groupMap的key不存在该namespaceId）
     *
     * @param namespaceId namespaceId
     * @return {@link Boolean}
     */
    public Boolean createNamespaceIfAbsent(String namespaceId){
        groupMap.putIfAbsent(namespaceId,Collections.synchronizedSet(new HashSet<>()));
        return true;
    }

    public Boolean deleteNamespace(String namespaceId) {
        // 删除命名空间
        groupMap.remove(namespaceId);
        return true;
    }

    /**
     * 根据namespaceId获取该命名空间下面的所有分组名称
     *
     * @param namespaceId
     * @return {@link Set}<{@link String}>
     */
    public Set<String> getAllGroupName(String namespaceId) {
        if(hasNamespace(namespaceId)){
            Set<String> groupNames = Collections.synchronizedSet(new HashSet<>());
            Set<Group> groups = groupMap.get(namespaceId);
            for (Group group : groups) {
                groupNames.add(group.getGroupName());
            }
            return groupNames;
        }
        return new CopyOnWriteArraySet<>();
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
        Set<String> serviceNames = Collections.synchronizedSet(new HashSet<>());
        if(hasNamespace(namespaceId)){
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
        return serviceNames;
    }

    /**
     * 获取所有符合条件的service(不进行分页)
     *
     * @param namespaceId      命名空间id
     * @param hideEmptyService 是否隐藏空服务（也就是说不统计没有instance的service）
     * @param groupName 分组名称（如果为null或者""则默认不指定分组名）
     * @param serviceName 服务名称（如果为null或者""则默认不指定服务名）
     * @return {@link Set}<{@link Service}>
     */
    public Set<Service> getAllService(String namespaceId,String groupName,String serviceName,boolean hideEmptyService) {
        // 如果groupName不为空,则说明指定了groupName
        boolean assignGroupName = groupName != null && !groupName.equals("");
        // 如果serviceName不为空,则说明指定了serviceName
        boolean assignServiceName = serviceName != null && !serviceName.equals("");
        if(hasNamespace(namespaceId)){
            // 最终需要返回的集合
            Set<Service> services = Collections.synchronizedSet(new HashSet<>());
            // 根据namespaceId获取该命名空间下面的所有分组
            Set<Group> groups = groupMap.get(namespaceId);
            // 遍历分组集合
            for (Group group : groups) {
                // 如果指定groupName了
                if(assignGroupName){
                    // 如果当前遍历到的分组的名称包含指定的groupName（也就是模糊匹配）
                    if(group.getGroupName().contains(groupName)){
                        // 获取每一个分组下面的所有service
                        Set<Service> svc = group.getServices();
                        for (Service service : svc) {
                            // 如果指定serviceName了
                            if(assignServiceName) {
                                // 如果当前遍历到的service的名称包含指定的serviceName（也就是模糊匹配）
                                if (service.getServiceName().contains(serviceName)) {
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
                            }else {
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
                    }
                }
                // 如果没有指定groupName
                else{
                    // 获取每一个分组下面的所有service
                    Set<Service> svc = group.getServices();
                    for (Service service : svc) {
                        // 如果指定serviceName了
                        if(assignServiceName) {
                            // 如果当前遍历到的service的名称包含指定的serviceName（也就是模糊匹配）
                            if (service.getServiceName().contains(serviceName)) {
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
                        }else {
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
                }
            }
            return services;
        }
        return new CopyOnWriteArraySet<>();
    }

    /**
     * 根据namespaceId获取该命名空间下面的所有service名称
     *
     * @param namespaceId namespaceId
     * @return {@link Set}<{@link String}>
     */
    public Set<String> getAllServiceName(String namespaceId,String groupName) {
        if(hasNamespace(namespaceId)){
            Set<String> serviceNames = Collections.synchronizedSet(new HashSet<>());
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
        return new CopyOnWriteArraySet<>();
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
        Float protectThreshold = serviceDTO.getProtectThreshold();
        String metadata = serviceDTO.getMetadata();
        // 如果metadata不为空我们才进行校验,为空就不校验了（因为metadata不是必填项）
        if(!StringUtils.isBlank(metadata)){
            // 校验metadata是否为JSON格式的字符串
            Boolean metadataIsJsonString = JsonUtils.isJsonString(metadata);
            // 如果metadata不是JSON格式的字符串,则创建失败
            if(!metadataIsJsonString){
                log.error("metadata不是JSON字符串格式。创建失败");
                return false;
            }
        }
        // 将Map<Object, Object>类型的metadata转成Map<String,String>类型
        final Map<Object, Object> oldMetadataMap = JsonUtils.jsonStr2Map(metadata);
        final Map<String,String> newMetadataMap = new ConcurrentHashMap<>();
        if(oldMetadataMap != null){
            oldMetadataMap.forEach((key,value) -> {
                String k = String.valueOf(key);
                String v = String.valueOf(value);
                newMetadataMap.put(k,v);
            });
        }

        // 获取指定service
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果该service不存在
        if (service == null) {
            // 如果namespace不存在
            if(!hasNamespace(namespaceId)){
                // 创建命名空间
                groupMap.put(namespaceId,Collections.synchronizedSet(new HashSet<>()));
            }
            // 需要创建的service
            // 将ServiceDTO对象转成Service对象
            service = ServiceBuilder.newBuilder()
                    .namespaceId(namespaceId)
                    .groupName(groupName)
                    .serviceName(serviceName)
                    .protectThreshold(protectThreshold)
                    .ephemeralInstances(Collections.synchronizedSet(new HashSet<>()))
                    .persistentInstances(Collections.synchronizedSet(new HashSet<>()))
                    .metadata(newMetadataMap)
                    .createTime(LocalDateTime.now())
                    .lastUpdatedTime(LocalDateTime.now())
                    .build();
            // 获取该namespace的分组集合(这个集合一定不为null,但是可能里面没有数据)
            Set<Group> groups = groupMap.get(namespaceId);
            // 目标分组
            Group targetGroup = null;
            for (Group group : groups) {
                // 如果找到我们的目标分组
                if (group.getGroupName().equals(groupName)) {
                    // 将找到的目标分组存到targetGroup对象中
                    targetGroup = group;
                    break;
                }
            }
            // 如果targetGroup为null,说明该分组（groupName）不存在,则需要创建该分组
            if(targetGroup == null) {
                // 创建新的group对象
                targetGroup = new Group();
                targetGroup.setGroupName(groupName);
                // 创建service集合
                Set<Service> services = Collections.synchronizedSet(new HashSet<>());
                services.add(service);
                // 将service集合放到该分组中
                targetGroup.setServices(services);
                // 将这个目标分组放到groups集合中
                groups.add(targetGroup);
                // 将groups集合放到groupMap中
                groupMap.put(namespaceId,groups);
            }
            // 如果targetGroup不为null,说明找到了目标分组（该分组存在）
            else {
                // 拿到这个分组的所有service
                Set<Service> services = targetGroup.getServices();
                // 如果该service已存在,则不能重复创建
                if(services.contains(service)){
                    log.warn("service={} , 已存在,不能重复创建"+service.toString());
                }else {
                    // 如果该service不存在,则将新创建的service放入该分组的service列表中
                    services.add(service);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 如果该service不存在才会创建该空的service
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     * @return boolean
     */
    public boolean createEmptyServiceIfAbsent(String namespaceId, String groupName, String serviceName) {
        // 获取指定service
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果该service不存在
        if (service == null) {
            // 如果namespace不存在
            if(!hasNamespace(namespaceId)){
                // 创建命名空间
                groupMap.put(namespaceId,Collections.synchronizedSet(new HashSet<>()));
            }
            // 需要创建的service
            // 将ServiceDTO对象转成Service对象
            service = ServiceBuilder.newBuilder()
                    .namespaceId(namespaceId)
                    .groupName(groupName)
                    .serviceName(serviceName)
                    .protectThreshold(0.0F)
                    .ephemeralInstances(Collections.synchronizedSet(new HashSet<>()))
                    .persistentInstances(Collections.synchronizedSet(new HashSet<>()))
                    .metadata(new HashMap<>())
                    .createTime(LocalDateTime.now())
                    .lastUpdatedTime(LocalDateTime.now())
                    .build();
            // 获取该namespace的分组集合(这个集合一定不为null,但是可能里面没有数据)
            Set<Group> groups = groupMap.get(namespaceId);
            // 目标分组
            Group targetGroup = null;
            for (Group group : groups) {
                // 如果找到我们的目标分组
                if (group.getGroupName().equals(groupName)) {
                    // 将找到的目标分组存到targetGroup对象中
                    targetGroup = group;
                    break;
                }
            }
            // 如果targetGroup为null,说明该分组（groupName）不存在,则需要创建该分组
            if(targetGroup == null) {
                // 创建新的group对象
                targetGroup = new Group();
                targetGroup.setGroupName(groupName);
                // 创建service集合
                Set<Service> services = Collections.synchronizedSet(new HashSet<>());
                services.add(service);
                // 将service集合放到该分组中
                targetGroup.setServices(services);
                // 将这个目标分组放到groups集合中
                groups.add(targetGroup);
                // 将groups集合放到groupMap中
                groupMap.put(namespaceId,groups);
            }
            // 如果targetGroup不为null,说明找到了目标分组（该分组存在）
            else {
                // 拿到这个分组的所有service
                Set<Service> services = targetGroup.getServices();
                // 如果该service已存在,则不能重复创建
                if(services.contains(service)){
                    log.warn("service={} , 已存在,不能重复创建"+service.toString());
                }else {
                    // 如果该service不存在,则将新创建的service放入该分组的service列表中
                    services.add(service);
                }
            }
            return true;
        }
        return false;
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
     * @param serviceName serviceName
     * @param ipAddr      ipAddr
     * @param port        port
     * @param isEphemeral 该实例是否为临时实例
     */
    public void deleteInstance(String namespaceId, String groupName, String serviceName,String ipAddr,int port,boolean isEphemeral) {

        Service service = getService(namespaceId, groupName, serviceName);
        // 临时实例列表
        Set<Instance> ephemeralInstances = service.getEphemeralInstances();
        for (Instance ephemeralInstance : ephemeralInstances) {
            // 找到想删除的临时实例
           if(ephemeralInstance.getIpAddr().equals(ipAddr) && ephemeralInstance.getPort()==port){
               // 从该服务的临时实例列表中删除该临时实例
               ephemeralInstances.remove(ephemeralInstance);
           }
        }
        // TODO: 2023/10/26 删除永久实例实现（永久实例是存储在磁盘中的）
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
        if(hasNamespace(namespaceId)){
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
        }
        return null;
    }

    /**
     * 获取service详情
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     * @return {@link ServiceDetailVO}
     */
    public ServiceDetailVO getServiceDetail(String namespaceId, String groupName, String serviceName) {
        if(hasNamespace(namespaceId)){
            Set<Group> groups = groupMap.get(namespaceId);
            // 目标分组
            Group targetGroup = null;
            for (Group group : groups) {
                // 如果找到目标分组
                if (group.getGroupName().equals(groupName)) {
                    targetGroup = group;
                    break;
                }
            }
            // 如果找到了目标分组，则继续找目标service
            if(targetGroup != null){
                Set<Service> services = targetGroup.getServices();
                for (Service service : services) {
                    // 如果找到目标service
                    if (service.getServiceName().equals(serviceName)) {
                        // 将Service转成ServiceDetailVO,并返回
                        ServiceDetailVO serviceDetailVO = new ServiceDetailVO();
                        serviceDetailVO.setNamespaceId(service.getNamespaceId());
                        serviceDetailVO.setGroupName(service.getGroupName());
                        serviceDetailVO.setServiceName(service.getServiceName());
                        serviceDetailVO.setProtectThreshold(service.getProtectThreshold());
                        serviceDetailVO.setAllInstances(service.getAllInstance());
                        serviceDetailVO.setMetadata(
                                (service.getMetadata() == null || service.getMetadata().size() == 0)
                                ? ""
                                : JsonUtils.map2FormatedJsonStr((Map<Object, Object>)(Object)service.getMetadata())
                        );
                        System.out.println(service);
                        System.out.println(service.hashCode());
                        System.out.println(service.getEphemeralInstances());
                        System.out.println(service.getAllInstance());
                        System.out.println(serviceDetailVO);
                        return serviceDetailVO;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 根据instanceId获取指定的instance
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     * @return {@link Instance}
     */
    public Instance getInstance(String namespaceId, String groupName, String serviceName, String instanceId) {
        // 先获取service
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果service不为空
        if(service != null){
            // 获取service所有instance
            List<Instance> allInstance = service.getAllInstance();
            // 如果该service存有instance
            if(allInstance != null && allInstance.size() > 0){
                // 遍历实例列表
                for (Instance instance : allInstance) {
                    // 如果找到指定的instance
                    if(instance.getInstanceId().equals(instanceId)){
                        return instance;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据ipAddr和Port获取指定的instance
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     * @param ipAddr ipAddr
     * @param port port
     * @return {@link Instance}
     */
    public Instance getInstance(String namespaceId, String groupName, String serviceName, String ipAddr,int port) {
        // 先获取service
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果service不为空
        if(service != null){
            // 获取service所有instance
            List<Instance> allInstance = service.getAllInstance();
            // 如果该service存有instance
            if(allInstance != null && allInstance.size() > 0){
                // 遍历实例列表
                for (Instance instance : allInstance) {
                    // 如果找到指定的instance
                    if(instance.getIpAddr().equals(ipAddr) && instance.getPort() == port){
                        return instance;
                    }
                }
            }
        }
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

    /**
     * 获取指定命名空间下面的所有服务总数
     *
     * @param namespaceId namespaceId
     * @return int
     */
    public int getServiceCount(String namespaceId) {
        AtomicInteger serviceCount = new AtomicInteger(0);
        // 如果有该命名空间
        if (hasNamespace(namespaceId)) {
            Set<Group> groups = groupMap.get(namespaceId);
            for (Group group : groups) {
                int size = group.getServices().size();
                // 将每个组下面的所有服务数量统计到serviceCount变量中
                serviceCount.addAndGet(size);
            }
            return serviceCount.get();
        }
        return 0;
    }


    /**
     * 该命名空间是否存在
     *
     * @param namespaceId namespaceId
     * @return boolean
     */
    public boolean hasNamespace(String namespaceId){

        boolean hasNamespace = groupMap.containsKey(namespaceId);
        if(!hasNamespace){
//            log.warn("namespaceId为 {} 的命名空间不存在",namespaceId);
        }
        return hasNamespace;
    }


    /**
     * 根据serviceDTO修改service
     *
     * @param serviceDTO serviceDTO
     * @return {@link Boolean}
     */
    public Boolean modifyServiceByServiceDTO(ServiceDTO serviceDTO) {
        String namespaceId = serviceDTO.getNamespaceId();
        String groupName = serviceDTO.getGroupName();
        String serviceName = serviceDTO.getServiceName();
        // 获取service对象
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果service不为空
        if(service != null){
            try {
                String metadata = serviceDTO.getMetadata();
                // 如果metadata不为空我们才进行校验,为空就不校验了（因为metadata不是必填项）
                if(!StringUtils.isBlank(metadata)){
                    // 校验metadata是否为JSON格式的字符串
                    Boolean metadataIsJsonString = JsonUtils.isJsonString(metadata);
                    // 如果metadata不是JSON格式的字符串,则修改失败
                    if(!metadataIsJsonString){
                        log.error("metadata不是JSON字符串格式。修改失败");
                        return false;
                    }
                }
                // 将Map<Object, Object>类型的metadata转成Map<String,String>类型
                final Map<Object, Object> oldMetadataMap = JsonUtils.jsonStr2Map(metadata);
                final Map<String,String> newMetadataMap = new ConcurrentHashMap<>();
                if(oldMetadataMap != null){
                    oldMetadataMap.forEach((key,value) -> {
                        String k = String.valueOf(key);
                        String v = String.valueOf(value);
                        newMetadataMap.put(k,v);
                    });
                }
                // 修改service
                service.setProtectThreshold(serviceDTO.getProtectThreshold());
                service.setMetadata(newMetadataMap);
                service.setLastUpdatedTime(LocalDateTime.now());
                return true;
            }catch (Exception e){
                e.printStackTrace();
                log.error("修改失败");
                return false;
            }
        }
        return false;
    }


    /**
     * 删除service
     *
     * @param namespaceId namespaceId
     * @param groupName   groupName
     * @param serviceName serviceName
     * @return {@link Boolean}
     */
    public Boolean deleteService(String namespaceId, String groupName, String serviceName) {
        Service service = getService(namespaceId, groupName, serviceName);
        // 如果service存在
        if (service != null) {
            Set<Group> groups = groupMap.get(namespaceId);
            for (Group group : groups) {
                // 如果找到分组
                if (group.getGroupName().equals(groupName)) {
                    Set<Service> services = group.getServices();
                    for (Service svc : services) {
                        // 如果找到service
                        if (svc.getServiceName().equals(serviceName)) {
                            // 删除指定的service
                            return services.remove(svc);
                        }
                    }
                }
            }
        }
        // service不存在,删除失败
        return false;
    }

    /**
     * 获取所有临时实例
     *
     * @return {@link List}<{@link Instance}>
     */
    public List<Instance> getAllEphemeralInstance() {
        List<Instance> allEphemeralInstance = Collections.synchronizedList(new ArrayList<>());
        Set<String> namespaceIds = groupMap.keySet();
        for (String namespaceId : namespaceIds) {
            Set<Group> groups = groupMap.get(namespaceId);
            for (Group group : groups) {
                Set<Service> services = group.getServices();
                for (Service service : services) {
                    Set<Instance> ephemeralInstances = service.getEphemeralInstances();
                    allEphemeralInstance.addAll(ephemeralInstances);
                }
            }
        }
        return allEphemeralInstance;
    }

    /**
     * 获取所有实例
     *
     * @return {@link List}<{@link Instance}>
     */
    public List<Instance> getAllInstance(String namespaceId,String groupName, String serviceName) {
        List<Instance> allInstance = Collections.synchronizedList(new ArrayList<>());
        Service service = getService(namespaceId, groupName, serviceName);
        return service.getAllInstance();
    }

    public Set<Group> getGroups(String namespaceId) {
        return groupMap.get(namespaceId);
    }
}
