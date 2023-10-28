package com.grace.client.utils;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * yaml工具类
 *
 * @author youzhengjie
 * @date 2023/10/28 01:17:51
 */
public class YamlUtils {

    /**
     * 将yaml格式的内容转成Map集合，并将该Map集合的key转成以"."进行分隔的key（例如: server.port）,
     * 只有这种格式的key才能让Spring识别的到
     *
     * @param yamlContent yml内容
     */
    public static Map<String, Object> yamlContentToMap(String yamlContent){
        ByteArrayInputStream bis = null;
        Map<String, Object> yamlContentMap = null;
        try {
            // 将yaml内容转成ByteArrayInputStream
            bis = new ByteArrayInputStream(yamlContent.getBytes(StandardCharsets.UTF_8));
            // 将yaml内容转成Map<String, Object>（注意: 此时这个Map的key没有以"."分隔 ！！！）
            yamlContentMap = new Yaml().load(bis);
            // 将Map的key转成以"."进行分隔的key,例如 server.port（只有这种格式的key,Spring才能识别的到）
            return processorMap(yamlContentMap);
        } finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * yaml格式的文件转成Map集合（并将该Map集合的key转成以"."进行分隔（例如: server.port））,
     * 只有这种格式的key才能让Spring识别的到
     *
     * @param yamlFile yaml文件对象
     */
    public static Map<String, Object> yamlFileToMap(File yamlFile){
        FileInputStream fis = null;
        Map<String, Object> yamlContentMap = null;
        try {
            // 输入流
            fis = new FileInputStream(yamlFile);
            // 将yaml格式的文件转成Map<String, Object>（注意: 此时这个Map的key没有以"."分隔 ！！！）
            yamlContentMap = new Yaml().load(fis);
            // 将Map的key转成以"."进行分隔的key,例如 server.port（只有这种格式的key,Spring才能识别的到）
            return processorMap(yamlContentMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return yamlContentMap;
    }


    /**
     * 在本地目录生成一个yaml类型的文件（也就是持久化yaml文件）
     *
     * @param yamlContent   yaml格式的内容
     * @param yamlFilePath  指定yaml文件要生成在本地磁盘的哪个路径
     * @param append  是否追加写文件
     */
    public static void generateYamlFile(String yamlContent, String yamlFilePath, boolean append){
        ByteArrayInputStream bis = null;
        Map<String, Object> yamlContentMap;
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(dumperOptions);
        // yaml文件输出路径
        File yamlFile = new File(yamlFilePath);
        FileWriter fw = null;
        try{
            fw = new FileWriter(yamlFile, append);
            // 将yaml内容转成ByteArrayInputStream
            bis = new ByteArrayInputStream(yamlContent.getBytes(StandardCharsets.UTF_8));
            // 将yaml内容转成Map<String, Object>（注意: 此时这个Map的key没有以"."分隔 ！！！）
            yamlContentMap = new Yaml().load(bis);
            // 在本地目录生成一个yaml类型的文件（也就是持久化yaml文件）
            yaml.dump(yamlContentMap, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 对这个Map进行加工。将Map的key转成以"."进行分隔的key,例如 server.port（只有这种格式的key,Spring才能识别的到）
     *
     * @param yamlContentMap 被转成Map的yaml内容（此时这个Map的key没有用"."分隔）
     * @return {@link Map}<{@link String}, {@link Object}>
     */
    private static Map<String, Object> processorMap(Map<String, Object> yamlContentMap) {
        Map<String, Object> map = yamlContentMap;
        Map<String, Object> mapAfterConvert = new HashMap<>();
        map.forEach((key1, value1) -> {
            if (value1 instanceof Map) {
                mapAfterConvert.putAll(forEachYaml(mapAfterConvert, key1, (Map) value1));
            } else {
                mapAfterConvert.put(key1, value1.toString());
            }
        });
        return mapAfterConvert;
    }

    private static Map<String, Object> forEachYaml(Map<String, Object> mapAfterConvert, String key1, Map<String, Object> map) {
        map.forEach((key2, value2) -> {
            String strNew;
            if (StringUtils.isNotEmpty(key1)) {
                strNew = key1 + "." + key2;
            } else {
                strNew = key2;
            }
            if (value2 instanceof Map) {
                mapAfterConvert.putAll(forEachYaml(mapAfterConvert, strNew, (Map) value2));
            } else {
                mapAfterConvert.put(strNew, value2);
            }
        });
        return mapAfterConvert;
    }


}
 