package com.grace.client.service.impl;

import com.grace.client.service.NamespaceService;
import com.grace.common.dto.CreateSysNamespaceDTO;
import com.grace.common.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 基于RestTemplate的名称空间服务impl
 *
 * @author youzhengjie
 * @date 2023/06/19 20:53:21
 */
@Service
public class RestTemplateNamespaceServiceImpl implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateNamespaceServiceImpl.class);

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseResult<Boolean> createNamespace(String name,String desc) {

        CreateSysNamespaceDTO createSysNamespaceDTO = new CreateSysNamespaceDTO(name, desc);

        return restTemplate.postForObject("http://localhost:8500/grace/server/namespace/createNamespace",
                createSysNamespaceDTO, ResponseResult.class);

    }
}
