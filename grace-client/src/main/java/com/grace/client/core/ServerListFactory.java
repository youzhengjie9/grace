package com.grace.client.core;

import java.util.List;


/**
 * 服务列表工厂
 *
 * @author youzhengjie
 * @date 2023/08/05 01:04:50
 */
public interface ServerListFactory {
    
    /**
     * 切换到新的服务并获取它
     *
     * @return ip:port
     */
    String genNextServer();
    
    /**
     * 获取当前选择的服务
     *
     * @return ip:port
     */
    String getCurrentServer();
    
    /**
     * 获取当前服务列表
     *
     * @return serverList.
     */
    List<String> getServerList();
    
}
