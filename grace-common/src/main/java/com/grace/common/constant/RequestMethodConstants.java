package com.grace.common.constant;

/**
 * 请求方法常量
 *
 * @author youzhengjie
 * @date 2023/08/20 21:27:55
 */
public class RequestMethodConstants {
    
    public static final String GET = "GET";
    
    /**
     * this is only use in nacos, Custom request type, essentially a GET request, Mainly used for GET request parameters
     * are relatively large,can not be placed on the URL, so it needs to be placed in the body.
     */
    public static final String GET_LARGE = "GET-LARGE";
    
    public static final String HEAD = "HEAD";
    
    public static final String POST = "POST";
    
    public static final String PUT = "PUT";
    
    public static final String PATCH = "PATCH";
    
    public static final String DELETE = "DELETE";
    
    /**
     * this is only use in nacos, Custom request type, essentially a DELETE request, Mainly used for DELETE request
     * parameters are relatively large, can not be placed on the URL, so it needs to be placed in the body.
     */
    public static final String DELETE_LARGE = "DELETE_LARGE";
    
    public static final String OPTIONS = "OPTIONS";
    
    public static final String TRACE = "TRACE";
}
