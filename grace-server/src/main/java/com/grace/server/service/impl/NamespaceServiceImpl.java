package com.grace.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.entity.Namespace;
import com.grace.common.utils.ResponseResult;
import com.grace.common.utils.SnowId;
import com.grace.server.mapper.NamespaceMapper;
import com.grace.server.service.NamespaceService;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 命名空间服务impl
 *
 * @author youzhengjie
 * @date 2023/07/02 17:43:17
 */
@Service
public class NamespaceServiceImpl extends ServiceImpl<NamespaceMapper, Namespace> implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(NamespaceServiceImpl.class);

    private NamespaceMapper namespaceMapper;

    @Autowired
    public void setSysNamespaceMapper(NamespaceMapper namespaceMapper) {
        this.namespaceMapper = namespaceMapper;
    }

    @Override
    public ResponseResult<Boolean> createNamespace(CreateNamespaceDTO createNamespaceDTO) {

        Namespace namespace = BeanUtil.copyProperties(createNamespaceDTO, Namespace.class);
        if(namespace != null) {
            namespace.setId(SnowId.nextId())
                    .setCreateTime(LocalDateTime.now());
            try {
                int res = namespaceMapper.insert(namespace);
                if(res == 0){
                    return ResponseResult.ok(false);
                }
                return ResponseResult.ok(true);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseResult.fail(false);
            }
        }
        return ResponseResult.ok(false);
    }

    @Override
    public Long getNamespaceId(String namespaceName) {
        return this.lambdaQuery()
                .select(Namespace::getId)
                .eq(Namespace::getNamespaceName, namespaceName)
                .one()
                .getId();
    }

    @Override
    public ResponseResult<Boolean> hasNamespace(String namespaceName) {

        LambdaQueryWrapper<Namespace> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Namespace::getNamespaceName,namespaceName);

        Long count = namespaceMapper.selectCount(queryWrapper);

        return count>0?ResponseResult.ok(true) : ResponseResult.ok(false);
    }
}
