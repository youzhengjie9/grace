package com.grace.security.annotation;

import java.lang.annotation.*;


/**
 * 作用: 该接口的方法允许所有人访问,服务调用不需要鉴权（只要加了这个@PermitAll注解就会生效）
 *
 * @author youzhengjie
 * @date 2023-11-05 00:31:45
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermitAll {

}
