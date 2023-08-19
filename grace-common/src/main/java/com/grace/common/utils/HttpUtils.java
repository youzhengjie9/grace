package com.grace.common.utils;

import com.grace.common.http.param.RequestParam;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * http工具类
 *
 * @author youzhengjie
 * @date 2023/08/15 23:43:35
 */
public class HttpUtils {

    public static URI buildUri(String url, RequestParam requestParam) throws URISyntaxException {
        if (requestParam != null && !requestParam.isEmpty()) {
            url = url + requestParam.toRequestParamString();
        }
        return new URI(url);
    }

}
