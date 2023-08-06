package com.grace.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.entity.Namespace;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023-06-19 22:42:58
 */
public interface NamespaceService extends IService<Namespace> {


    /**
     * 创建命名空间
     *
     * @param namespace 命名空间
     * @return boolean
     */
    boolean createNamespace(Namespace namespace);


    /**
     * 获取命名空间id
     *
     * @param namespaceName 命名空间名称
     * @return long
     */
    long getNamespaceId(String namespaceName);


    /**
     * 是否存在该命名空间
     *
     * @param namespaceName 命名空间名称
     * @return boolean
     */
    boolean hasNamespace(String namespaceName);

}
