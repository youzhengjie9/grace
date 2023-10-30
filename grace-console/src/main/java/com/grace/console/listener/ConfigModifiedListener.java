package com.grace.console.listener;

import com.grace.console.cache.CacheConfigClientAddress;
import com.grace.console.event.ConfigModifiedEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 配置被修改事件（ConfigModifiedEvent）的监听器
 *
 * @author youzhengjie
 * @date 2023/10/29 20:09:13
 */
@Component // 单例。将监听器交给Spring管理才能生效
public class ConfigModifiedListener implements ApplicationListener<ConfigModifiedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ConfigModifiedListener.class);

    private final CacheConfigClientAddress cacheConfigClientAddress;

    /**
     * 用于发送请求的线程池
     */
    private final ThreadPoolExecutor sendRequestThreadPool;

    /**
     * 动态刷新配置端点
     */
    private final String DYNAMIC_REFRESH_CONFIG_ENDPOINT = "/dynamic/refresh/config";

    public ConfigModifiedListener(){
        log.info("配置被修改事件（ConfigModifiedEvent）的监听器启动成功");
        cacheConfigClientAddress = CacheConfigClientAddress.getSingleton();
        // 初始化用于发送请求的线程池
        sendRequestThreadPool = new ThreadPoolExecutor(1,5,
                1L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void onApplicationEvent(@NotNull ConfigModifiedEvent configModifiedEvent) {
        log.info("监听到配置被修改事件,准备使用push模式刷新目标客户端配置。{}",configModifiedEvent);
        // 从触发配置被修改事件(configModifiedEvent对象)中获取被修改的配置信息
        String namespaceId = configModifiedEvent.getNamespaceId();
        String groupName = configModifiedEvent.getGroupName();
        String dataId = configModifiedEvent.getDataId();
        // 获取所有使用该配置的客户端地址
        List<String> clientAddressList =
                cacheConfigClientAddress.getClientAddressList(namespaceId, groupName, dataId);
        // 如果clientAddressList有数据（说明有客户端使用了该配置）
        if(clientAddressList != null && clientAddressList.size()>0) {
            // 遍历所有使用该配置的客户端地址的列表（clientAddressList）
            for (String clientAddress : clientAddressList) {
                // 使用线程池异步发送请求
                sendRequestThreadPool.submit(() -> {
                    // 拼接请求url（格式例如: http://192.168.184.100:6001/dynamic/refresh/config ）
                    String requestUrl = "http://" + clientAddress + DYNAMIC_REFRESH_CONFIG_ENDPOINT;
                    // 构建httpclient对象
                    CloseableHttpClient httpClient = HttpClients.createDefault();
                    // 构建post请求对象
                    HttpPost httpPost = new HttpPost(requestUrl);
                    try {
                        // 使用apache httpclient发送动态刷新配置的请求
                        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }


}
