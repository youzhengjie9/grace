package com.grace.console.core;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Group;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * 检查所有临时实例的心跳
 * <p>
 * 对说明有可能该实例超过 maxHeartBeatTimeout（默认30s）没发送心跳导致被删除,或者传过来的心跳参数有误
 *
 * @author youzhengjie
 * @date 2023/10/25 12:06:24
 */
public class CheckAllEphemeralInstanceHeartBeatTask implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(CheckAllEphemeralInstanceHeartBeatTask.class);
    private final GroupManager groupManager = GroupManager.getGroupManagerSingleton();

    @Override
    public void run() {
        // 获取所有命名空间id
        Set<String> namespaceIds = groupManager.getAllNamespaceId();
        for (String namespaceId : namespaceIds) {
            //拿到该命名空间的分组
            Set<Group> groups = groupManager.getGroups(namespaceId);
            for (Group group : groups) {
                // 获取这个组的所有服务
                Set<Service> services = group.getServices();
                for (Service service : services) {
                    // 拿到该服务的临时实例列表
                    Set<Instance> ephemeralInstances = service.getEphemeralInstances();
                    for (Instance ephemeralInstance : ephemeralInstances) {
                        // 该临时实例最近一次发送心跳请求时间
                        long lastHeartBeatTime = ephemeralInstance.getLastHeartBeatTime();
                        // 当前时间（毫秒）
                        long currentTime = System.currentTimeMillis();
                        // 该临时实例多久没有发送心跳请求了
                        long lastHeartBeatInterval = currentTime - lastHeartBeatTime;
                        // 如果lastHeartBeatInterval大于15秒、小于30秒（将该实例健康状态health修改成false）
                        if (lastHeartBeatInterval > Constants.HEART_BEAT_TIMEOUT &&
                                lastHeartBeatInterval < Constants.MAX_HEART_BEAT_TIMEOUT) {
                            ephemeralInstance.setHealthy(false);
                            log.warn("该实例由于超过15秒没有发送心跳,所以健康状态被修改成false。该实例的信息为{}",ephemeralInstance);
                            // 进入下次循环
                            continue;
                        }
                        // 如果lastHeartBeatInterval大于30秒（将该实例从对应所在服务的实例列表中移除）
                        if (lastHeartBeatInterval > Constants.MAX_HEART_BEAT_TIMEOUT) {
                            // 删除该临时实例
                            groupManager.deleteInstance(namespaceId,group.getGroupName()
                                    ,service.getServiceName(),ephemeralInstance.getIpAddr()
                                    ,ephemeralInstance.getPort(),true);
                            log.warn("由于该实例超过30秒没有发送心跳,所以被删除。该实例的信息为{}",ephemeralInstance);
                        }
                    }
                }
            }
        }
    }
}
