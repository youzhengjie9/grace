package com.grace.common.constant;

/**
 * Properties Key Const.
 *
 * @author youzhengjie
 * @date 2023/07/06 19:36:02
 */
public class PropertiesKeyConstant {
    
    public static final String IS_USE_CLOUD_NAMESPACE_PARSING = "isUseCloudNamespaceParsing";
    
    public static final String IS_USE_ENDPOINT_PARSING_RULE = "isUseEndpointParsingRule";
    
    public static final String ENDPOINT = "endpoint";
    
    public static final String ENDPOINT_QUERY_PARAMS = "endpointQueryParams";
    
    public static final String ENDPOINT_PORT = "endpointPort";
    
    public static final String SERVER_NAME = "serverName";
    
    public static final String NAMESPACE = "namespace";

    public static final String NAMESPACE_DESC = "namespaceDesc";

    /**
     * 如果某个指定的命名空间不存在则会自动创建该命名空间
     */
    public static final String AUTO_CREATE_NAMESPACE = "autoCreateNamespace";
    
    public static final String USERNAME = "username";
    
    public static final String PASSWORD = "password";
    
    public static final String ACCESS_KEY = "accessKey";
    
    public static final String SECRET_KEY = "secretKey";
    
    public static final String RAM_ROLE_NAME = "ramRoleName";
    
    public static final String SERVER_ADDR = "serverAddr";
    
    public static final String CONTEXT_PATH = "contextPath";
    
    public static final String CLUSTER_NAME = "clusterName";
    
    public static final String ENCODE = "encode";
    
    public static final String CONFIG_LONG_POLL_TIMEOUT = "configLongPollTimeout";
    
    public static final String CONFIG_RETRY_TIME = "configRetryTime";
    
    public static final String MAX_RETRY = "maxRetry";
    
    public static final String ENABLE_REMOTE_SYNC_CONFIG = "enableRemoteSyncConfig";
    
    public static final String NAMING_LOAD_CACHE_AT_START = "namingLoadCacheAtStart";
    
    public static final String NAMING_CACHE_REGISTRY_DIR = "namingCacheRegistryDir";
    
    public static final String NAMING_CLIENT_BEAT_THREAD_COUNT = "namingClientBeatThreadCount";
    
    public static final String NAMING_POLLING_THREAD_COUNT = "namingPollingThreadCount";
    
    public static final String NAMING_REQUEST_DOMAIN_RETRY_COUNT = "namingRequestDomainMaxRetryCount";
    
    public static final String NAMING_PUSH_EMPTY_PROTECTION = "namingPushEmptyProtection";
    
    public static final String NAMING_ASYNC_QUERY_SUBSCRIBE_SERVICE = "namingAsyncQuerySubscribeService";
    
    /**
     * Get the key value of some variable value from the system property.
     */
    public static class SystemEnv {
        
        public static final String ALIBABA_ALIWARE_ENDPOINT_PORT = "ALIBABA_ALIWARE_ENDPOINT_PORT";
        
        public static final String ALIBABA_ALIWARE_NAMESPACE = "ALIBABA_ALIWARE_NAMESPACE";
        
        public static final String ALIBABA_ALIWARE_ENDPOINT_URL = "ALIBABA_ALIWARE_ENDPOINT_URL";
    }
    
}
