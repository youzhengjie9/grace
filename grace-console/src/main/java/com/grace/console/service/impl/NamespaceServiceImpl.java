package com.grace.console.service.impl;

import com.grace.common.entity.Namespace;
import com.grace.console.service.NamespaceService;
import com.grace.console.vo.NamespaceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    private static final int DEFAULT_QUOTA = 200;

    @Override
    public List<NamespaceVO> getNamespaceList() {

        // TODO: 2023/10/11 暂时模拟namespace数据,以后要从数据库中获取
        NamespaceVO publicNamespace = new NamespaceVO(
                "",
                "public",
                -66,
                5,
                "默认环境"
        );
        NamespaceVO devNamespace = new NamespaceVO(
                "dev-namespace",
                "dev",
                -77,
                3,
                "开发环境"
        );
        NamespaceVO testNamespace = new NamespaceVO(
                "test-namespace",
                "test",
                -88,
                2,
                "测试环境"
        );
        List<NamespaceVO> namespaceVOList = new ArrayList<>();
        namespaceVOList.add(publicNamespace);
        namespaceVOList.add(devNamespace);
        namespaceVOList.add(testNamespace);
        return namespaceVOList;
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
