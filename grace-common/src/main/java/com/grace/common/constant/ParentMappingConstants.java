package com.grace.common.constant;

/**
 * 顶层@RequestMapping接口路径常量
 *
 * @author youzhengjie
 * @date 2023/07/13 23:48:31
 */
public final class ParentMappingConstants {


    /**
     * grace-server基础的url
     */
    public static final String GRACE_SERVER_BASE_URL = "/grace/server";

    /**
     * InstanceController接口的顶层路径
     */
    public static final String INSTANCE_CONTROLLER = GRACE_SERVER_BASE_URL+"/instance";

    /**
     * NamespaceController接口的顶层路径
     */
    public static final String NAMESPACE_CONTROLLER = GRACE_SERVER_BASE_URL+"/namespace";

    /**
     * ServiceController接口的顶层路径
     */
    public static final String SERVICE_CONTROLLER = GRACE_SERVER_BASE_URL+"/service";

    /**
     * ConfigController接口的顶层路径
     */
    public static final String CONFIG_CONTROLLER = GRACE_SERVER_BASE_URL+"/config";

    /**
     * ConfigVersionController接口的顶层路径
     */
    public static final String CONFIG_VERSION_CONTROLLER = GRACE_SERVER_BASE_URL+"/config/version";

    /**
     * UserController接口的顶层路径
     */
    public static final String USER_CONTROLLER = GRACE_SERVER_BASE_URL+"/user";

    /**
     * RoleController接口的顶层路径
     */
    public static final String ROLE_CONTROLLER = GRACE_SERVER_BASE_URL+"/role";

}
