package com.grace.core.exception;


/**
 * 解析token失败异常
 *
 * @author youzhengjie
 * @date 2023/06/15 22:53:58
 */
public class ParseTokenException extends RuntimeException{

    private static final String MSG="解析token失败,请检查token是否正确";

    public ParseTokenException() {
        super(MSG);
    }

    public ParseTokenException(String message) {
        super(message);
    }
}
