package com.grace.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.dto.CreateSysNamespaceDTO;
import com.grace.common.entity.SysNamespace;
import com.grace.common.enums.ResponseType;
import com.grace.common.utils.ResponseResult;
import com.grace.common.utils.SnowId;
import com.grace.server.mapper.SysNamespaceMapper;
import com.grace.server.service.SysNamespaceService;
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
public class SysNamespaceServiceImpl extends ServiceImpl<SysNamespaceMapper, SysNamespace> implements SysNamespaceService {

    private static final Logger log = LoggerFactory.getLogger(SysNamespaceServiceImpl.class);

    private SysNamespaceMapper sysNamespaceMapper;

    @Autowired
    public void setSysNamespaceMapper(SysNamespaceMapper sysNamespaceMapper) {
        this.sysNamespaceMapper = sysNamespaceMapper;
    }

    @Override
    public ResponseResult<Boolean> createNamespace(CreateSysNamespaceDTO createSysNamespaceDTO) {

        SysNamespace sysNamespace = BeanUtil.copyProperties(createSysNamespaceDTO, SysNamespace.class);
        if(sysNamespace != null) {
            sysNamespace.setId(SnowId.nextId())
                    .setCreateTime(LocalDateTime.now());
            try {
                int res = sysNamespaceMapper.insert(sysNamespace);
                if(res == 0){
                    return ResponseResult.fail(false);
                }
                return ResponseResult.ok(true);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseResult.fail(false);
            }
        }
        return ResponseResult.fail(false);
    }
}
