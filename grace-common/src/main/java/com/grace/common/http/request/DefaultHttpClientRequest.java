package com.grace.common.http.request;

import com.alibaba.fastjson2.JSON;
import com.grace.common.constant.RequestHeaderConstants;
import com.grace.common.enums.RequestMethod;
import com.grace.common.http.config.HttpClientConfig;
import com.grace.common.http.param.Header;
import com.grace.common.http.param.MediaType;
import com.grace.common.http.response.DefaultHttpClientResponse;
import com.grace.common.http.response.HttpClientResponse;
import com.grace.common.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 默认的http客户端请求（默认底层使用的是apache的httpclient发送请求）
 *
 * @author youzhengjie
 * @date 2023/08/17 23:19:31
 */
public class DefaultHttpClientRequest implements HttpClientRequest {

    /**
     * Apache HttpClient对象（发送请求全靠这个类，非常重要！）
     */
    private final CloseableHttpClient httpClient;

    /**
     * Apache HttpClient默认的请求配置
     */
    private final RequestConfig defaultRequestConfig;

    public DefaultHttpClientRequest(CloseableHttpClient httpClient, RequestConfig defaultRequestConfig) {
        this.httpClient = httpClient;
        this.defaultRequestConfig = defaultRequestConfig;
    }

    @Override
    public HttpClientResponse sendRequest(URI uri, String requestMethod, Header requestHeader, Object requestBody, HttpClientConfig httpClientConfig) throws Exception {
        // 构建一个http请求
        HttpRequestBase request = buildHttpRequest(
                uri, requestMethod, requestHeader,
                requestBody, httpClientConfig,
                defaultRequestConfig
        );

        // 发送http请求，接收apache httpclient的响应结果
        CloseableHttpResponse response = httpClient.execute(request);

        // 将apache httpclient的响应结果封装到DefaultHttpClientResponse对象中
        return new DefaultHttpClientResponse(response);
    }


    static HttpRequestBase buildHttpRequest(URI uri, String requestMethod, Header requestHeader,
                                            Object requestBody, HttpClientConfig httpClientConfig,
                                            RequestConfig defaultConfig) throws Exception {

        // 创建最基础的（除了uri（注意: 这个uri在上一步已经包含了请求参数了！）和请求方法外,无其他任何属性的）HttpRequestBase对象
        HttpRequestBase baseHttpRequestBase = createBaseHttpRequestBase(uri, requestMethod);
        // 初始化请求头,将请求头放到baseHttpRequestBase中
        initRequestHeader(baseHttpRequestBase, requestHeader);
        // 如果是POST表单（form）提交方式，并且请求体格式是Map
        if (MediaType.APPLICATION_FORM_URLENCODED.equals(requestHeader.getValue(RequestHeaderConstants.CONTENT_TYPE))
                && requestBody instanceof Map) {
            // 设置请求体
            setRequestBody(baseHttpRequestBase, (Map<String, String>) requestBody, requestHeader.getCharset());
        }
        // 不是POST表单（form）提交方式，或者请求体格式不是Map
        else {
            // 设置请求体
            setRequestBody(baseHttpRequestBase, requestBody, requestHeader);
        }
        // 合并默认配置
        mergeDefaultConfig(baseHttpRequestBase, httpClientConfig, defaultConfig);
        return baseHttpRequestBase;
    }

    /**
     * 创建最基础的（除了uri和请求方法外,无其他任何属性的）HttpRequestBase对象
     *
     * @return {@link HttpRequestBase}
     */
    static HttpRequestBase createBaseHttpRequestBase(URI uri,String method){

        // 取出HttpMethod枚举类所有枚举值
        RequestMethod[] requestMethods = RequestMethod.values();

        for (RequestMethod requestMethod : requestMethods) {
            // 挑选出该请求方法的枚举值
            if (StringUtils.equalsIgnoreCase(method, requestMethod.getMethodName())) {
                // 调用该枚举值重写的createRequest方法
                return requestMethod.createRequest(uri.toString());
            }
        }
        throw new IllegalArgumentException("Unsupported http method : " + method);
    }

