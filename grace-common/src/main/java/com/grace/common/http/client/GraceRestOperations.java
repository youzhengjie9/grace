package com.grace.common.http.client;

import com.grace.common.http.entity.RequestHttpEntity;
import com.grace.common.utils.ResponseResult;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Map;

/**
 * GraceRestTemplate的方法定义
 *
 * @author youzhengjie
 * @date 2023/08/14 21:42:38
 */
public interface GraceRestOperations {

    <T> ResponseResult<T> get(String url, Class<T> responseType);

    <T> ResponseResult<T> get(String url, Class<T> responseType, Map<String, ?> uriVariables);

    <T> ResponseResult<T> post(String url, @Nullable Object request, Class<T> responseType);

    <T> ResponseResult<T> post(String url, @Nullable Object request, Class<T> responseType, Map<String, ?> uriVariables);

    <T> ResponseResult<T> put(String url, Class<T> responseType, @Nullable Object request, Map<String, ?> uriVariables);

    <T> ResponseResult<T> put(String url, Class<T> responseType , @Nullable Object request);

    <T> ResponseResult<T> delete(String url , Class<T> responseType);

    <T> ResponseResult<T> delete(String url, Class<T> responseType, Map<String, ?> uriVariables);

    <T> ResponseEntity<T> exchange(String url, HttpMethod method, Class<T> responseType, Map<String, String> bodyValues);

    <T> ResponseResult<T> execute(String url, String httpMethod, RequestHttpEntity requestEntity,
                                          Type responseType);

}
