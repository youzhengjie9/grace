package com.grace.common.exception;


/**
 * 令牌过期异常
 *
 * @author youzhengjie
 * @date 2023/06/15 22:54:17
 */
public class TokenExpiredException extends RuntimeException {

    private static final String MSG="Token已过期";

    public TokenExpiredException() {
        super(MSG);
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}
