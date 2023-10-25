package com.grace.console.core;

import com.grace.common.constant.Constants;
import com.grace.common.entity.Instance;

import java.util.List;

/**
 * 检查所有临时实例的心跳
 * <p>
 * 对说明有可能该实例超过 maxHeartBeatTimeout（默认30s）没发送心跳导致被删除,或者传过来的心跳参数有误
 *
 * @author youzhengjie
 * @date 2023/10/25 12:06:24
 */
public class CheckAllEphemeralInstanceHeartBeatTask implements Runnable{

    private final GroupManager groupManager = GroupManager.getGroupManagerSingleton();

    @Override
    public void run() {

        // 获取所有临时实例
        List<Instance> allEphemeralInstance = groupManager.getAllEphemeralInstance();

        for (Instance ephemeralInstance: allEphemeralInstance) {

            // 该临时实例最近一次发送心跳请求时间
            long lastHeartBeatTime = ephemeralInstance.getLastHeartBeatTime();
            long currentTime = System.currentTimeMillis();
            // 该临时实例多久没有发送心跳请求了
            long lastHeartBeatInterval = currentTime - lastHeartBeatTime;
            // 如果lastHeartBeatInterval大于15秒、小于30秒（将该实例健康状态health修改成false）
            if(lastHeartBeatInterval > Constants.HEART_BEAT_TIMEOUT &&
                    lastHeartBeatInterval < Constants.MAX_HEART_BEAT_TIMEOUT) {

            }
            // 如果lastHeartBeatInterval大于30秒（将该实例从对应所在服务的实例列表中移除）
            if(lastHeartBeatInterval > Constants.MAX_HEART_BEAT_TIMEOUT) {

            }

        }

    }
}
