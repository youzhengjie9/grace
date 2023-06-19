package com.grace.client.annotation;

import java.lang.annotation.*;

/**
 * 该注解(@EnableGraceClient) = @EnableGraceRegistry + @EnableGraceConfig
 *
 * @author youzhengjie
 * @date 2023/06/19 22:13:45
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableGraceRegistry
@EnableGraceConfig
public @interface EnableGraceClient {



}
