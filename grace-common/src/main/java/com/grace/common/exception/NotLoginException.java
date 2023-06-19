package com.grace.common.exception;


/**
 * token解析成功，但是redis中没有该用户的LoginUser对象。
 * 用户未登录异常
 *
 * @author youzhengjie
 * @date 2023/06/15 22:53:32
 */
public class NotLoginException extends RuntimeException{

    private static final String MSG="用户未登录,请重新登录！";

    public NotLoginException() {
        super(MSG);
    }

    public NotLoginException(String message) {
        super(message);
    }
}
