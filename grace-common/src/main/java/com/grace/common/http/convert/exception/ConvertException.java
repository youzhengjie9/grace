package com.grace.common.http.convert.exception;

/**
 * 转换异常
 *
 * @author youzhengjie
 * @date 2023/08/22 00:56:15
 */
public class ConvertException extends RuntimeException{

    public ConvertException() {
    }

    public ConvertException(String message) {
        super(message);
    }

}
