package com.grace.common.http.param;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


/**
 * url的请求参数（例如https://www.baidu.com?ie=utf-8&tn=baidu&wd=aa，则请求参数为?ie=utf-8&tn=baidu&wd=aa）
 *
 * @author youzhengjie
 * @date 2023/08/15 00:40:34
 */
public class RequestParam {

    /**
     * 存储请求参数的map
     */
    private Map<String, Object> requestParamMap;

    /**
     * 默认编码方式
     */
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    public RequestParam() {
        requestParamMap = new LinkedHashMap<>();
    }

    /**
     * 创建新的RequestParam实例
     *
     * @return {@link RequestParam}
     */
    public static RequestParam newInstance() {
        return new RequestParam();
    }
    
    /**
     * 添加一个请求参数
     *
     * @param key   key
     * @param value value
     */
    public RequestParam addRequestParam(String key, Object value) {
        requestParamMap.put(key, value);
        return this;
    }

    /**
     * 通过请求参数的key获取其对应的value
     *
     * @param key key
     * @return {@link Object}
     */
    public Object getRequestParamValue(String key) {
        return requestParamMap.get(key);
    }
    
    /**
     * 一次性添加多个请求参数
     *
     * @param requestParamMap 请求参数map
     */
    public RequestParam addRequestParams(Map<String, String> requestParamMap) {
        // 如果requestParamMap不为空
        if (requestParamMap != null && !requestParamMap.isEmpty()) {
            for (Map.Entry<String, String> entry : requestParamMap.entrySet()) {
                addRequestParam(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }


    /**
     * 作用是: 将requestParamMap存储的数据转换成请求参数字符串（如果requestParamMap为空则这个方法会返回null）
     * <p>
     * 例如: requestParamMap=[id: 100, name: 'zhangsan']
     * <p>
     * 调用了此方法后会变成:  ?id=100&name=zhangsan
     *
     * @return {@link String}
     */
    public String toRequestParamString() {
        // 如果requestParamMap不为空
        if(requestParamMap != null && !requestParamMap.isEmpty()){
            StringBuilder requestParamStringBuilder = new StringBuilder();
            requestParamStringBuilder.append("?");
            Set<Map.Entry<String, Object>> entrySet = requestParamMap.entrySet();
            int i = entrySet.size();
            for (Map.Entry<String, Object> entry : entrySet) {
                try {
                    if (null != entry.getValue()) {
                        requestParamStringBuilder.append(entry.getKey()).append('=')
                                .append(URLEncoder.encode(String.valueOf(entry.getValue()), DEFAULT_ENCODING));
                        if (i > 1) {
                            requestParamStringBuilder.append('&');
                        }
                    }
                    i--;
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }

            return requestParamStringBuilder.toString();
        }
        return null;
    }
    
    public void clear() {
        requestParamMap.clear();
    }

    public boolean isEmpty() {
        return requestParamMap.isEmpty();
    }
    
}
