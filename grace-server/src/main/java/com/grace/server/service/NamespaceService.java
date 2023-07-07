package com.grace.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.entity.SysNamespace;
import com.grace.common.utils.ResponseResult;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023-06-19 22:42:58
 */
public interface NamespaceService extends IService<SysNamespace> {


    /**
     * 创建命名空间
     *
     * @param createNamespaceDTO 创建名称空间dto
     * @return {@link ResponseResult}<{@link Boolean}>
     */
    ResponseResult<Boolean> createNamespace(CreateNamespaceDTO createNamespaceDTO);

    /**
     * 是否存在该命名空间
     *
     * @param namespaceName 命名空间名称
     * @return {@link ResponseResult}<{@link Boolean}>
     */
    ResponseResult<Boolean> hasNamespace(String namespaceName);




}
