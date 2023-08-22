package com.grace.common.http.request;

import com.grace.common.http.config.HttpClientConfig;
import com.grace.common.http.entity.RequestHttpEntity;
import com.grace.common.http.param.Header;
import com.grace.common.http.param.RequestParam;
import com.grace.common.http.response.HttpClientResponse;

import java.net.URI;

/**
 * http客户端请求
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
     * @param requestBody      请求体
     * @param httpClientConfig http客户端配置
     * @return {@link HttpClientResponse}
     * @throws Exception 异常
     */
    HttpClientResponse sendRequest(URI uri, String requestMethod, Header requestHeader,
                                   Object requestBody, HttpClientConfig httpClientConfig) throws Exception;


}
