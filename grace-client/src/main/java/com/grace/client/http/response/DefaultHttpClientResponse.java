package com.grace.client.http.response;

import com.grace.client.http.param.ResponseHeader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * 默认的http客户端响应
 *
 * @author youzhengjie
 * @date 2023/08/17 23:24:43
 */
public class DefaultHttpClientResponse implements HttpClientResponse {

    private static final int DEFAULT_BUFFER_SIZE = 4096;

    /**
     * apache的httpclient响应结果
     */
    private HttpResponse response;

    /**
     * 响应头
     */
    private ResponseHeader responseHeader;

    public DefaultHttpClientResponse(HttpResponse response) {
        this.response = response;
    }

    @Override
    public ResponseHeader getResponseHeader() {
        if (this.responseHeader == null) {
            this.responseHeader = ResponseHeader.newInstance();
            org.apache.http.Header[] allHeaders = response.getAllHeaders();
            for (org.apache.http.Header header : allHeaders) {
                // 添加响应头
                this.responseHeader.addResponseHeader(header.getName(), header.getValue());
            }
        }
        return this.responseHeader;
    }

    @Override
    public String getResult() throws IOException {
        return resultToString(response.getEntity());
    }

    @Override
    public int getStatusCode() {
        return this.response.getStatusLine().getStatusCode();
    }

    @Override
    public String getStatusText() {
        return this.response.getStatusLine().getReasonPhrase();
    }


    /**
     * 将响应结果（org.apache.http.HttpResponse）转成字符串
     * <p>
     * 返回结果的格式为 {"code":"200","msg":"成功","data":{"name":"张三","age":23}}
     * @param entity 实体
     * @return {@link String}
     * @throws IOException    ioexception
     * @throws ParseException 解析异常
     */
    private static String resultToString(final HttpEntity entity) throws IOException, ParseException {
        Args.notNull(entity, "Entity");
        ContentType contentType = ContentType.get(entity);
        // 获取响应结果的流
        final InputStream inStream = entity.getContent();
        if (inStream == null) {
            return null;
        }
        try {
            Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
                    "HTTP entity too large to be buffered in memory");
            int capacity = (int)entity.getContentLength();
            if (capacity < 0) {
                capacity = DEFAULT_BUFFER_SIZE;
            }
            Charset charset = null;
            if (contentType != null) {
                charset = contentType.getCharset();
                if (charset == null) {
                    final ContentType defaultContentType = ContentType.getByMimeType(contentType.getMimeType());
                    charset = defaultContentType != null ? defaultContentType.getCharset() : null;
                }
            }
            if (charset == null) {
                charset = HTTP.DEF_CONTENT_CHARSET;
            }
            final Reader reader = new InputStreamReader(inStream, charset);
            final CharArrayBuffer buffer = new CharArrayBuffer(capacity);
            final char[] tmp = new char[1024];
            int l;
            while((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toString();
        } finally {
            inStream.close();
        }

    }


}
