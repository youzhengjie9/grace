package com.grace.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.entity.Namespace;
import com.grace.common.entity.Service;
import com.grace.common.utils.ResponseResult;
import com.grace.common.utils.SnowId;
import com.grace.server.service.NamespaceService;
import com.grace.server.service.SvcService;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@org.springframework.stereotype.Service
public class SvcServiceImpl implements SvcService {

    private static final Logger log = LoggerFactory.getLogger(SvcServiceImpl.class);

    /**
     * 存储service的map（key=命名空间id,value=service服务集合）
     */
    private static final Map<Long, List<Service>> serviceMap = new ConcurrentHashMap<>();

    private NamespaceService namespaceService;

    @Autowired
    public void setNamespaceService(NamespaceService namespaceService) {
        this.namespaceService = namespaceService;
    }

    @Override
    public ResponseResult<Boolean> createService(CreateServiceDTO createServiceDTO) {
        Service service = BeanUtil.copyProperties(createServiceDTO, Service.class);
        String namespaceName = createServiceDTO.getNamespaceName();
        // 如果命名空间名称为空
        if(StringUtils.isBlank(namespaceName)){
            log.error("传入的namespace名称为空");
            return ResponseResult.ok(false);
        }
        Boolean hasNamespace = namespaceService.hasNamespace(namespaceName).getData();
        // 如果传入的namespace不存在则范湖false
        if(!hasNamespace){
            log.error("传入的namespace不存在");
            return ResponseResult.ok(false);
        }
        // 根据命名空间名称查询该命名空间的id
        Long namespaceId = namespaceService.getNamespaceId(namespaceName);
        // 根据命名空间id获取其对应的存储service对象的集合
        List<Service> services = serviceMap.get(namespaceId);
        // 防止services集合为空，如果services集合为空则进行初始化集合
        if(services == null){
            services = new CopyOnWriteArrayList<>();
        }
        if(services.size()>0){
            // 只要这个count>0说明该service名称已经在该命名空间中存在，就不能创建这个service
            long count = services.stream()
                    .map(Service::getServiceName)
                    .distinct()
                    .filter(serviceName -> serviceName.equals(service.getServiceName()))
                    .count();
            // 如果在sys_service表中有某条记录同时等于createServiceDTO获取到的service名称和namespace的id，则不能创建这个service
            if(count > 0){
                log.error("该service名称已经存在");
                return ResponseResult.ok(false);
            }
        }
        // 填充其余的属性
        service.setId(SnowId.nextId())
                .setNamespaceId(namespaceId)
                .setCreateTime(LocalDateTime.now());
        try {
            services.add(service);
            // 将数据保存起来
            serviceMap.put(namespaceId,services);
            return ResponseResult.ok(true);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseResult.fail(false);
        }
    }

    @Override
    public ResponseResult<Boolean> hasService(String namespace, String serviceName) {
        // 如果命名空间名称为空
        if(StringUtils.isBlank(namespace)){
            log.error("传入的namespace名称为空");
            return ResponseResult.ok(false);
        }
        Boolean hasNamespace = namespaceService.hasNamespace(namespace).getData();
        // 如果传入的namespace不存在则范湖false
        if(!hasNamespace){
            log.error("传入的namespace不存在");
            return ResponseResult.ok(false);
        }
        // 根据命名空间名称查询该命名空间的id
        Long namespaceId = namespaceService.getNamespaceId(namespace);
        // 根据命名空间id获取其对应的存储service对象的集合
        List<Service> services = serviceMap.get(namespaceId);
        if(services.size()>0){
            // 只要这个count>0说明该service名称已经在该命名空间中存在
            long count = services.stream()
                    .map(Service::getServiceName)
                    .distinct()
                    .filter(svcName -> svcName.equals(serviceName))
                    .count();
            return ResponseResult.ok(count>0);
        }
        return ResponseResult.ok(false);
    }

    @Override
    public ResponseResult<List<Service>> getAllServices(String namespace) {
        // 如果命名空间名称为空
        if(StringUtils.isBlank(namespace)){
            log.error("传入的namespace名称为空");
            return ResponseResult.ok(new ArrayList<>());
        }
        Boolean hasNamespace = namespaceService.hasNamespace(namespace).getData();
        // 如果传入的namespace不存在则范湖false
        if(!hasNamespace){
            log.error("传入的namespace不存在");
            return ResponseResult.ok(new ArrayList<>());
        }
        // 根据命名空间名称查询该命名空间的id
        Long namespaceId = namespaceService.getNamespaceId(namespace);
        // 根据命名空间id获取其对应的存储service对象的集合
        List<Service> services = serviceMap.get(namespaceId);
        return ResponseResult.ok(services);
    }

    @Override
    public ResponseResult<Service> getService(String namespace, String serviceName) {
        // 如果命名空间名称为空
        if(StringUtils.isBlank(namespace)){
            log.error("传入的namespace名称为空");
            return ResponseResult.ok(null);
        }
        Boolean hasNamespace = namespaceService.hasNamespace(namespace).getData();
        // 如果传入的namespace不存在则范湖false
        if(!hasNamespace){
            log.error("传入的namespace不存在");
            return ResponseResult.ok(null);
        }
        // 根据命名空间名称查询该命名空间的id
        Long namespaceId = namespaceService.getNamespaceId(namespace);
        // 根据命名空间id获取其对应的存储service对象的集合
        List<Service> services = serviceMap.get(namespaceId);
        for (Service service : services) {
            String svcName = service.getServiceName();
            if(svcName.equals(serviceName)){
                return ResponseResult.ok(service);
            }
        }
        return ResponseResult.ok(null);
    }


}
