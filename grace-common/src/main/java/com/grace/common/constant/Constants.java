package com.grace.common.constant;

/**
 * instance常量
 *
 * @author youzhengjie
 * @date 2023/10/05 15:54:26
 */
public class Constants {

    public static final String NAMESPACE_ID = "namespaceId";

    public static final String GROUP_NAME = "groupName";

    public static final String SERVICE_NAME = "serviceName";

    public static final String IP_ADDR = "ipAddr";

    public static final String PORT = "port";

    public static final String WEIGHT = "weight";

    public static final String HEALTHY = "healthy";
    public static final String EPHEMERAL = "ephemeral";
    public static final String META_DATA = "metadata";
    public static final String ONLY_HEALTHY = "onlyHealthy";

    /**
     * 默认的命名空间id
     */
    public static final Long DEFAULT_NAMESPACE_ID = 101L;

    /**
     * 默认的字符串类型的命名空间id（解决@RequestParam注解上的defaultValue属性只能接收String类型的常量的问题）
     */
    public static final String DEFAULT_STRING_NAMESPACE_ID = "101";

    /**
     * 默认的命名空间名称
     */
    public static final String DEFAULT_NAMESPACE_NAME = "public";

    /**
     * 默认的分组id
     */
//    public static final Long DEFAULT_GROUP_ID = 101L;

    /**
     * 默认的分组名称
     */
    public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";

    /**
     * 默认健康状态
     */
    public static final Boolean DEFAULT_HEALTHY = true;

    /**
     * 默认权重
     */
    public static final Double DEFAULT_WEIGHT = 1.0;

    /**
     * 默认是否为临时实例
     */
    public static final Boolean DEFAULT_EPHEMERAL = true;
}
