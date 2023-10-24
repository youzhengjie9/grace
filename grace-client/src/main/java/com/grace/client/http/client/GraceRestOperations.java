package com.grace.client.http.client;

import com.grace.client.http.config.HttpClientConfig;
import com.grace.client.http.RestResult;
import com.grace.client.http.param.RequestHeader;
import com.grace.client.http.param.RequestParam;

import java.util.Map;

/**
 * GraceRestTemplate的方法定义
 *
 * @author youzhengjie
 * @date 2023/08/14 21:42:38
 */
public interface GraceRestOperations {

    <T> RestResult<T> get(String url, RequestHeader requestHeader, RequestParam requestParam, Class<T> responseType) throws Exception;

    <T> RestResult<T> get(String url, RequestHeader requestHeader, RequestParam requestParam,
                          HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception;

    <T> RestResult<T> getLarge(String url, RequestHeader requestHeader, RequestParam requestParam,
                               Map<String, String> requestBodyMap, Class<T> responseType) throws Exception;

    <T> RestResult<T> post(String url, RequestHeader requestHeader, RequestParam requestParam,
                           Map<String, String> requestBodyMap, Class<T> responseType) throws Exception;

    <T> RestResult<T> post(String url, RequestHeader requestHeader,
                                   Map<String, String> requestBodyMap, Class<T> responseType) throws Exception;

    <T> RestResult<T> post(String url, RequestHeader requestHeader, Map<String, String> requestBodyMap,
                           HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception;

    <T> RestResult<T> put(String url, RequestHeader requestHeader, RequestParam requestParam,
                          Map<String, String> requestBodyMap, Class<T> responseType) throws Exception;

    <T> RestResult<T> put(String url, RequestHeader requestHeader, Map<String, String> requestBodyMap,
                          Class<T> responseType) throws Exception;

    <T> RestResult<T> put(String url, RequestHeader requestHeader, Map<String, String> requestBodyMap,
                          HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception;

    <T> RestResult<T> delete(String url, RequestHeader requestHeader, RequestParam requestParam,
                             Class<T> responseType) throws Exception;

    <T> RestResult<T> delete(String url, RequestHeader requestHeader, RequestParam requestParam,
                             HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception;

    /**
     * 用于发送所有类型的请求（例如get、post、put、delete等）
     *
     * @param url url
     * @param requestMethod 请求方法
     * @param requestHeader 请求头
     * @param requestParam 请求参数（没有请求参数的话可以传null值）
     * @param requestBodyMap map类型的请求体（没有请求体的话可以传null值）
     * @param responseType  RestResult的data对象类型（也就是响应结果类型）
     * @return {@link RestResult}<{@link T}>
     * @throws Exception 异常
     */
    <T> RestResult<T> exchange(String url, String requestMethod, RequestHeader requestHeader, RequestParam requestParam,
                               Map<String, String> requestBodyMap, Class<T> responseType) throws Exception;


}
