package com.grace.client.service.impl;

import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.service.NamespaceService;
import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

/**
 * 基于RestTemplate的名称空间服务impl
 * <p>
 * 使用场景：Spring、SpringBoot
 *
 * @author youzhengjie
 * @date 2023/06/19 20:53:21
 */
@Service
public class RestTemplateNamespaceServiceImpl implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateNamespaceServiceImpl.class);

    private RestTemplate restTemplate;

    private GraceRegistryProperties graceRegistryProperties;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setGraceRegistryProperties(GraceRegistryProperties graceRegistryProperties) {
        this.graceRegistryProperties = graceRegistryProperties;
    }

    @Override
    public ResponseResult<Boolean> createNamespace(String namespace) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> createNamespace(String name,String desc) {

        CreateNamespaceDTO createNamespaceDTO = new CreateNamespaceDTO(name, desc);

        String serverAddr = graceRegistryProperties.getServerAddr();

        return restTemplate.postForObject("http://"+ serverAddr +"/grace/server/namespace/createNamespace",
                createNamespaceDTO, ResponseResult.class);

    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName, String namespace) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName, String namespace, String groupName) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName, String namespace, String groupName, String metaData) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> registerServiceInstance(String serviceName, String ipAddr, int port) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> registerServiceInstance(String serviceName, String ipAddr, int port, int weight) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> registerServiceInstance(String serviceName, String ipAddr, int port, int weight, String metaData) {
        return null;
    }
}
