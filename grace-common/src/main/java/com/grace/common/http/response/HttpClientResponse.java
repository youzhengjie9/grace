package com.grace.common.http.response;

import com.grace.common.http.param.Header;

import java.io.IOException;


/**
 * http客户端响应
 *
 * @author youzhengjie
 * @date 2023/08/17 23:26:08
 */
public interface HttpClientResponse{

    /**
     * 获取响应头
     *
     * @return {@link Header}
     */
    Header getResponseHeader();
    
    /**
     * 获取响应结果的JSON字符串（返回结果的格式为 {"code":"200","msg":"成功","data":{"name":"张三","age":23}} ）
     *
     * @return 响应结果
     * @throws IOException IOException
     */
    String getResult() throws IOException;
    
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
