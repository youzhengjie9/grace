package com.grace.client.utils;
 
import org.springframework.web.multipart.MultipartFile;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
 
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


/**
 * yaml工具类
 *
 * @author youzhengjie
 * @date 2023/10/28 01:17:51
 */
public class YamlUtils {
 
    /**
     * yaml格式的文件转成Map集合
     *
     * @param yamlFile yaml文件对象
     */
    public static Map<String, Object> yamlFileToMap(File yamlFile){
        FileInputStream fis = null;
        Map<String, Object> map = null;
        try {
            // 输入流
            fis = new FileInputStream(yamlFile);
            // 调用load方法 转到map集合中
            map = new Yaml().load(fis);
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
        return map;
    }

    /**
     * yaml格式的内容转成Map集合
     *
     * @param yamlContent yml内容
     */
    public static Map<String, Object> yamlContentToMap(String yamlContent){
        ByteArrayInputStream bis = null;
        Map<String, Object> map = null;
        try {
            // 输入流
            bis = new ByteArrayInputStream(yamlContent.getBytes(StandardCharsets.UTF_8));
            // 调用load方法 转到map集合中
            map = new Yaml().load(bis);
        } finally {
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
 
    public static Map<String, Object> yamlToMap(MultipartFile file){
        Map<String, Object> map = null;
        try {
            //将MultipartFile中输入流直接放入load()方法中，调用load方法 转到map集合中
            map = new Yaml().load(file.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }
 
    /**
     * map转为yaml文件
     *
     * @param map   需要转换map数据
     * @param path  输出路径
     * @param flag  是否追加写文件
     */
    public static void mapToYaml(Map<String, Object> map, String path, boolean flag){
        //将数据写入yaml文件
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml wyaml = new Yaml(dumperOptions);
        //输出文件路径
        File dumpfile = new File(path);
        FileWriter fw = null;
        try{
            //输出流
            fw = new FileWriter(dumpfile, flag);
            //调佣dump方法 转到yaml文件中
            wyaml.dump(map, fw);
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
        }
    }
}
 