package com.grace.common.constant;

import org.apache.commons.lang3.StringUtils;

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
     * 默认的命名空间（public）的命名空间id为空字符串
     */
    public static final String DEFAULT_NAMESPACE_ID = "";

    /**
     * 默认的命名空间名称为public
     */
    public static final String DEFAULT_NAMESPACE_NAME = "public";

    /**
     * 默认的分组名称
     */
    public static final String DEFAULT_GROUP_NAME = "DEFAULT_GROUP";

    /**
     * 默认健康状态
     */
    public static final Boolean DEFAULT_HEALTHY = true;

    /**
     * 默认是否在线
     */
    public static final Boolean DEFAULT_ONLINE = true;

    /**
     * 默认权重
     */
    public static final double DEFAULT_WEIGHT = 1.0;

    /**
     * 最小权重
     */
    public static final double MIN_WEIGHT_VALUE = 0.00D;

    /**
     * 最大权重
     */
    public static final double MAX_WEIGHT_VALUE = 10000.0D;

    /**
     * 默认是否为临时实例
     */
    public static final Boolean DEFAULT_EPHEMERAL = true;

    public static final String ENCODE = "UTF-8";

}
