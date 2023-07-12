package com.grace.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.entity.SysNamespace;
import com.grace.common.entity.SysService;
import com.grace.common.utils.ResponseResult;
import com.grace.common.utils.SnowId;
import com.grace.server.mapper.ServiceMapper;
import com.grace.server.service.NamespaceService;
import com.grace.server.service.SvcService;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SvcServiceImpl extends ServiceImpl<ServiceMapper, SysService> implements SvcService {

    private static final Logger log = LoggerFactory.getLogger(SvcServiceImpl.class);
    private ServiceMapper serviceMapper;

    private NamespaceService namespaceService;

    @Autowired
    public void setServiceMapper(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @Override
    public ResponseResult<Boolean> createService(CreateServiceDTO createServiceDTO) {
        SysService sysService = BeanUtil.copyProperties(createServiceDTO, SysService.class);
        String namespaceName = createServiceDTO.getNamespaceName();
        Boolean hasNamespace = namespaceService.hasNamespace(namespaceName).getData();
        // 如果传入的namespace不存在则范湖false
        if(!hasNamespace){
            log.error("传入的namespace不存在");
            return ResponseResult.ok(false);
        }
        // 根据命名空间名称查询该命名空间的信息
        SysNamespace sysNamespace = namespaceService.
                lambdaQuery()
                .select(SysNamespace::getId)
                .eq(SysNamespace::getNamespaceName, namespaceName)
                .one();
        Long namespaceId = sysNamespace.getId();
        // 只要这个count>0就不能创建这个service
        Long count = this.lambdaQuery()
                .eq(SysService::getNamespaceId,namespaceId)
                .eq(SysService::getServiceName, sysService.getServiceName())
                .count();
        // 如果在sys_service表中有某条记录同时等于createServiceDTO获取到的service名称和namespace的id，则不能创建这个service
        if(count > 0){
            log.error("该service名称已经存在");
            return ResponseResult.ok(false);
        }
        // 填充其余的属性
        sysService.setId(SnowId.nextId())
                .setNamespaceId(namespaceId)
                .setCreateTime(LocalDateTime.now());
        try {
            //插入数据
            int res = serviceMapper.insert(sysService);
            if(res == 0){
                return ResponseResult.ok(false);
            }
            return ResponseResult.ok(true);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.ok(false);
        }
    }
}
