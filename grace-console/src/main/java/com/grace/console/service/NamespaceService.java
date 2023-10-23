package com.grace.console.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.grace.common.entity.Namespace;
import com.grace.console.vo.NamespaceVO;

import java.util.List;

/**
 * 命名空间服务
 *
 * @author youzhengjie
 * @date 2023-06-19 22:42:58
 */
public interface NamespaceService extends IService<Namespace> {

    /**
     * 获取命名空间列表
     *
     * @return {@link List}<{@link NamespaceVO}>
     */
    List<NamespaceVO> getNamespaceList();

    /**
     * 根据namespaceId获取命名空间
     *
     * @param namespaceId namespaceId
     * @return {@link Namespace}
     */
    Namespace getNamespace(String namespaceId);

    /**
     * 创建命名空间
     *
     * @param namespace 命名空间
     * @return boolean
     */
    Boolean createNamespace(Namespace namespace);

    /**
     * 修改namespace
     *
     * @param namespace namespace
     * @return {@link Boolean}
     */
    Boolean modifyNamespace(Namespace namespace);

    /**
     * 删除namespace
     *
     * @param namespaceId namespaceId
     * @return {@link Boolean}
     */
    Boolean deleteNamespace(String namespaceId);


}
