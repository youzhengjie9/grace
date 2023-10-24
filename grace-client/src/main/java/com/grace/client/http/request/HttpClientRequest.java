package com.grace.client.http.request;

import com.grace.client.http.config.HttpClientConfig;
import com.grace.client.http.param.RequestHeader;
import com.grace.client.http.response.HttpClientResponse;

import java.net.URI;
import java.util.Map;

/**
 * http客户端请求,作用是发送请求
 *
 * @author youzhengjie
 * @date 2023/08/17 23:27:07
 */
public interface HttpClientRequest {

    /**
     * 发送http请求
     *
     * @param uri              已经含有请求参数的uri（如果没有请求参数也可以）。格式为（例如https://www.baidu.com/s?wd=你好）
     * @param requestMethod    请求方法
     * @param requestHeader    请求头
     * @param requestBodyMap      请求体Map（没有请求体的话可以传null值）
     * @param httpClientConfig http客户端配置（不需要传配置则传null值）
     * @return {@link HttpClientResponse}
     * @throws Exception 异常
     */
    HttpClientResponse sendRequest(URI uri, String requestMethod, RequestHeader requestHeader,
                                   Map<String, String> requestBodyMap, HttpClientConfig httpClientConfig) throws Exception;


}
