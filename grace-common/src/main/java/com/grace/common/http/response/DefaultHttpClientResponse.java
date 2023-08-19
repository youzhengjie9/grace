package com.grace.common.http.response;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.io.InputStream;

/**
 * 默认的http客户端响应
 *
 * @author youzhengjie
 * @date 2023/08/17 23:24:43
 */
public class DefaultHttpClientResponse implements HttpClientResponse {

    private HttpResponse response;

    public DefaultHttpClientResponse(HttpResponse response) {
        this.response = response;
    }

    @Override
    public InputStream getBody() throws IOException {
        return response.getEntity().getContent();
    }

    @Override
    public int getStatusCode() {
        return this.response.getStatusLine().getStatusCode();
    }

    @Override
    public String getStatusText() {
        return this.response.getStatusLine().getReasonPhrase();
    }


}
