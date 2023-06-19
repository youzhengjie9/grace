package com.grace.client.annotation;

import java.lang.annotation.*;

/**
 * 开启grace配置中心功能
 *
 * @author youzhengjie
 * @date 2023/06/19 20:40:26
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableGraceConfig {




}
