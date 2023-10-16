package com.grace.console.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.constant.Constants;
import com.grace.common.entity.Namespace;
import com.grace.console.core.GroupManager;
import com.grace.console.dto.ModifyNamespaceDTO;
import com.grace.console.mapper.NamespaceMapper;
import com.grace.console.service.NamespaceService;
import com.grace.console.vo.NamespaceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 命名空间服务impl
 *
 * @author youzhengjie
 * @date 2023/07/02 17:43:17
 */
@Service
public class NamespaceServiceImpl extends ServiceImpl<NamespaceMapper,Namespace> implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(NamespaceServiceImpl.class);

    @Autowired
    private NamespaceMapper namespaceMapper;

    private final GroupManager groupManager = GroupManager.getGroupManagerSingleton();

    @Override
    public List<NamespaceVO> getNamespaceList() {

        // 创建线程安全的集合
        List<NamespaceVO> namespaceVOList = Collections.synchronizedList(new ArrayList<>());
        // 由于public命名空间不存入数据库,所以要在代码中写死
        // TODO: 2023/10/16 配置数还未实现
        NamespaceVO publicNamespaceVO = new NamespaceVO(
                Constants.DEFAULT_NAMESPACE_ID,
                Constants.DEFAULT_NAMESPACE_NAME,
                groupManager.getServiceCount(Constants.DEFAULT_NAMESPACE_ID),
                666,
                200,
                ""
        );
        // 先将public命名空间放到集合的第一个位置作为默认的命名空间
        namespaceVOList.add(publicNamespaceVO);

        // 将数据库查询出来的List<Namespace>列表转成List<NamespaceVO>列表
        List<NamespaceVO> list = namespaceMapper.selectList(new LambdaQueryWrapper<>())
                .stream()
                .map(namespace -> {
                    String namespaceId = namespace.getNamespaceId();
                    int serviceCount = groupManager.getServiceCount(namespaceId);
                    return new NamespaceVO(
                            namespaceId,
                            namespace.getNamespaceName(),
                            serviceCount,
                            666,
                            namespace.getMaxConfigCount(),
                            namespace.getNamespaceDesc()
                    );
                })
                .collect(Collectors.toList());
        // 将数据库查询出来的命名空间列表全部添加到集合中
        namespaceVOList.addAll(list);

        return namespaceVOList;
    }

    @Override
    public Namespace getNamespace(String namespaceId) {
        return null;
    }

    @Override
    public Boolean createNamespace(Namespace namespace) {
        String namespaceId = namespace.getNamespaceId();
        String namespaceName = namespace.getNamespaceName();
        // 防止用户创建public默认命名空间（这种情况下不能创建）
        if(namespaceId.equals(Constants.DEFAULT_NAMESPACE_ID) ||
                namespaceName.equals(Constants.DEFAULT_NAMESPACE_NAME)){
            log.error("不能创建public默认命名空间。{}",namespace);
            return false;
        }
        namespace.setMaxConfigCount(Constants.DEFAULT_MAX_CONFIG_COUNT);
        // 因为我们数据库的namespaceId和namespaceName字段都是有唯一索引的,所以不需要判断是否存在,直接插入就行
        // 如果插入结果大于0,则说明插入成功,反之则插入失败
        int insertResult = namespaceMapper.insert(namespace);
        if(insertResult > 0){
            // 给groupMap创建指定的命名空间（前提是groupMap的key不存在该namespaceId）
            // 也就是说创建命名空间,不仅仅数据库要创建,groupMap也要创建
            return groupManager.createNamespaceIfAbsent(namespaceId);
        }
        return false;
    }

    @Override
    public Boolean modifyNamespace(ModifyNamespaceDTO modifyNamespaceDTO) {
        String namespaceId = modifyNamespaceDTO.getNamespaceId();
        String namespaceName = modifyNamespaceDTO.getNamespaceName();
        String namespaceDesc = modifyNamespaceDTO.getNamespaceDesc();
        // 如果命名空间存在
        if (groupManager.hasNamespace(namespaceId)) {


        }
        // 如果命名空间不存在,则修改失败
        return false;
    }

    @Override
    public Boolean deleteNamespace(String namespaceId) {
        return null;
    }
}
