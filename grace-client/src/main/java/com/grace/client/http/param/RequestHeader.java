package com.grace.client.http.param;

import com.grace.common.constant.Constants;
import com.grace.common.constant.RequestHeaderConstants;
import com.grace.common.utils.MapUtil;
import com.grace.common.utils.StringUtils;

import java.util.*;

/**
 * 请求头
 *
 * @author youzhengjie
 * @date 2023/08/19 01:06:14
 */
public class RequestHeader {

    /**
     * 存储请求头内容
     */
    private final Map<String, String> requestHeaderMap = new HashMap<>();
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    
    private RequestHeader() {
        // 添加默认的请求头
        addRequestHeader(RequestHeaderConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        addRequestHeader(RequestHeaderConstants.ACCEPT_CHARSET, DEFAULT_CHARSET);
    }

    /**
     * 创建一个请求头
     *
     * @return {@link RequestHeader}
     */
    public static RequestHeader newInstance() {
        return new RequestHeader();
    }

    /**
     * 添加请求头
     *
     * @param key the key
     * @param value the value
     * @return requestHeaderMap
     */
    public RequestHeader addRequestHeader(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            requestHeaderMap.put(key, value);
        }
        return this;
    }

    public RequestHeader setContentType(String contentType) {
        if (contentType == null) {
            contentType = MediaType.APPLICATION_JSON;
        }
        return addRequestHeader(RequestHeaderConstants.CONTENT_TYPE, contentType);
    }

    public String getValue(String key) {
        return requestHeaderMap.get(key);
    }
    
    public Map<String, String> getRequestHeaderMap() {
        return requestHeaderMap;
    }
    
    /**
     * 添加多个请求头
     *
     * @param requestHeaderMap 请求头Map
     */
    public void addAll(Map<String, String> requestHeaderMap) {
        if (MapUtil.isNotEmpty(requestHeaderMap)) {
            for (Map.Entry<String, String> entry : requestHeaderMap.entrySet()) {
                addRequestHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 获取请求头中的字符编码
     *
     * @return {@link String}
     */
    public String getCharset() {
        String acceptCharset = getValue(RequestHeaderConstants.ACCEPT_CHARSET);
        if (acceptCharset == null) {
            String contentType = getValue(RequestHeaderConstants.CONTENT_TYPE);
            acceptCharset = StringUtils.isNotBlank(contentType) ? analysisCharset(contentType) : Constants.ENCODE;
        }
        return acceptCharset;
    }

    /**
     * 从contentType中获取字符编码
     *
     * @param contentType contentType
     * @return {@link String}
     */
    private String analysisCharset(String contentType) {
        String[] values = contentType.split(";");
        String charset = Constants.ENCODE;
        for (String value : values) {
            if (value.startsWith("charset=")) {
                charset = value.substring("charset=".length());
            }
        }
        return charset;
    }

    @Override
    public String toString() {
        return "RequestHeader{" +
                "requestHeaderMap=" + requestHeaderMap +
                '}';
    }
}

