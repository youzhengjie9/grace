package com.grace.client.misc.factory;

import com.grace.client.misc.LoginService;
import com.grace.client.registry.RegistryService;

import java.lang.reflect.Constructor;
import java.util.Properties;

/**
 * 用于创建LoginService对象
 *
 * @author youzhengjie
 * @date 2023-11-01 00:12:19
 */
public class LoginServiceFactory {

    /**
     * 通过反射的方式创建LoginService对象
     *
     * @param consoleAddress grace控制台地址（例如 192.168.184.100:8848）
     * @return {@link LoginService}
     */
    public static LoginService createLoginService(String consoleAddress) {
        try {
            Class<?> graceLoginServiceClass =
                    Class.forName("com.grace.client.misc.GraceLoginService");
            Constructor<?> constructor = graceLoginServiceClass.getConstructor(String.class);
            return (LoginService) constructor.newInstance(consoleAddress);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 通过反射的方式创建LoginService对象
     *
     * @param properties 指定控制台的基本属性
     * @return {@link RegistryService}
     */
    public static LoginService createLoginService(Properties properties) {
        try {
            Class<?> graceLoginServiceClass =
                    Class.forName("com.grace.client.misc.GraceLoginService");
            Constructor<?> constructor = graceLoginServiceClass.getConstructor(Properties.class);
            return (LoginService)constructor.newInstance(properties);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
