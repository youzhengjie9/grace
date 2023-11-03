package com.grace.common.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Mybatis-Plus配置类
 *
 * @author youzhengjie
 * @date 2023/06/16 00:13:51
 */
@Configuration
@EnableTransactionManagement//开启事务管理器
public class MybatisPlusConfig {

    /**
     * mybatis-plus插件都要添加到这个mybatis-plus拦截器上面去。
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        //配置分页插件PaginationInnerInterceptor，指定数据库类型为MYSQL
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));

        return mybatisPlusInterceptor;
    }

}
