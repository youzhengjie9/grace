package com.grace.client.factory;

import com.grace.client.service.NamespaceService;
import com.grace.client.service.impl.RestTemplateNamespaceServiceImpl;
import com.grace.common.exception.GraceException;
import com.grace.common.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class NamespaceFactory {

    private static final Logger log = LoggerFactory.getLogger(NamespaceFactory.class);

    /**
     * 创建默认的NamespaceService
     * <p>
     * 使用场景：纯Java环境(不使用Spring、SpringBoot)、Spring、SpringBoot
     * <p>
     * 通过反射获取DefaultNamespaceServiceImpl类对象
     *
     * @return {@link NamespaceService}
     */
    public static NamespaceService createDefaultNamespaceService(Properties properties)
            throws GraceException {
        try {
            Class<?> defaultNamespaceService =
                    Class.forName("com.grace.client.service.impl.DefaultNamespaceServiceImpl");
            Constructor<?> constructor = defaultNamespaceService.getConstructor(Properties.class);
            return (NamespaceService)constructor.newInstance(properties);
        } catch (Throwable e) {
            throw new GraceException(GraceException.CLIENT_INVALID_PARAM, e);
        }
    }

    /**
     * 创建基于RestTemplate的NamespaceService
     * <p>
     * 注意: 需要在application.yml配置GraceRegistryProperties类的属性才能生效！
     * <p>
     * 使用场景：Spring、SpringBoot
     *
     * @return {@link NamespaceService}
     */
    public static NamespaceService createRestTemplateNamespaceService(){
        return SpringUtils.getBean(RestTemplateNamespaceServiceImpl.class);
    }

}
