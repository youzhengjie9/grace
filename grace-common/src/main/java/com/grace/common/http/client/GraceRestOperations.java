package com.grace.common.http.client;

import com.grace.common.http.RestResult;
import com.grace.common.http.config.HttpClientConfig;
import com.grace.common.http.param.Header;
import com.grace.common.http.param.RequestParam;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * GraceRestTemplate的方法定义
 *
 * @author youzhengjie
 * @date 2023/08/14 21:42:38
 */
public interface GraceRestOperations {

    <T> RestResult<T> get(String url, Header requestHeader, RequestParam requestParam, Type responseType) throws Exception;

    <T> RestResult<T> get(String url, Header requestHeader, RequestParam requestParam,
                          HttpClientConfig httpClientConfig, Type responseType) throws Exception;

    <T> RestResult<T> getLarge(String url, Header requestHeader, RequestParam requestParam,
                                   Object requestBody, Type responseType) throws Exception;

    <T> RestResult<T> getLarge(String url, Header requestHeader, RequestParam requestParam,
                               String requestBodyJson, Type responseType) throws Exception;

    <T> RestResult<T> getLarge(String url, Header requestHeader, RequestParam requestParam,
                               Map<String, String> requestBodyMap, Type responseType) throws Exception;

    <T> RestResult<T> post(String url, Header requestHeader, RequestParam requestParam,
                               Object requestBody, Type responseType) throws Exception;

    <T> RestResult<T> post(String url, Header requestHeader, RequestParam requestParam,
                               String requestBodyJson, Type responseType) throws Exception;

    <T> RestResult<T> post(String url, Header requestHeader, String requestBodyJson, Type responseType) throws Exception;

    <T> RestResult<T> post(String url, Header requestHeader, RequestParam requestParam,
                                   Map<String, String> requestBodyMap, Type responseType) throws Exception;

    <T> RestResult<T> post(String url, Header requestHeader,
                                   Map<String, String> requestBodyMap, Type responseType) throws Exception;

    <T> RestResult<T> post(String url, Header requestHeader, Map<String, String> requestBodyMap,
                           HttpClientConfig httpClientConfig, Type responseType) throws Exception;

    <T> RestResult<T> put(String url, Header requestHeader, RequestParam requestParam,
                              Object requestBody, Type responseType) throws Exception;

    <T> RestResult<T> put(String url, Header requestHeader, RequestParam requestParam,
                              String requestBodyJson, Type responseType) throws Exception;

    <T> RestResult<T> put(String url, Header requestHeader, String requestBodyJson, Type responseType) throws Exception;

    <T> RestResult<T> put(String url, Header requestHeader, RequestParam requestParam,
                                  Map<String, String> requestBodyMap, Type responseType) throws Exception;

    <T> RestResult<T> put(String url, Header requestHeader, Map<String, String> requestBodyMap,
                          Type responseType) throws Exception;

    <T> RestResult<T> put(String url, Header requestHeader, Map<String, String> requestBodyMap,
                          HttpClientConfig httpClientConfig, Type responseType) throws Exception;

    <T> RestResult<T> delete(String url, Header requestHeader, RequestParam requestParam, Type responseType) throws Exception;

    <T> RestResult<T> delete(String url, Header requestHeader, RequestParam requestParam,
                             HttpClientConfig httpClientConfig, Type responseType) throws Exception;

    <T> RestResult<T> exchange(String url, String requestMethod, Header requestHeader,
                               RequestParam requestParam, Object requestBody,
                               HttpClientConfig httpClientConfig, Type responseType);

    <T> RestResult<T> exchange(String url, String requestMethod, Header requestHeader,
                               RequestParam requestParam, String requestBodyJson,
                               Type responseType);

    <T> RestResult<T> exchange(String url, String requestMethod, Header requestHeader, RequestParam requestParam,
                               Map<String, String> requestBodyMap, Type responseType);


}
