package com.grace.console.cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存一个Map(配置,所有绑定该配置的客户端地址)
 *
 * @author youzhengjie
 * @date 2023/10/30 01:22:49
 */
public class CacheConfigClientAddress {

    private static final CacheConfigClientAddress INSTANCE = new CacheConfigClientAddress();

    /**
     * Map(配置,所有绑定该配置的客户端地址),作用是根据“修改的配置”找到“所有绑定该配置的客户端地址”,
     * 然后向所有绑定该配置的客户端地址发送刷新配置请求(相当于调用客户端的contextRefresher.refresh方法)
     * <p>
     * Map的key格式为: 命名空间id@@分组名@@dataId（例如: test@@DEFAULT_GROUP@@application-dev.yml ）
     * <p>
     * Map的value是: 绑定该配置的所有客户端地址集合（格式为192.168.184.123:6001）
     */
    private final Map<String, Set<String>> configClientAddressMap;

    private CacheConfigClientAddress(){
        configClientAddressMap = new ConcurrentHashMap<>(1 << 2);
    }

    /**
     * 获取当前类的单例对象
     *
     * @return {@link CacheConfigClientAddress}
     */
    public static CacheConfigClientAddress getSingleton(){
        return INSTANCE;
    }

    public Map<String, Set<String>> getConfigClientAddressMap() {
        return configClientAddressMap;
    }

    /**
     * 给指定的客户度地址绑定一个配置
     *
     * @return {@link Boolean}
     */
    public Boolean clientAddressBindConfig(String namespaceId, String groupName,
                                           String dataId, String clientAddress){
        // 构建configClientAddressMap的key
        String key = namespaceId + "@@" + groupName + "@@" + dataId;
        Set<String> clientAddresses = configClientAddressMap.get(key);
        // 如果是第一次给该配置绑定客户端地址
        if(clientAddresses == null){
            // 初始化该配置的客户端地址集合
            clientAddresses = Collections.synchronizedSet(new HashSet<>());
        }
        // 将客户端地址放到对应的集合中
        clientAddresses.add(clientAddress);
        // 放到缓存中
        configClientAddressMap.put(key,clientAddresses);
        return true;
    }

    /**
     * 获取所有绑定该配置的客户端地址
     *
     * @return {@link List}<{@link String}>
     */
    public List<String> getClientAddressList(String namespaceId, String groupName, String dataId){
        // 构建configClientAddressMap的key
        String key = namespaceId + "@@" + groupName + "@@" + dataId;
        Set<String> clientAddresses = configClientAddressMap.get(key);
        // 如果该配置没有一个客户端地址绑定
        if(clientAddresses == null || clientAddresses.size() == 0 ){
            // 返回空集合
            return new ArrayList<>();
        }
        // 如果该配置有客户端地址绑定
        return new ArrayList<>(clientAddresses);
    }

}
