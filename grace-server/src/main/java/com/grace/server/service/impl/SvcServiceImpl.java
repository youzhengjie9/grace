package com.grace.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.entity.SysNamespace;
import com.grace.common.entity.SysService;
import com.grace.common.utils.ResponseResult;
import com.grace.common.utils.SnowId;
import com.grace.server.mapper.ServiceMapper;
import com.grace.server.service.SvcService;
import net.dreamlu.mica.core.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SvcServiceImpl extends ServiceImpl<ServiceMapper, SysService> implements SvcService {

    private ServiceMapper serviceMapper;

    @Autowired
    public void setServiceMapper(ServiceMapper serviceMapper) {
        this.serviceMapper = serviceMapper;
    }

    @Override
    public ResponseResult<Boolean> createService(CreateServiceDTO createServiceDTO) {
        SysService sysService = BeanUtil.copyProperties(createServiceDTO, SysService.class);
        if(sysService != null) {
            sysService.setId(SnowId.nextId())
                    .setCreateTime(LocalDateTime.now());
            try {
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
        return ResponseResult.ok(false);
    }
}
