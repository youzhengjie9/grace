package com.grace.client.http.convert;

import com.alibaba.fastjson2.JSON;
import com.grace.client.http.convert.exception.ConvertException;
import com.grace.client.http.RestResult;
import com.grace.client.http.param.RequestHeader;
import com.grace.client.http.param.ResponseHeader;
import com.grace.client.http.response.HttpClientResponse;
import com.grace.common.utils.Result;

import java.io.IOException;

/**
 * HttpClientResponse转换成RestResult的转换器。
 * 作用: 将HttpClientResponse对象转换成RestResult对象
 *
 * @param <T> 转换成的RestResult的泛型。
 * @author youzhengjie
 * @date 2023/08/21 18:28:00
 */
public class HttpClientResponseRestResultConverter<T> implements Converter<RestResult<T>> {

    @Override
    public RestResult<T> convert(Object source) throws IOException {
        // 如果source对象不是HttpClientResponse类的对象,说明转换失败
        if(source instanceof HttpClientResponse){
            try {
                // 将source对象强制转换成HttpClientResponse类型
                HttpClientResponse httpClientResponse = (HttpClientResponse) source;
                // 获取响应头
                final ResponseHeader responseHeader = httpClientResponse.getResponseHeader();
                // 获取响应结果JSON
                String resultJson = httpClientResponse.getResult();
                // 将响应结果JSON转成Result<T>对象
                Result<T> result = JSON.parseObject(resultJson, Result.class);
                // 将Result<T>转换成RestResult<T>用来供GraceRestTemplate使用
                RestResult<T> restResult = new RestResult<>();
                restResult.setResult(result);
                restResult.setResponseHeader(responseHeader);
                return restResult;
            }catch (Exception e){
                throw new ConvertException("转换失败");
            }
        }else {
            throw new ConvertException("传入的source对象不是HttpClientResponse的类或其子类");
        }

    }
}
