package com.grace.core.exception;


/**
 * 用户名或者密码不正确
 *
 * @author youzhengjie
 * @date 2023/06/15 22:54:51
 */
public class UserNameOrPassWordException extends RuntimeException {

    private static final String MSG="用户名或者密码不正确,请重新输入";

    public UserNameOrPassWordException() {
        super(MSG);
    }

    public UserNameOrPassWordException(String message) {
        super(message);
    }

}
