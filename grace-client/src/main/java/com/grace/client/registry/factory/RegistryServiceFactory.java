package com.grace.client.registry.factory;

import com.grace.client.registry.RegistryService;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * 创建RegistryService对象,用于操作注册中心
 *
 * @author youzhengjie
 * @date 2023/09/27 23:17:19
 */
public class RegistryServiceFactory {

    /**
     * 通过反射的方式创建RegistryService对象(注意: 这种方式使用的命名空间只能是默认的public，因为没有传namespace)
     *
     * @param serverAddr grace注册中心地址（例如 192.168.184.100:8500）
     * @return {@link RegistryService}
     */
    public static RegistryService createRegistryService(String serverAddr) {
        try {
            Class<?> graceRegistryServiceClass =
                    Class.forName("com.grace.client.registry.GraceRegistryService");
            Constructor<?> constructor = graceRegistryServiceClass.getConstructor(String.class);
            return (RegistryService)constructor.newInstance(serverAddr);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 通过反射的方式创建RegistryService对象（这种方式可以指定注册中心地址（serverAddr）和操作的命名空间（namespace））
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
