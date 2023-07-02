package com.grace.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.dto.CreateSysNamespaceDTO;
import com.grace.common.entity.SysNamespace;
import com.grace.common.enums.ResponseType;
import com.grace.common.utils.ResponseResult;
import com.grace.server.mapper.SysNamespaceMapper;
import com.grace.server.service.SysNamespaceService;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 命名空间服务impl
 *
 * @author youzhengjie
 * @date 2023/07/02 17:43:17
 */
@Service
public class SysNamespaceServiceImpl extends ServiceImpl<SysNamespaceMapper, SysNamespace> implements SysNamespaceService {

    private SysNamespaceMapper sysNamespaceMapper;

    @Autowired
    public void setSysNamespaceMapper(SysNamespaceMapper sysNamespaceMapper) {
        this.sysNamespaceMapper = sysNamespaceMapper;
    }

    @Override
    public ResponseResult<Boolean> createNamespace(CreateSysNamespaceDTO createSysNamespaceDTO) {

        SysNamespace sysNamespace = BeanUtil.copyProperties(createSysNamespaceDTO, SysNamespace.class);

        sysNamespace

        int res = sysNamespaceMapper.insert(sysNamespace);

        return ResponseResult.build(ResponseType.SUCCESS,false);
    }
}
