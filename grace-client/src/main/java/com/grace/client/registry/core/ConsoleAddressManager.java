package com.grace.client.registry.core;

import java.util.List;

/**
 * 控制台地址的管理器
 *
 * @author youzhengjie
 * @date 2023-09-28 09:00:06
 */
public interface ConsoleAddressManager {
    
    /**
     * 获取下一个控制台地址（例如: latestIndex= latestIndex+ 1 ; consoleAddressList.get(latestIndex)）
     * <p>
     * 注意: 如果 consoleAddressList 只有一个控制台地址,则此方法和 String getCurrentConsoleAddress();效果一样
     * @return ip:port
     */
    String getNextConsoleAddress();
    
    /**
     * 获取当前控制台地址（consoleAddressList.get(latestIndex)）
     *
     * @return ip:port
     */
    String getCurrentConsoleAddress();
    
    /**
     * 获取当前控制台地址列表(consoleAddressList)
     *
     * @return consoleAddressList
     */
    List<String> getConsoleAddressList();
    
}
