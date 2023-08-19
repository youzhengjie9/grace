package com.grace.common.http.response;

import java.io.IOException;
import java.io.InputStream;


/**
 * http客户端响应
 *
 * @author youzhengjie
 * @date 2023/08/17 23:26:08
 */
public interface HttpClientResponse{
//
//    /**
//     * Return the headers of this message.
//     *
//     * @return a corresponding HttpHeaders object (never {@code null})
//     */
//    Header getHeaders();
    
    /**
     * 返回消息体作为输入流
     *
     * @return String response body
     * @throws IOException IOException
     */
    InputStream getBody() throws IOException;
    
    /**
     * 返回HTTP状态码
     *
     * @return the HTTP status as an integer
     * @throws IOException IOException
     */
    int getStatusCode() throws IOException;
    
    /**
     * 返回响应的HTTP状态文本.
     *
     * @return the HTTP status text
     * @throws IOException IOException
     */
    String getStatusText() throws IOException;
    

}
