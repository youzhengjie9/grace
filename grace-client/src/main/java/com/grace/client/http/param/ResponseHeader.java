package com.grace.client.http.param;

import com.grace.common.constant.Constants;
import com.grace.common.constant.RequestHeaderConstants;
import com.grace.common.utils.MapUtil;
import com.grace.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应头
 *
 * @author youzhengjie
 * @date 2023-10-24 00:42:03
 */
public class ResponseHeader {

    /**
     * 存储响应头内容
     */
    private final Map<String, String> responseHeaderMap = new HashMap<>();

    private static final String DEFAULT_CHARSET = "UTF-8";

    private ResponseHeader() {
        // 添加默认的响应头
        addResponseHeader("Content-Type", MediaType.APPLICATION_JSON);
        addResponseHeader("Accept-Charset", DEFAULT_CHARSET);
    }

    /**
     * 创建一个响应头
     *
     * @return {@link ResponseHeader}
     */
    public static ResponseHeader newInstance() {
        return new ResponseHeader();
    }

    /**
     * 添加响应头
     *
     * @param key the key
     * @param value the value
     * @return requestHeaderMap
     */
    public ResponseHeader addResponseHeader(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            responseHeaderMap.put(key, value);
        }
        return this;
    }

    public String getValue(String key) {
        return responseHeaderMap.get(key);
    }
    
    public Map<String, String> getResponseHeaderMap() {
        return responseHeaderMap;
    }
    
    /**
     * 添加多个响应头
     *
     * @param responseHeaderMap 响应头Map
     */
    public void addAll(Map<String, String> responseHeaderMap) {
        if (MapUtil.isNotEmpty(responseHeaderMap)) {
            for (Map.Entry<String, String> entry : responseHeaderMap.entrySet()) {
                addResponseHeader(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 获取响应头中的字符编码
     *
     * @return {@link String}
     */
    public String getCharset() {
        String acceptCharset = getValue("Accept-Charset");
        if (acceptCharset == null) {
            String contentType = getValue("Content-Type");
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
        return "ResponseHeader{" +
                "responseHeaderMap=" + responseHeaderMap +
                '}';
    }
}

