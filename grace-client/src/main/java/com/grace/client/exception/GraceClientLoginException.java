package com.grace.client.exception;

/**
 * grace客户端登录失败异常
 *
 * @author youzhengjie
 * @date 2023/11/01 13:11:18
 */
public class GraceClientLoginException extends RuntimeException {

    public GraceClientLoginException() {
        super("grace客户端登录失败,请检查帐号密码是否正确");
    }

    public GraceClientLoginException(String message) {
        super(message);
    }
}
