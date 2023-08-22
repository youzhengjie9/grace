package com.grace.client.core;

import com.grace.common.constant.PropertiesKeyConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务列表管理器
 *
 * @author youzhengjie
 * @date 2023/08/05 01:08:16
 */
public class ServerListManager implements ServerListFactory{

    private final long refreshServerListInternal = TimeUnit.SECONDS.toMillis(30);

    private final Long namespaceId;

    /**
     * 当前选择的server所在的serverList的下标
     */
    private final AtomicInteger currentIndex = new AtomicInteger();

    private final List<String> serverList = new ArrayList<>();

    private ScheduledExecutorService refreshServerListExecutor;


    public ServerListManager(Properties properties) {
        this(properties,null);
    }

    public ServerListManager(Properties properties , Long namespaceId) {
        this.namespaceId = namespaceId;
        initServerAddr(properties);
        // 如果serverList不为空
        if (!serverList.isEmpty()) {
            //初始化时,如果serverList中有一个或多个server则“随机”抽取一个server作为当前选择的server
            Random random = new Random();
            // 如果serverList的size=3,则生成的index范围为[0,3),也就是(0,1,2)其中的一个数字
            int index=random.nextInt(serverList.size());
            //保存为currentIndex（当前选择的server所在的serverList的下标）
            currentIndex.set(index);
        }else{
            throw new RuntimeException("serverList is empty,please check configuration");
        }

    }

    private void initServerAddr(Properties properties) {
        String serverListStr = properties.getProperty(PropertiesKeyConstants.SERVER_ADDR);
        if (StringUtils.isNotEmpty(serverListStr)) {
            this.serverList.addAll(Arrays.asList(serverListStr.split(",")));
        }
    }

    private void refreshServerListIfNeed() {

    }

    @Override
    public List<String> getServerList() {
        return serverList;
    }

    /**
     * 切换到新的服务并获取它
     *
     * @return {@link String}
     */
    @Override
    public String genNextServer() {
        int index = currentIndex.incrementAndGet() % getServerList().size();
        return getServerList().get(index);
    }

    /**
     * 获取当前选择的服务
     *
     * @return {@link String}
     */
    @Override
    public String getCurrentServer() {
        int index = currentIndex.get() % getServerList().size();
        return getServerList().get(index);
    }



}
