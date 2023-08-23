package com.grace.common.http.client;

import com.grace.common.constant.RequestMethodConstants;
import com.grace.common.http.RestResult;
import com.grace.common.http.config.HttpClientConfig;
import com.grace.common.http.convert.HttpClientResponseRestResultConverter;
import com.grace.common.http.param.Header;
import com.grace.common.http.param.MediaType;
import com.grace.common.http.param.RequestParam;
import com.grace.common.http.request.HttpClientRequest;
import com.grace.common.http.response.HttpClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * GraceRestTemplate
 *
 * @author youzhengjie
 * @date 2023/08/14 21:43:24
 */
public class GraceRestTemplate implements GraceRestOperations{

    private final Logger log;

    private final HttpClientRequest httpClientRequest;


    public GraceRestTemplate(HttpClientRequest httpClientRequest) {
        this.httpClientRequest = httpClientRequest;
        this.log = LoggerFactory.getLogger(GraceRestTemplate.class);
    }


    /**
     * 执行发送请求
     *
     * @param url              url
     * @param requestHeader    请求头
     * @param requestParam     请求参数（没有请求参数的话可以传null值）
     * @param requestMethod    请求方法
     * @param requestBody      请求体（没有请求体的话可以传null值）
     * @param httpClientConfig http客户端配置（不需要传配置则传null值）
     * @param responseType     RestResult的data对象类型（也就是响应结果类型）
     * @return {@link RestResult}<{@link T}>
     * @throws URISyntaxException urisyntax例外
     */
    private <T> RestResult<T> execute(String url, String requestMethod, Header requestHeader,
                                      RequestParam requestParam, Object requestBody,
                                      HttpClientConfig httpClientConfig, Type responseType) throws Exception {
        // 构建uri（将url和请求参数进行拼接）格式为（例如https://www.baidu.com/s?wd=你好）
        URI uri = buildUri(url, requestParam);
        if (log.isDebugEnabled()) {
            log.debug("HTTP method: {}, url: {}, body: {}", requestMethod, uri, requestBody);
        }

        // 发送请求,接收apache httpclient的响应结果（httpClientResponse）
        HttpClientResponse httpClientResponse =
                getHttpClientRequest().sendRequest(uri, requestMethod, requestHeader, requestBody, httpClientConfig);

        HttpClientResponseRestResultConverter<T> httpClientResponseRestResultConverter = new HttpClientResponseRestResultConverter<>();

        // 将apache httpclient的响应结果（httpClientResponse）
        // 转换成我们自己写的响应结果RestResult（这才是GraceRestTemplate需要的响应结果）
        return httpClientResponseRestResultConverter.convert(httpClientResponse);
    }

    private HttpClientRequest getHttpClientRequest() {
        // TODO: 2023/8/21 未完成
//        if (CollectionUtils.isNotEmpty(interceptors)) {
//            if (logger.isDebugEnabled()) {
//                logger.debug("Execute via interceptors :{}", interceptors);
//            }
//            return new InterceptingHttpClientRequest(requestClient, interceptors.iterator());
//        }
        return httpClientRequest;
    }

    /**
     * 构建uri（将url和请求参数进行拼接）格式为（例如https://www.baidu.com/s?wd=你好）
     *
     * @param url          url
     * @param requestParam 请求参数
     * @return {@link URI}
     * @throws URISyntaxException urisyntax例外
     */
    static URI buildUri(String url, RequestParam requestParam) throws URISyntaxException {
        if (requestParam != null && !requestParam.isEmpty()) {
            url = url + "?" + requestParam.toRequestParamString();
        }
        return new URI(url);
    }

