package com.grace.client.config.cache;

import com.grace.client.config.cache.entity.CacheConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存配置的管理器 <p>
 * 缓存我们 “从配置中心获取并加载”到PropertySource的自定义配置（注意: 这里说的配置不是application.yml和bootstrap.yml的配置）
 *
 * @author youzhengjie
 * @date 2023/10/29 14:53:17
 */
public class CacheConfigManager {

    private static final CacheConfigManager INSTANCE= new CacheConfigManager();


    /**
     * 缓存我们 “从配置中心获取并加载”到PropertySource的自定义配置 <p>
     * 注意: 这里说的配置不是application.yml和bootstrap.yml的配置 <p>
     *
     * Map的key格式为: 命名空间id@@分组名@@dataId（例如: test@@DEFAULT_GROUP@@application-dev.yml ）
     * <p>
     * Map的value是: 该对应配置的内容和md5值等数据
     *
     */
    private static final Map<String, CacheConfig> cacheConfigMap =
            new ConcurrentHashMap<>(1 << 2);

    /**
     * 单例
     */
    private CacheConfigManager(){

    }

    /**
     * 获取当前类的单例对象
     *
     * @return {@link CacheConfigManager}
     */
    public static CacheConfigManager getSingleton(){
        return INSTANCE;
    }

    public static Map<String, CacheConfig> getCacheConfigMap() {
        return cacheConfigMap;
    }

    /**
     * 放入缓存配置（存在则修改、不存在的新增）
     *
     * @return {@link Boolean}
     */
    public Boolean putCacheConfig(String namespaceId, String groupName,
                                  String dataId, String content, String md5){
        // 构建配置缓存Map的key
        String key = namespaceId + "@@" + groupName + "@@" + dataId;
        // 创建缓存配置的对象
        CacheConfig cacheConfig = new CacheConfig(content,md5);
        // 放入缓存配置的Map中（存在则修改、不存在的新增）
        cacheConfigMap.put(key, cacheConfig);
        return true;
    }

    /**
     * 获取缓存的配置
     *
     * @return {@link CacheConfig}
     */
    public CacheConfig getCacheConfig(String namespaceId, String groupName, String dataId){
        // 构建缓存配置Map的key
        String key = namespaceId + "@@" + groupName + "@@" + dataId;
        return cacheConfigMap.get(key);
    }

}
