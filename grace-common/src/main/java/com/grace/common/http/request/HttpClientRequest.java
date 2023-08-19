package com.grace.common.http.request;

import com.grace.common.http.entity.RequestHttpEntity;
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
     * @param uri               http url
     * @param httpMethod        http request method
     * @param requestHttpEntity http request entity
     * @return HttpClientResponse
     * @throws Exception ex
     */
    HttpClientResponse sendRequest(URI uri, String httpMethod, RequestHttpEntity requestHttpEntity) throws Exception;


}
