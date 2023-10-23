package com.grace.common.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONValidator;
import com.alibaba.fastjson2.JSONWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * JSON工具类
 *
 * @author youzhengjie
 * @date 2023/10/11 23:17:01
 */
public class JsonUtils {

//    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 判断字符串是否为JSON格式的字符串（空字符串""也属于JSON格式）
     *
     * @param str 字符串
     * @return {@link Boolean}
     */
    public static Boolean isJsonString(String str){
        try {
            // 如果不是json字符串则会抛出异常
            JSON.parseObject(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将json字符串转成Map
     *
     * @param jsonStr json字符串
     */
    public static Map<Object,Object> jsonStr2Map(String jsonStr){
        // 如果jsonStr不是json格式
        if(!isJsonString(jsonStr)){
            return null;
        }
        // jsonStr是json格式
        return JSON.parseObject(jsonStr, Map.class);
    }

    /**
     * map转成被格式化后的json字符串
     * <p>
     * 格式化后的json字符串如下:
     * <p>
     *
     * {
     * 	"gray":false,
     * 	"preserved.register.source":"SPRING_CLOUD"
     * }
     *
     * @param map map
     * @return {@link String}
     */
    public static String map2FormatedJsonStr(Map<Object,Object> map){

        return JSON.toJSONString(map, JSONWriter.Feature.PrettyFormat);
    }

    public static void main(String[] args) {

//        String str = "{'preserved.register.source': 'SPRING_CLOUD','gray': false}";
//        // 判断字符串是否为JSON格式的字符串
//        Boolean isJsonString = isJsonString(str);
//        System.out.println("=======isJsonString========");
//        System.out.println(isJsonString);
//        System.out.println("===============");
//        System.out.println("=======jsonStr2Map========");
//        Map<Object, Object> map = jsonStr2Map(str);
//        map.forEach((k,v) -> {
//            System.out.println(k+"<==>"+v);
//        });
//        System.out.println("===============");
//        System.out.println("=======map2FormatedJsonStr========");
//        String jsonStr = map2FormatedJsonStr(map);
//        System.out.println(jsonStr);
//        System.out.println("===============");

    }


}
