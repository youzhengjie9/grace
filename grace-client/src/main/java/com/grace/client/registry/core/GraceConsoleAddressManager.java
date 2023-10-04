package com.grace.client.registry.core;

import com.grace.common.constant.PropertiesKeyConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * grace控制台（grace-console模块）地址的管理器（注意: 因为集群模式暂未实现，所以建议只填写一个grace控制台地址！）
 * <p>
 *
 * @author youzhengjie
 * @date 2023-09-28 09:06:54
 */
public class GraceConsoleAddressManager implements ConsoleAddressManager {

    private final long refreshServerListInternal = TimeUnit.SECONDS.toMillis(30);

    private final Long namespaceId;

    /**
     * 记录当前最新获取的控制台地址所在的consoleAddressList的下标
     */
    private final AtomicInteger latestIndex = new AtomicInteger();

    /**
     * grace控制台地址列表
     */
    private final List<String> consoleAddressList = new ArrayList<>();

    private ScheduledExecutorService refreshServerListExecutor;


    public GraceConsoleAddressManager(Properties properties) {
        this(properties,null);
    }

    public GraceConsoleAddressManager(Properties properties , Long namespaceId) {
        this.namespaceId = namespaceId;
        // 根据传过来的Properties对象对consoleAddressList属性进行初始化
        initConsoleAddressListByProperties(properties);
        // 如果consoleAddressList不为空
        if (!consoleAddressList.isEmpty()) {
            //初始化时,如果consoleAddressList中有一个或多个控制台地址则“随机”选一个latestIndex
            Random random = new Random();
            // 如果consoleAddressList的size=3,则生成的index范围为[0,3),也就是(0,1,2)其中的一个数字
            int index=random.nextInt(consoleAddressList.size());
            //保存为latestIndex（记录当前最新获取的控制台地址所在的consoleAddressList的下标）
            latestIndex.set(index);
        }else{
            throw new RuntimeException("consoleAddressList不能为空");
        }
    }

    /**
     * 根据传过来的Properties对象对consoleAddressList属性进行初始化
     *
     * @param properties
     */
    private void initConsoleAddressListByProperties(Properties properties) {
        String consoleAddress = properties.getProperty(PropertiesKeyConstants.CONSOLE_ADDRESS);
        if (StringUtils.isNotEmpty(consoleAddress)) {
            // 多个地址用","分隔
            this.consoleAddressList.addAll(Arrays.asList(consoleAddress.split(",")));
        }
    }

    private void refreshServerListIfNeed() {

    }

    /**
     * 获取下一个控制台地址（例如: latestIndex= latestIndex+ 1 ; consoleAddressList.get(latestIndex)）
     * <p>
     * 注意: 如果 consoleAddressList 只有一个控制台地址,则此方法和 String getCurrentConsoleAddress();效果一样
     * @return ip:port
     */
    @Override
    public String getNextConsoleAddress() {
        int index = latestIndex.incrementAndGet() % getConsoleAddressList().size();
        return getConsoleAddressList().get(index);
    }

    /**
     * 获取当前控制台地址（consoleAddressList.get(latestIndex)）
     *
     * @return ip:port
     */
    @Override
    public String getCurrentConsoleAddress() {
        int index = latestIndex.get() % getConsoleAddressList().size();
        return getConsoleAddressList().get(index);
    }

    @Override
    public List<String> getConsoleAddressList() {
        return consoleAddressList;
    }


}