    @Override
    public <T> RestResult<T> get(String url, Header requestHeader, RequestParam requestParam, Type responseType) throws Exception {
        return execute(
                url,
                RequestMethodConstants.GET,
                requestHeader,
                requestParam,
                null,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> get(String url, Header requestHeader, RequestParam requestParam,
                                 HttpClientConfig httpClientConfig, Type responseType) throws Exception {
        return execute(
                url,
                RequestMethodConstants.GET,
                requestHeader,
                requestParam,
                null,
                httpClientConfig,
                responseType);
    }

    @Override
    public <T> RestResult<T> getLarge(String url, Header requestHeader, RequestParam requestParam,
                                      Object requestBody, Type responseType) throws Exception {
        return execute(
                url,
                RequestMethodConstants.GET_LARGE,
                requestHeader,
                requestParam,
                requestBody,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> getLarge(String url, Header requestHeader, RequestParam requestParam,
                                      String requestBodyJson, Type responseType) throws Exception {

        // 因为请求体（requestBodyJson）是JSON字符串类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

        return execute(
                url,
                RequestMethodConstants.GET_LARGE,
                requestHeader,
                requestParam,
                requestBodyJson,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> getLarge(String url, Header requestHeader, RequestParam requestParam,
                                      Map<String, String> requestBodyMap, Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                RequestMethodConstants.GET_LARGE,
                requestHeader,
                requestParam,
                requestBodyMap,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> post(String url, Header requestHeader, RequestParam requestParam,
                                  Object requestBody, Type responseType) throws Exception {

        return execute(
                url,
                RequestMethodConstants.POST,
                requestHeader,
                requestParam,
                requestBody,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> post(String url, Header requestHeader, RequestParam requestParam,
                                  String requestBodyJson, Type responseType) throws Exception {

        // 因为请求体（requestBodyJson）是JSON字符串类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

        return execute(
                url,
                RequestMethodConstants.POST,
                requestHeader,
                requestParam,
                requestBodyJson,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> post(String url, Header requestHeader,
                                  String requestBodyJson, Type responseType) throws Exception {

        // 因为请求体（requestBodyJson）是JSON字符串类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

        return execute(
                url,
                RequestMethodConstants.POST,
                requestHeader,
                null,
                requestBodyJson,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> post(String url, Header requestHeader, RequestParam requestParam,
                                  Map<String, String> requestBodyMap, Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                RequestMethodConstants.POST,
                requestHeader,
                requestParam,
                requestBodyMap,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> post(String url, Header requestHeader,
                                  Map<String, String> requestBodyMap, Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                RequestMethodConstants.POST,
                requestHeader,
                null,
                requestBodyMap,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> post(String url, Header requestHeader, Map<String, String> requestBodyMap,
                                  HttpClientConfig httpClientConfig, Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                RequestMethodConstants.POST,
                requestHeader,
                null,
                requestBodyMap,
                httpClientConfig,
                responseType);
    }

    @Override
    public <T> RestResult<T> put(String url, Header requestHeader, RequestParam requestParam,
                                 Object requestBody, Type responseType) throws Exception {
        return execute(
                url,
                RequestMethodConstants.PUT,
                requestHeader,
                requestParam,
                requestBody,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> put(String url, Header requestHeader, RequestParam requestParam,
                                 String requestBodyJson, Type responseType) throws Exception {

        // 因为请求体（requestBodyJson）是JSON字符串类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

        return execute(
                url,
                RequestMethodConstants.PUT,
                requestHeader,
                requestParam,
                requestBodyJson,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> put(String url, Header requestHeader, String requestBodyJson, Type responseType) throws Exception {

        // 因为请求体（requestBodyJson）是JSON字符串类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

        return execute(
                url,
                RequestMethodConstants.PUT,
                requestHeader,
                null,
                requestBodyJson,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> put(String url, Header requestHeader, RequestParam requestParam,
                                 Map<String, String> requestBodyMap, Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                RequestMethodConstants.PUT,
                requestHeader,
                requestParam,
                requestBodyMap,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> put(String url, Header requestHeader,
                                 Map<String, String> requestBodyMap, Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                RequestMethodConstants.PUT,
                requestHeader,
                null,
                requestBodyMap,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> put(String url, Header requestHeader, Map<String, String> requestBodyMap,
                                 HttpClientConfig httpClientConfig, Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                RequestMethodConstants.PUT,
                requestHeader,
                null,
                requestBodyMap,
                httpClientConfig,
                responseType);

    }

    @Override
    public <T> RestResult<T> delete(String url, Header requestHeader, RequestParam requestParam, Type responseType) throws Exception {

        return execute(
                url,
                RequestMethodConstants.DELETE,
                requestHeader,
                requestParam,
                null,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> delete(String url, Header requestHeader, RequestParam requestParam, HttpClientConfig httpClientConfig, Type responseType) throws Exception {

        return execute(
                url,
                RequestMethodConstants.DELETE,
                requestHeader,
                requestParam,
                null,
                httpClientConfig,
                responseType);
    }

    @Override
    public <T> RestResult<T> exchange(String url, String requestMethod, Header requestHeader,
                                      RequestParam requestParam, Object requestBody,
                                      HttpClientConfig httpClientConfig, Type responseType) throws Exception {

        return execute(
                url,
                requestMethod,
                requestHeader,
                requestParam,
                requestBody,
                httpClientConfig,
                responseType);
    }

    @Override
    public <T> RestResult<T> exchange(String url, String requestMethod, Header requestHeader,
                                      RequestParam requestParam, String requestBodyJson,
                                      Type responseType) throws Exception {

        // 因为请求体（requestBodyJson）是JSON字符串类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

        return execute(
                url,
                requestMethod,
                requestHeader,
                requestParam,
                requestBodyJson,
                null,
                responseType);
    }

    @Override
    public <T> RestResult<T> exchange(String url, String requestMethod, Header requestHeader,
                                      RequestParam requestParam, Map<String, String> requestBodyMap,
                                      Type responseType) throws Exception {

        // 因为请求体（requestBodyMap）是Map类型,所以要将
        // 请求头（requestHeader）中的Content-Type设置为application/x-www-form-urlencoded;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return execute(
                url,
                requestMethod,
                requestHeader,
                requestParam,
                requestBodyMap,
                null,
                responseType);

    }


}
