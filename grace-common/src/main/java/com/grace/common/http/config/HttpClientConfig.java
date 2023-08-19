package com.grace.common.http.config;

import com.grace.common.utils.ThreadUtils;

import java.util.concurrent.TimeUnit;


/**
 * HTTP客户端配置
 *
 * @author youzhengjie
 * @date 2023/08/19 00:52:34
 */
public class HttpClientConfig {
    
    /**
     * 连接超时
     */
    private final int conTimeOutMillis;
    
    /**
     * 读超时
     */
    private final int readTimeOutMillis;
    
    /**
     * 连接存在时间
     */
    private final long connTimeToLive;
    
    /**
     * 连接存在时间的时间单位
     */
    private final TimeUnit connTimeToLiveTimeUnit;
    
    /**
     * 连接请求超时
     */
    private final int connectionRequestTimeout;
    
    /**
     * 最大重试次数
     */
    private final int maxRedirects;
    
    /**
     * 最大连接数
     */
    private final int maxConnTotal;
    
    /**
     * 分配每个路由的最大连接数
     */
    private final int maxConnPerRoute;
    
    /**
     * 是否启用HTTP压缩
     */
    private final boolean contentCompressionEnabled;
    
    /**
     * IO线程数
     */
    private final int ioThreadCount;
    
    /**
     * 用户代理
     */
    private final String userAgent;
    
    public HttpClientConfig(int conTimeOutMillis, int readTimeOutMillis, long connTimeToLive, TimeUnit timeUnit,
            int connectionRequestTimeout, int maxRedirects, int maxConnTotal, int maxConnPerRoute,
            boolean contentCompressionEnabled, int ioThreadCount, String userAgent) {
        this.conTimeOutMillis = conTimeOutMillis;
        this.readTimeOutMillis = readTimeOutMillis;
        this.connTimeToLive = connTimeToLive;
        this.connTimeToLiveTimeUnit = timeUnit;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.maxRedirects = maxRedirects;
        this.maxConnTotal = maxConnTotal;
        this.maxConnPerRoute = maxConnPerRoute;
        this.contentCompressionEnabled = contentCompressionEnabled;
        this.ioThreadCount = ioThreadCount;
        this.userAgent = userAgent;
    }
    
    public int getConTimeOutMillis() {
        return conTimeOutMillis;
    }
    
    public int getReadTimeOutMillis() {
        return readTimeOutMillis;
    }
    
    public long getConnTimeToLive() {
        return connTimeToLive;
    }
    
    public TimeUnit getConnTimeToLiveTimeUnit() {
        return connTimeToLiveTimeUnit;
    }
    
    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }
    
    public int getMaxRedirects() {
        return maxRedirects;
    }
    
    public int getMaxConnTotal() {
        return maxConnTotal;
    }
    
    public int getMaxConnPerRoute() {
        return maxConnPerRoute;
    }
    
    public boolean getContentCompressionEnabled() {
        return contentCompressionEnabled;
    }
    
    public int getIoThreadCount() {
        return ioThreadCount;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public static HttpClientConfigBuilder builder() {
        return new HttpClientConfigBuilder();
    }

    /**
     * http客户端配置构建器
     *
     * @author youzhengjie
     * @date 2023/08/19 00:57:18
     */
    public static final class HttpClientConfigBuilder {
        
        private int conTimeOutMillis = -1;
        
        private int readTimeOutMillis = -1;
        
        private long connTimeToLive = -1;
        
        private TimeUnit connTimeToLiveTimeUnit = TimeUnit.MILLISECONDS;
        
        private int connectionRequestTimeout = 5000;
        
        private int maxRedirects = 50;
        
        private int maxConnTotal = 0;
        
        private int maxConnPerRoute = 0;
        
        private boolean contentCompressionEnabled = true;
        
        private int ioThreadCount = ThreadUtils.getSuitableThreadCount(1);
        
        private String userAgent;
        
        public HttpClientConfigBuilder setConTimeOutMillis(int conTimeOutMillis) {
            this.conTimeOutMillis = conTimeOutMillis;
            return this;
        }
        
        public HttpClientConfigBuilder setReadTimeOutMillis(int readTimeOutMillis) {
            this.readTimeOutMillis = readTimeOutMillis;
            return this;
        }
        
        public HttpClientConfigBuilder setConnectionTimeToLive(long connTimeToLive, TimeUnit connTimeToLiveTimeUnit) {
            this.connTimeToLive = connTimeToLive;
            this.connTimeToLiveTimeUnit = connTimeToLiveTimeUnit;
            return this;
        }
        
        public HttpClientConfigBuilder setConnectionRequestTimeout(int connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
            return this;
        }
        
        public HttpClientConfigBuilder setMaxRedirects(int maxRedirects) {
            this.maxRedirects = maxRedirects;
            return this;
        }
        
        public HttpClientConfigBuilder setMaxConnTotal(int maxConnTotal) {
            this.maxConnTotal = maxConnTotal;
            return this;
        }
        
        public HttpClientConfigBuilder setMaxConnPerRoute(int maxConnPerRoute) {
            this.maxConnPerRoute = maxConnPerRoute;
            return this;
        }
        
        public HttpClientConfigBuilder setContentCompressionEnabled(boolean contentCompressionEnabled) {
            this.contentCompressionEnabled = contentCompressionEnabled;
            return this;
        }
        
        public HttpClientConfigBuilder setIoThreadCount(int ioThreadCount) {
            this.ioThreadCount = ioThreadCount;
            return this;
        }
        
        public HttpClientConfigBuilder setUserAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }
    
        /**
         * 构建HTTP客户端配置
         *
         * @return HttpClientConfig
         */
        public HttpClientConfig build() {
            return new HttpClientConfig(conTimeOutMillis, readTimeOutMillis, connTimeToLive, connTimeToLiveTimeUnit,
                    connectionRequestTimeout, maxRedirects, maxConnTotal, maxConnPerRoute, contentCompressionEnabled,
                    ioThreadCount, userAgent);
        }
    }
}
