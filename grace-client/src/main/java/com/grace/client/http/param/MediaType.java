package com.grace.client.http.param;

import com.grace.common.constant.Constants;
import com.grace.common.utils.StringUtils;

/**
 * 媒体类型。决定浏览器将以什么形式、什么编码对资源进行解析
 *
 * @author youzhengjie
 * @date 2023/08/19 00:46:50
 */
public final class MediaType {

    /**
     * JSON类型
     * <p>
     * 传输数据要求:
     * 1: 需要后端（接收方）使用@RequestBody进行接收
     * 2: 客户端（发送方）将Map类型的请求体转成JSON再进行发送
     */
    public static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    
    private MediaType(String type, String charset) {
        this.type = type;
        this.charset = charset;
    }
    
    /**
     * content type.
     */
    private final String type;
    
    /**
     * content type charset.
     */
    private final String charset;
    
    /**
     * Parse the given String contentType into a {@code MediaType} object.
     *
     * @param contentType mediaType
     * @return MediaType
     */
    public static MediaType valueOf(String contentType) {
        if (StringUtils.isEmpty(contentType)) {
            throw new IllegalArgumentException("MediaType must not be empty");
        }
        String[] values = contentType.split(";");
        String charset = Constants.ENCODE;
        for (String value : values) {
            if (value.startsWith("charset=")) {
                charset = value.substring("charset=".length());
            }
        }
        return new MediaType(values[0], charset);
    }
    
    /**
     * Use the given contentType and charset to assemble into a {@code MediaType} object.
     *
     * @param contentType contentType
     * @param charset charset
     * @return MediaType
     */
    public static MediaType valueOf(String contentType, String charset) {
        if (StringUtils.isEmpty(contentType)) {
            throw new IllegalArgumentException("MediaType must not be empty");
        }
        String[] values = contentType.split(";");
        return new MediaType(values[0], StringUtils.isEmpty(charset) ? Constants.ENCODE : charset);
    }
    
    public String getType() {
        return type;
    }
    
    public String getCharset() {
        return charset;
    }
    
    @Override
    public String toString() {
        return type + ";charset=" + charset;
    }
}
