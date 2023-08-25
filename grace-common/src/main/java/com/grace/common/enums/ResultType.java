package com.grace.common.enums;

/**
 * 响应结果类型枚举类
 *
 * @author youzhengjie
 * @date 2023-06-15 22:49:43
 */
public enum ResultType {

    /**
     * 通用状态
     */
    SUCCESS(200,"请求接口成功"),
    ERROR(500,"请求接口异常"),

    UNAUTHORIZED(401,"认证失败"),
    FORBIDDEN(403,"权限不足,拒绝访问"),

    /**
     * 用户状态
     */
    NOT_LOGIN(401,"用户未登录，请重新登录"),
    NOT_PERMISSION(403,"用户没有权限"),

    /**
     * 登录状态
     */
    LOGIN_SUCCESS(600,"用户登录成功！"),
    USERNAME_PASSWORD_ERROR(601,"用户名或者密码不正确"),
    CODE_ERROR(602,"验证码错误"),
    ACCOUNT_NOT_EXIST(603,"账号不存在"),
    ACCOUNT_LOCKED(604,"账号被锁定"),
    ACCOUNT_CREDENTIAL_EXPIRED(605,"用户凭证已失效"),
    ACCOUNT_DISABLE(606,"账号已被禁用"),

    /**
     * 用户退出状态
     */
    LOGOUT_SUCCESS(800,"退出登录成功"),
    LOGOUT_ERROR(801,"退出登录失败"),

    ;


    private int code;
    private String message;

    ResultType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
