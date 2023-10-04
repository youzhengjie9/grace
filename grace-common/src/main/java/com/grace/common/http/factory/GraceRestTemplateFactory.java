package com.grace.common.http.factory;

import com.grace.common.http.client.GraceRestTemplate;
import com.grace.common.http.config.HttpClientConfig;
import com.grace.common.http.request.DefaultHttpClientRequest;
import com.grace.common.http.request.HttpClientRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.RequestContent;

/**
 * GraceRestTemplate工厂
 * （作用是更加方便的创建GraceRestTemplate对象，因为直接创建GraceRestTemplate对象比较复杂,需要传很多参数。）
 *
 * @author youzhengjie
 * @date 2023/09/29 00:31:40
 */
public class GraceRestTemplateFactory {

    /**
     * 超时时间（毫秒）
     */
    private static final int TIME_OUT_MILLIS = 10000;

    /**
     * 连接超时时间（毫秒）
     */
    private static final int CON_TIME_OUT_MILLIS = 5000;


    /**
     * 私有化构造器
     */
    private GraceRestTemplateFactory(){

    }

    /**
     * 获取GraceRestTemplateFactory的单例对象
     *
     * @return {@link GraceRestTemplateFactory}
     */
    public static GraceRestTemplateFactory getInstance(){

        return GraceRestTemplateFactoryInstance.INSTANCE;
    }

    public GraceRestTemplate createGraceRestTemplate() {
        // 构建HttpClientConfig对象
        final HttpClientConfig httpClientConfig = buildSyncHttpClientConfig();
        // 构建RequestConfig对象
        final RequestConfig defaultRequestConfig = buildRequestConfig();
        // 创建CloseableHttpClient对象
        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .addInterceptorLast(new RequestContent(true))
                .setDefaultRequestConfig(defaultRequestConfig)
                .setUserAgent(httpClientConfig.getUserAgent())
                .setMaxConnTotal(httpClientConfig.getMaxConnTotal())
                .setMaxConnPerRoute(httpClientConfig.getMaxConnPerRoute())
                .setConnectionTimeToLive(httpClientConfig.getConnTimeToLive(), httpClientConfig.getConnTimeToLiveTimeUnit())
                .build();
        // 创建HttpClientRequest对象
        HttpClientRequest defaultHttpClientRequest =
                new DefaultHttpClientRequest(closeableHttpClient,defaultRequestConfig);
        // 创建GraceRestTemplate对象
        return new GraceRestTemplate(defaultHttpClientRequest);
    }

    /**
     * 构建同步的HttpClientConfig对象
     *
     * @return {@link HttpClientConfig}
     */
    protected HttpClientConfig buildSyncHttpClientConfig() {
        return HttpClientConfig.builder().setConTimeOutMillis(CON_TIME_OUT_MILLIS)
                .setReadTimeOutMillis(TIME_OUT_MILLIS).setMaxRedirects(0).build();
    }

    /**
     * 构建RequestConfig对象
     *
     * @return {@link RequestConfig}
     */
    protected RequestConfig buildRequestConfig() {
        // 构建同步的HttpClientConfig对象
        HttpClientConfig httpClientConfig = buildSyncHttpClientConfig();
        return RequestConfig.custom().setConnectTimeout(httpClientConfig.getConTimeOutMillis())
                .setSocketTimeout(httpClientConfig.getReadTimeOutMillis())
                .setConnectionRequestTimeout(httpClientConfig.getConnectionRequestTimeout())
                .setContentCompressionEnabled(httpClientConfig.getContentCompressionEnabled())
                .setMaxRedirects(httpClientConfig.getMaxRedirects()).build();
    }


    private static final class GraceRestTemplateFactoryInstance {

        /**
         * GraceRestTemplateFactory的单例对象
         */
        private static final GraceRestTemplateFactory INSTANCE = new GraceRestTemplateFactory();

    }

}
