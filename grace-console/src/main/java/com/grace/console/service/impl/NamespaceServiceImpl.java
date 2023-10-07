package com.grace.console.service.impl;

import com.grace.common.entity.Namespace;
import com.grace.console.service.NamespaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 命名空间服务impl
 *
 * @author youzhengjie
 * @date 2023/07/02 17:43:17
 */
@Service
public class NamespaceServiceImpl implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(NamespaceServiceImpl.class);

    private static final String DEFAULT_NAMESPACE_ID = "public";

    private static final int DEFAULT_QUOTA = 200;


    @Override
    public List<Namespace> getNamespaceList() {
        return null;
    }

    @Override
    public Namespace getNamespace(String namespaceId) {
        return null;
    }

    @Override
    public Boolean createNamespace(Namespace namespace) {
        return null;
    }

    @Override
    public Boolean modifyNamespace(Namespace namespace) {
        return null;
    }

    @Override
    public Boolean deleteNamespace(String namespaceId) {
        return null;
    }
}
