package com.grace.common.http.entity;

import com.grace.common.http.config.HttpClientConfig;
import com.grace.common.http.param.Header;
import com.grace.common.http.param.RequestParam;

import java.util.Map;


/**
 * 表示一个HTTP请求的实体类，由报头和正文组成
 *
 * @author youzhengjie
 * @date 2023/08/19 00:58:56
 */
public class RequestHttpEntity {

    private final Header header = Header.newInstance();

    private final HttpClientConfig httpClientConfig;

    private final RequestParam requestParam;
    
    private final Object requestBody;

    public RequestHttpEntity(Header header, RequestParam requestParam) {
        this(null, header, requestParam);
    }

    public RequestHttpEntity(Header header, Object requestBody) {
        this(null, header, null, requestBody);
    }

    public RequestHttpEntity(Header header, RequestParam requestParam, Object requestBody) {
        this(null, header, requestParam, requestBody);
    }

    public RequestHttpEntity(HttpClientConfig httpClientConfig, Header header, RequestParam requestParam) {
        this(httpClientConfig, header, requestParam, null);
    }

    public RequestHttpEntity(HttpClientConfig httpClientConfig, Header header, Object requestBody) {
        this(httpClientConfig, header, null, requestBody);
    }

    public RequestHttpEntity(HttpClientConfig httpClientConfig, Header header, RequestParam requestParam, Object requestBody) {
        handleRequestHeader(header);
        this.httpClientConfig = httpClientConfig;
        this.requestParam = requestParam;
        this.requestBody = requestBody;
    }

    private void handleRequestHeader(Header header) {
        if (header != null && !header.getHeader().isEmpty()) {
            Map<String, String> headerMap = header.getHeader();
            header.addAll(headerMap);
        }
    }

    public Header getRequestHeader() {
        return header;
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
