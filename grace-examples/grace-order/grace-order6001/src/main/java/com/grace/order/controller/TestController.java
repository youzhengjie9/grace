package com.grace.order.controller;

import com.alibaba.fastjson2.JSONObject;
import com.grace.order.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 测试注册中心动态修改配置
 *
 * @author youzhengjie
 * @date 2023/10/26 21:35:37
 */
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