    /**
     * 初始化请求头,将请求头放到baseHttpRequestBase中
     *
     * @param baseHttpRequestBase   baseHttpRequestBase
     * @param requestHeader 请求头
     */
    static void initRequestHeader(HttpRequestBase baseHttpRequestBase, Header requestHeader) {
        Iterator<Map.Entry<String, String>> iterator = requestHeader.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            baseHttpRequestBase.setHeader(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 前提：只有baseHttpRequestBase的类是HttpEntityEnclosingRequest的实现类才能设置请求体，
     * 1：如果baseHttpRequestBase的类是HttpGetLarge、HttpDeleteLarge、HttpPut、HttpPost则会设置请求体（则这个方法会生效），
     * 2：而如果baseHttpRequestBase的类是HttpGet、HttpDelete则无法设置请求体（则>>失效<<！！！）
     *
     * @param baseHttpRequestBase http请求
     * @param requestBodyMap         请求体的map
     * @param charset             字符集
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    private static void setRequestBody(HttpRequestBase baseHttpRequestBase,
                                       Map<String, String> requestBodyMap,
                                       String charset) throws UnsupportedEncodingException {
        if (requestBodyMap == null || requestBodyMap.isEmpty()) {
            return;
        }
        // 只有baseHttpRequestBase的类是HttpEntityEnclosingRequest的实现类才设置请求体
        // 常用的请求方法类中（HttpGetLarge、HttpDeleteLarge、HttpPut、HttpPost）“可以设置请求体”
        // 而（HttpGet、HttpDelete）“没有请求体，所以不可以设置请求体！！！”
        if (baseHttpRequestBase instanceof HttpEntityEnclosingRequest) {
            // 存储一个请求体中所有参数的集合
            List<NameValuePair> requestBodyParams = new ArrayList<>(requestBodyMap.size());
            // 将请求体map（requestBodyMap）转成NameValuePair集合（一个NameValuePair相当于requestBodyMap的一个kv键值对）
            for (Map.Entry<String, String> entry : requestBodyMap.entrySet()) {
                // 请求体的一个参数（k-v键值对结构）
                BasicNameValuePair oneRequestBodyParam =
                        new BasicNameValuePair(entry.getKey(), entry.getValue());
                requestBodyParams.add(oneRequestBodyParam);
            }
            // 将baseHttpRequestBase强制转换成HttpEntityEnclosingRequest类对象
            HttpEntityEnclosingRequest request = (HttpEntityEnclosingRequest) baseHttpRequestBase;
            // 将一个请求体中所有参数的集合（requestBodyParams）转成请求体（HttpEntity）
            HttpEntity entity = new UrlEncodedFormEntity(requestBodyParams, charset);
            // 给请求（request）设置请求体
            request.setEntity(entity);
        }


    }


    /**
     * 前提：只有baseHttpRequestBase的类是HttpEntityEnclosingRequest的实现类才能设置请求体，
     * 1：如果baseHttpRequestBase的类是HttpGetLarge、HttpDeleteLarge、HttpPut、HttpPost则会设置请求体（则这个方法会生效），
     * 2：而如果baseHttpRequestBase的类是HttpGet、HttpDelete则无法设置请求体（则>>失效<<！！！）
     *
     * @param baseHttpRequestBase http请求
     * @param requestBody         请求体
     * @param requestHeader       请求头
     */
    private static void setRequestBody(HttpRequestBase baseHttpRequestBase, Object requestBody, Header requestHeader) {

        if (requestBody == null) {
            return;
        }
        // 只有baseHttpRequestBase的类是HttpEntityEnclosingRequest的实现类才设置请求体
        // 常用的请求方法类中（HttpGetLarge、HttpDeleteLarge、HttpPut、HttpPost）“可以设置请求体”
        // 而（HttpGet、HttpDelete）“没有请求体，所以不可以设置请求体！！！”
        if (baseHttpRequestBase instanceof HttpEntityEnclosingRequest) {
            // 将baseHttpRequestBase强制转换成HttpEntityEnclosingRequest类对象
            HttpEntityEnclosingRequest request = (HttpEntityEnclosingRequest) baseHttpRequestBase;
            MediaType mediaType = MediaType.valueOf(requestHeader.getValue(RequestHeaderConstants.CONTENT_TYPE));
            // 通过mediaType创建ContentType对象
            ContentType contentType = ContentType.create(mediaType.getType(), mediaType.getCharset());
            HttpEntity entity;
            // 如果requestBody是byte数组
            if (requestBody instanceof byte[]) {
                // 生成请求体
                entity = new ByteArrayEntity((byte[]) requestBody, contentType);
            }
            // 如果requestBody不是byte数组（默认为字符串）
            else {
                // 生成请求体
                entity = new StringEntity(requestBody instanceof String ?
                        (String) requestBody : JSON.toJSONString(requestBody),
                        contentType);
            }
            // 给请求（request）设置请求体
            request.setEntity(entity);
        }
    }

    /**
     * 将默认HTTP客户端配置（defaultConfig）和指定的HTTP客户端配置（httpClientConfig）合并。
     *
     * @param baseHttpRequestBase http请求
     * @param httpClientConfig    指定的http客户端配置
     * @param defaultConfig       默认http客户端配置
     */
    private static void mergeDefaultConfig(HttpRequestBase baseHttpRequestBase, HttpClientConfig httpClientConfig, RequestConfig defaultConfig) {

        if (httpClientConfig == null) {
            return;
        }
        // 给baseHttpRequestBase添加配置
        baseHttpRequestBase.setConfig(RequestConfig.copy(defaultConfig)
                .setConnectTimeout(httpClientConfig.getConTimeOutMillis())
                .setSocketTimeout(httpClientConfig.getReadTimeOutMillis()).build());

    }

}
