package com.grace.client.config.factory;

import com.grace.client.config.ConfigService;
import com.grace.client.registry.RegistryService;

import java.lang.reflect.Constructor;
import java.util.Properties;


/**
 * 用于创建ConfigService对象
 *
 * @author youzhengjie
 * @date 2023/10/27 11:11:54
 */
public class ConfigServiceFactory {

    /**
     * 通过反射的方式创建ConfigService对象
     *
     * @param consoleAddress grace配置中心地址（例如 192.168.184.100:8848）
     * @return {@link ConfigService}
     */
    public static ConfigService createConfigService(String consoleAddress) {
        try {
            Class<?> graceConfigServiceClass =
                    Class.forName("com.grace.client.config.GraceConfigService");
            Constructor<?> constructor = graceConfigServiceClass.getConstructor(String.class);
            return (ConfigService) constructor.newInstance(consoleAddress);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 通过反射的方式创建ConfigService对象
     *
     * @param properties 指定配置中心基本属性
     * @return {@link ConfigService}
     */
    public static ConfigService createConfigService(Properties properties) {
        try {
            Class<?> graceConfigServiceClass =
                    Class.forName("com.grace.client.config.GraceConfigService");
            Constructor<?> constructor = graceConfigServiceClass.getConstructor(Properties.class);
            return (ConfigService) constructor.newInstance(properties);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
