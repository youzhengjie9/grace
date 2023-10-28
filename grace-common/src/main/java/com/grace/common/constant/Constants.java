package com.grace.common.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * instance常量
 *
 * @author youzhengjie
 * @date 2023/10/05 15:54:26
 */
public class Constants {

    /**
     * 默认grace控制台（服务端）的ip地址
     */
    public static final String DEFAULT_CONSOLE_ADDR = "127.0.0.1:8848";

    /**
     * 默认帐号
     */
    public static final String DEFAULT_USERNAME = "grace";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD = "grace";

    public static final String NAMESPACE_ID = "namespaceId";

    public static final String GROUP_NAME = "groupName";

    public static final String SERVICE_NAME = "serviceName";

    public static final String IP_ADDR = "ipAddr";

    public static final String PORT = "port";

    public static final String WEIGHT = "weight";

    public static final String HEALTHY = "healthy";

    public static final String ONLINE = "online";
    public static final String EPHEMERAL = "ephemeral";
    public static final String META_DATA = "metadata";
    public static final String ONLY_HEALTHY = "onlyHealthy";

    /**
     * dataId
     */
    public static final String DATA_ID = "dataId";

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
     * 默认的客户端端口
     */
    public static final Integer DEFAULT_CLIENT_PORT = 8080;

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


    /**
     * 命名空间默认最大的配置数
     */
    public static final int DEFAULT_MAX_CONFIG_COUNT = 200;

    /**
     * 客户端发送心跳请求的时间间隔,单位:毫秒（如这里设置5000,则说明每隔5秒发送一个心跳请求给grace-console）
     */
    public static final Long HEART_BEAT_INTERVAL = 5000L;
    /**
     * 心跳超时时间,单位: 毫秒（如果在heartBeatTimeout时间范围内某个实例没有发送请求则超时）
     * <p>
     * 如果(当前时间 - 某个实例最后一次心跳时间) > heartBeatTimeout）则会把该实例的healthy修改为false
     */
    public static final Long HEART_BEAT_TIMEOUT = 15000L;

    /**
     * 最大心跳超时时间,单位: 毫秒（如果在maxHeartBeatTimeout时间范围内某个实例没有发送请求则将该实例“删除”）
     * <p>
     * 如果(当前时间 - 某个实例最后一次心跳时间) > maxHeartBeatTimeout）则会把该实例“删除”
     */
    public static final Long MAX_HEART_BEAT_TIMEOUT = 30000L;
}
