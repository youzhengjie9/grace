package com.grace.client.http.client;

import com.grace.client.http.config.HttpClientConfig;
import com.grace.client.http.convert.HttpClientResponseRestResultConverter;
import com.grace.client.http.request.HttpClientRequest;
import com.grace.common.constant.RequestMethodConstants;
import com.grace.client.http.RestResult;
import com.grace.client.http.param.RequestHeader;
import com.grace.client.http.param.MediaType;
import com.grace.client.http.param.RequestParam;
import com.grace.client.http.response.HttpClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * 通过操作HttpClientRequest类（底层为Apache HttpClient）发送api请求
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
     * @param requestBodyMap      请求体map（没有请求体的话可以传null值）
     * @param httpClientConfig http客户端配置（不需要传配置则传null值）
     * @param responseType     RestResult的data对象类型（也就是响应结果类型）
     * @return {@link RestResult}<{@link T}>
     */
    private <T> RestResult<T> execute(String url, String requestMethod, RequestHeader requestHeader,
                                      RequestParam requestParam, Map<String, String> requestBodyMap,
                                      HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception {
        // 构建uri（将url和请求参数进行拼接）格式为（例如https://www.baidu.com/s?wd=你好）
        URI uri = buildUri(url, requestParam);
        System.out.println("发送的uri="+uri);
        if (log.isDebugEnabled()) {
            log.debug("HTTP method: {}, url: {}, body: {}", requestMethod, uri, requestBodyMap);
        }

        // 发送请求,接收apache httpclient的响应结果（httpClientResponse）
        HttpClientResponse httpClientResponse =
                getHttpClientRequest().sendRequest(uri, requestMethod, requestHeader, requestBodyMap, httpClientConfig);

        HttpClientResponseRestResultConverter<T> httpClientResponseRestResultConverter =
                new HttpClientResponseRestResultConverter<>();

        // 将apache httpclient的响应结果（httpClientResponse）
        // 转换成我们自己写的响应结果RestResult（这才是GraceRestTemplate需要的响应结果）
        return httpClientResponseRestResultConverter.convert(httpClientResponse);
    }

    private HttpClientRequest getHttpClientRequest() {
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
            url = url + requestParam.toRequestParamString();
        }
        return new URI(url);
    }

    @Override
    public <T> RestResult<T> get(String url, RequestHeader requestHeader, RequestParam requestParam,
                                 Class<T> responseType) throws Exception {
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
    public <T> RestResult<T> get(String url, RequestHeader requestHeader, RequestParam requestParam,
                                 HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception {
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
    public <T> RestResult<T> getLarge(String url, RequestHeader requestHeader, RequestParam requestParam,
                                      Map<String, String> requestBodyMap, Class<T> responseType) throws Exception {

        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

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
    public <T> RestResult<T> post(String url, RequestHeader requestHeader, RequestParam requestParam,
                                  Map<String, String> requestBodyMap, Class<T> responseType) throws Exception {

        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

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
    public <T> RestResult<T> post(String url, RequestHeader requestHeader,
                                  Map<String, String> requestBodyMap, Class<T> responseType) throws Exception {

        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

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
    public <T> RestResult<T> post(String url, RequestHeader requestHeader, Map<String, String> requestBodyMap,
                                  HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception {

        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

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
    public <T> RestResult<T> put(String url, RequestHeader requestHeader, RequestParam requestParam,
                                 Map<String, String> requestBodyMap, Class<T> responseType) throws Exception {

        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

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
    public <T> RestResult<T> put(String url, RequestHeader requestHeader,
                                 Map<String, String> requestBodyMap, Class<T> responseType) throws Exception {

        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

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
    public <T> RestResult<T> put(String url, RequestHeader requestHeader, Map<String, String> requestBodyMap,
                                 HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception {

        // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
        requestHeader.setContentType(MediaType.APPLICATION_JSON);

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
    public <T> RestResult<T> delete(String url, RequestHeader requestHeader, RequestParam requestParam,
                                    Class<T> responseType) throws Exception {

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
    public <T> RestResult<T> delete(String url, RequestHeader requestHeader, RequestParam requestParam,
                                    HttpClientConfig httpClientConfig, Class<T> responseType) throws Exception {

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
    public <T> RestResult<T> exchange(String url, String requestMethod, RequestHeader requestHeader,
                                      RequestParam requestParam, Map<String, String> requestBodyMap,
                                      Class<T> responseType) throws Exception {

        // 只要下面这几种请求类型才需要设置请求体，其他都不需要
        if(requestMethod.equalsIgnoreCase(RequestMethodConstants.GET_LARGE)
                || requestMethod.equalsIgnoreCase(RequestMethodConstants.POST)
                || requestMethod.equalsIgnoreCase(RequestMethodConstants.PUT)
                || requestMethod.equalsIgnoreCase(RequestMethodConstants.DELETE_LARGE)) {
            // 请求头（requestHeader）中的Content-Type设置为application/json;charset=UTF-8
            requestHeader.setContentType(MediaType.APPLICATION_JSON);
        }

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
