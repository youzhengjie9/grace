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
    
    public static final String APPLICATION_ATOM_XML = "application/atom+xml";

    // POST表单（form）提交方式
    public static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded;charset=UTF-8";
    
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    
    public static final String APPLICATION_SVG_XML = "application/svg+xml";
    
    public static final String APPLICATION_XHTML_XML = "application/xhtml+xml";
    
    public static final String APPLICATION_XML = "application/xml;charset=UTF-8";
    
    public static final String APPLICATION_JSON = "application/json;charset=UTF-8";
    
    public static final String MULTIPART_FORM_DATA = "multipart/form-data;charset=UTF-8";
    
    public static final String TEXT_HTML = "text/html;charset=UTF-8";
    
    public static final String TEXT_PLAIN = "text/plain;charset=UTF-8";
    
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
