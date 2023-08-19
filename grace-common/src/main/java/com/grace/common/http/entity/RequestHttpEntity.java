package com.grace.common.http.entity;

import com.grace.common.http.config.HttpClientConfig;
import com.grace.common.http.param.RequestHeader;
import com.grace.common.http.param.RequestParam;

import java.util.Map;


/**
 * 表示一个HTTP请求的实体类，由报头和正文组成
 *
 * @author youzhengjie
 * @date 2023/08/19 00:58:56
 */
public class RequestHttpEntity {

    private final RequestHeader requestHeader = RequestHeader.newInstance();

    private final HttpClientConfig httpClientConfig;

    private final RequestParam requestParam;
    
    private final Object requestBody;

    public RequestHttpEntity(RequestHeader requestHeader, RequestParam requestParam) {
        this(null, requestHeader, requestParam);
    }

    public RequestHttpEntity(RequestHeader requestHeader, Object requestBody) {
        this(null, requestHeader, null, requestBody);
    }

    public RequestHttpEntity(RequestHeader requestHeader, RequestParam requestParam, Object requestBody) {
        this(null, requestHeader, requestParam, requestBody);
    }

    public RequestHttpEntity(HttpClientConfig httpClientConfig, RequestHeader requestHeader, RequestParam requestParam) {
        this(httpClientConfig, requestHeader, requestParam, null);
    }

    public RequestHttpEntity(HttpClientConfig httpClientConfig, RequestHeader requestHeader, Object requestBody) {
        this(httpClientConfig, requestHeader, null, requestBody);
    }

    public RequestHttpEntity(HttpClientConfig httpClientConfig, RequestHeader requestHeader, RequestParam requestParam, Object requestBody) {
        handleRequestHeader(requestHeader);
        this.httpClientConfig = httpClientConfig;
        this.requestParam = requestParam;
        this.requestBody = requestBody;
    }

    private void handleRequestHeader(RequestHeader requestHeader) {
        if (requestHeader != null && !requestHeader.getHeader().isEmpty()) {
            Map<String, String> headerMap = requestHeader.getHeader();
            requestHeader.addAll(headerMap);
        }
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public RequestParam getRequestParam() {
        return requestParam;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public HttpClientConfig getHttpClientConfig() {
        return httpClientConfig;
    }

    public boolean isEmptyRequestBody() {
        return requestBody == null;
    }

    
}
