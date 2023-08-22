package com.grace.common.enums;

import com.grace.common.constant.RequestMethodConstants;
import org.apache.http.client.methods.*;

import java.net.URI;

/**
 * 请求方法枚举类（根据不同的请求方法来选择不同的创建请求实现方式）
 *
 * @author youzhengjie
 * @date 2023/08/18 00:35:24
 */
public enum RequestMethod {

    GET(RequestMethodConstants.GET) {
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpGet(uri);
        }
    },
    GET_LARGE(RequestMethodConstants.GET_LARGE){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpGetLarge(uri);
        }
    },

    POST(RequestMethodConstants.POST){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpPost(uri);
        }
    },

    PUT(RequestMethodConstants.PUT){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpPut(uri);
        }
    },

    DELETE(RequestMethodConstants.DELETE){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpDelete(uri);
        }
    },
    DELETE_LARGE(RequestMethodConstants.DELETE_LARGE){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpDeleteLarge(uri);
        }
    },
    HEAD(RequestMethodConstants.HEAD){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpHead(uri);
        }
    },
    PATCH(RequestMethodConstants.PATCH){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpPatch(uri);
        }
    },
    OPTIONS(RequestMethodConstants.OPTIONS){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpOptions(uri);
        }
    },
    TRACE(RequestMethodConstants.TRACE){
        @Override
        public HttpRequestBase createRequest(String uri) {
            return new HttpTrace(uri);
        }
    }

    ;

    private String methodName;


    RequestMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    /**
     * 根据url来创建请求（根据不同的请求方法来选择不同的创建请求实现方式）
     *
     * @param uri uri
     * @return {@link HttpRequestBase}
     */
    public HttpRequestBase createRequest(String uri) {
        throw new UnsupportedOperationException();
    }

    /**
     * get Large implemented.
     * <p>
     * Mainly used for GET request parameters are relatively large, can not be placed on the URL, so it needs to be
     * placed in the body.
     * </p>
     */
    public static class HttpGetLarge extends HttpEntityEnclosingRequestBase {

        public static final String METHOD_NAME = "GET";

        public HttpGetLarge(String uri) {
            super();
            setURI(URI.create(uri));
        }

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }

    /**
     * delete Large implemented.
     * <p>
     * Mainly used for DELETE request parameters are relatively large, can not be placed on the URL, so it needs to be
     * placed in the body.
     * </p>
     */
    public static class HttpDeleteLarge extends HttpEntityEnclosingRequestBase {

        public static final String METHOD_NAME = "DELETE";

        public HttpDeleteLarge(String uri) {
            super();
            setURI(URI.create(uri));
        }

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }
    }

}
