package com.grace.order.controller;

import com.alibaba.fastjson2.JSONObject;
import com.grace.order.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试注册中心动态修改配置
 *
 * @RefreshScope注解的作用: <p>
 * 所有加了@RefreshScope注解的类,当我们调用contextRefresher的refresh方法时,GracePropertySourceLocator类会被重新加载,
 * 从而重新去配置中心获取最新的配置,并加载到propertySource中, 同时该类会被重新加载（@value的值也会被重新加载）,
 * 这样就实现了动态修改配置的功能。
 *
 * @author youzhengjie
 * @date 2023/10/26 21:35:37
 */

@RefreshScope
@RestController
public class TestController {

    @Autowired
    private Product product;

    @Value("${project.version}")
    private String version;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/getProduct")
    public String getProduct(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("application","grace-order6002");
        jsonObject.put("id",product.getId());
        jsonObject.put("productName",product.getProductName());
        jsonObject.put("price",product.getPrice());
        jsonObject.put("version",version);
        return jsonObject.toJSONString();
    }

    @GetMapping("/modifyProduct/{id}")
    public String modifyProduct(@PathVariable("id") String id){
        // 获取可进行配置的environment对象
        ConfigurableEnvironment configurableEnvironment =
                (ConfigurableEnvironment) applicationContext.getEnvironment();



        return "ok";
    }


//    @Value("${user.username}")
//    private String username;
//
//    @Value("${user.age}")
//    private String age;
//
//    @GetMapping("/getUserInfo")
//    public String getUserInfo(){
//        System.out.println(username);
//        System.out.println(age);
//        return "ok";
//    }

}
