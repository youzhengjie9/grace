package com.grace.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.SysInstance;
import com.grace.common.entity.SysNamespace;
import com.grace.common.entity.SysService;
import com.grace.common.utils.ResponseResult;
import com.grace.common.utils.SnowId;
import com.grace.server.mapper.InstanceMapper;
import com.grace.server.service.InstanceService;
import com.grace.server.service.NamespaceService;
import com.grace.server.service.SvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * instance service impl
 *
 * @author youzhengjie
 * @date 2023/07/13 17:19:51
 */
@Service
public class InstanceServiceImpl extends ServiceImpl<InstanceMapper, SysInstance> implements InstanceService {

    private InstanceMapper instanceMapper;

    private SvcService svcService;

    private NamespaceService namespaceService;

    @Autowired
    public void setInstanceMapper(InstanceMapper instanceMapper) {
        this.instanceMapper = instanceMapper;
    }

    @Autowired
    public void setSvcService(SvcService svcService) {
        this.svcService = svcService;
    }

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @Override
    public ResponseResult<Boolean> registerInstance(RegisterInstanceDTO registerInstanceDTO) {
        SysInstance sysInstance = BeanUtil.copyProperties(registerInstanceDTO, SysInstance.class);
        String namespace = registerInstanceDTO.getNamespace();
        String serviceName = registerInstanceDTO.getServiceName();
        // 根据命名空间名称查询命名空间的id
        SysNamespace sysNamespace = namespaceService.lambdaQuery()
                .select(SysNamespace::getId)
                .eq(SysNamespace::getNamespaceName, namespace)
                .one();
        // 根据服务名查询服务的id
        SysService sysService = svcService.lambdaQuery()
                .select(SysService::getId)
                .eq(SysService::getServiceName, serviceName)
                .one();

        long instanceId = SnowId.nextId();
        Long namespaceId = sysNamespace.getId();
        Long serviceId = sysService.getId();
        sysInstance.setId(instanceId)
                .setServiceId(serviceId)
                .setCreateTime(LocalDateTime.now());


        return null;
    }
}
