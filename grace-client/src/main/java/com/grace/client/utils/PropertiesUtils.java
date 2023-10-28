package com.grace.client.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

    /**
     * 将properties类型的内容转成Map类型的对象
     *
     * @param propertiesContent properties类型的内容
     * @return {@link Map}<{@link String},{@link Object}>
     */
    public static Map<String,Object> propertiesContentToMap(String propertiesContent){
        Properties properties = new Properties();
        try {
            // 加载properties格式的内容
            properties.load(new ByteArrayInputStream(propertiesContent.getBytes(StandardCharsets.UTF_8)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new HashMap<String, Object>((Map) properties);
    }

}
