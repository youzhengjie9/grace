package com.grace.common.http.param;

import com.grace.common.constant.Constants;
import com.grace.common.constant.RequestHeaderConstants;
import com.grace.common.utils.MapUtil;
import com.grace.common.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * http请求头
 *
 * @author youzhengjie
 * @date 2023/08/19 01:06:14
 */
public class RequestHeader {
    
    public static final RequestHeader EMPTY = RequestHeader.newInstance();
    
    private final Map<String, String> header;
    
    private final Map<String, List<String>> originalResponseHeader;
    
    private static final String DEFAULT_CHARSET = "UTF-8";
    
    private static final String DEFAULT_ENCODING = "gzip";
    
    private RequestHeader() {
        header = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        originalResponseHeader = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        addParam(RequestHeaderConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        addParam(RequestHeaderConstants.ACCEPT_CHARSET, DEFAULT_CHARSET);
        //addParam(HttpHeaderConsts.ACCEPT_ENCODING, DEFAULT_ENCODING);
    }
    
    public static RequestHeader newInstance() {
        return new RequestHeader();
    }

    /**
     * Add the key and value to the header.
     *
     * @param key the key
     * @param value the value
     * @return header
     */
    public RequestHeader addParam(String key, String value) {
        if (StringUtils.isNotEmpty(key)) {
            header.put(key, value);
        }
        return this;
    }
    
    public RequestHeader setContentType(String contentType) {
        if (contentType == null) {
            contentType = MediaType.APPLICATION_JSON;
        }
        return addParam(RequestHeaderConstants.CONTENT_TYPE, contentType);
    }
    
    public RequestHeader build() {
        return this;
    }
    
    public String getValue(String key) {
        return header.get(key);
    }
    
    public Map<String, String> getHeader() {
        return header;
    }
    
    public Iterator<Map.Entry<String, String>> iterator() {
        return header.entrySet().iterator();
    }
    
    /**
     * Transfer to KV part list. The odd index is key and the even index is value.
     *
     * @return KV string list
     */
    public List<String> toList() {
        List<String> list = new ArrayList<>(header.size() * 2);
        Iterator<Map.Entry<String, String>> iterator = iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            list.add(entry.getKey());
            list.add(entry.getValue());
        }
        return list;
    }
    
    /**
     * Add all KV list to header. The odd index is key and the even index is value.
     *
     * @param list KV list
     * @return header
     */
    public RequestHeader addAll(List<String> list) {
        if ((list.size() & 1) != 0) {
            throw new IllegalArgumentException("list size must be a multiple of 2");
        }
        for (int i = 0; i < list.size(); ) {
            String key = list.get(i++);
            if (StringUtils.isNotEmpty(key)) {
                header.put(key, list.get(i++));
            }
        }
        return this;
    }
    
    /**
     * Add all parameters to header.
     *
     * @param params parameters
     */
    public void addAll(Map<String, String> params) {
        if (MapUtil.isNotEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                addParam(entry.getKey(), entry.getValue());
            }
        }
    }
    
    /**
     * set original format response header.
     *
     * <p>Currently only corresponds to the response header of JDK.
     *
     * @param key original response header key
     * @param values original response header values
     */
    public void addOriginalResponseHeader(String key, List<String> values) {
        if (StringUtils.isNotEmpty(key)) {
            this.originalResponseHeader.put(key, values);
            addParam(key, values.get(0));
        }
    }
    
    /**
     * get original format response header.
     *
     * <p>Currently only corresponds to the response header of JDK.
     *
     * @return Map original response header
     */
    public Map<String, List<String>> getOriginalResponseHeader() {
        return this.originalResponseHeader;
    }
    
    public String getCharset() {
        String acceptCharset = getValue(RequestHeaderConstants.ACCEPT_CHARSET);
        if (acceptCharset == null) {
            String contentType = getValue(RequestHeaderConstants.CONTENT_TYPE);
            acceptCharset = StringUtils.isNotBlank(contentType) ? analysisCharset(contentType) : Constants.ENCODE;
        }
        return acceptCharset;
    }
    
    private String analysisCharset(String contentType) {
        String[] values = contentType.split(";");
        String charset = Constants.ENCODE;
        if (values.length == 0) {
            return charset;
        }
        for (String value : values) {
            if (value.startsWith("charset=")) {
                charset = value.substring("charset=".length());
            }
        }
        return charset;
    }
    
    public void clear() {
        header.clear();
        originalResponseHeader.clear();
    }
    
    @Override
    public String toString() {
        return "Header{" + "headerToMap=" + header + '}';
    }
}

