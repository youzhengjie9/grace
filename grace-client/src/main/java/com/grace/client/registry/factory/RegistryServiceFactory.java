package com.grace.client.registry.factory;

import com.grace.client.registry.RegistryService;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * 用于创建RegistryService对象
 *
 * @author youzhengjie
 * @date 2023/09/27 23:17:19
 */
public class RegistryServiceFactory {

    /**
     * 通过反射的方式创建RegistryService对象
     *
     * @param consoleAddress grace注册中心地址（例如 192.168.184.100:8848）
     * @return {@link RegistryService}
     */
    public static RegistryService createRegistryService(String consoleAddress) {
        try {
            Class<?> graceRegistryServiceClass =
                    Class.forName("com.grace.client.registry.GraceRegistryService");
            Constructor<?> constructor = graceRegistryServiceClass.getConstructor(String.class);
            return (RegistryService)constructor.newInstance(consoleAddress);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 通过反射的方式创建RegistryService对象
     *
     * @param properties 指定注册中心基本属性
     * @return {@link RegistryService}
     */
    public static RegistryService createRegistryService(Properties properties) {
        try {
            Class<?> graceRegistryServiceClass =
                    Class.forName("com.grace.client.registry.GraceRegistryService");
            Constructor<?> constructor = graceRegistryServiceClass.getConstructor(Properties.class);
            return (RegistryService)constructor.newInstance(properties);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
