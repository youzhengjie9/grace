package com.grace.client.utils;

import com.alibaba.fastjson2.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {


    /**
     * Json格式的内容转成Map
     * <p>
     * 例如: <p>
     * {
     *   "project":{
     *     "version": "v1.0.0"
     *   }
     * }
     * <p>
     * 转成:
     * Map的key=project.version,Map的value=v1.0.0
     *
     */
    public static Map<String, Object> jsonContentToMap(String jsonContent) {
        // 最终返回的结果Map
        Map<String, Object> resultMap = new HashMap<>();
        // 将json内容字符串转成Map
        Map<String, Object> jsonContentMap = JSONObject.parseObject(jsonContent, Map.class);
        // 对jsonContentMap进行加工（将Map的key以"."进行分隔,否则Spring无法识别）
        processorMap("", resultMap, jsonContentMap);
        return resultMap;
    }

    /**
     * 对这个Map进行加工（将Map的key以"."进行分隔,否则Spring无法识别）
     */
    private static void processorMap(String prefix, Map<String, Object> resultMap, Map<String, Object> jsonContentMap) {
        if (prefix.length() > 0) {
            prefix += ".";
        }
        for (Map.Entry<String, Object> entrySet : jsonContentMap.entrySet()) {
            if (entrySet.getValue() instanceof Map) {
                processorMap(prefix + entrySet.getKey(), resultMap, (Map<String, Object>) entrySet.getValue());
            } else {
                resultMap.put(prefix + entrySet.getKey(), entrySet.getValue());
            }
        }
    }

}
