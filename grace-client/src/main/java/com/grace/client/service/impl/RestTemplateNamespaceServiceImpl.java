package com.grace.client.service.impl;

import com.grace.client.properties.GraceRegistryProperties;
import com.grace.client.service.NamespaceService;
import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.entity.Instance;
import com.grace.common.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
    public boolean createNamespace(String namespace) {
        return false;
    }

    @Override
    public boolean createNamespace(String name,String desc) {

        CreateNamespaceDTO createNamespaceDTO = new CreateNamespaceDTO(name, desc);

        String serverAddr = graceRegistryProperties.getServerAddr();

        ResponseResult<Boolean> result = restTemplate.postForObject("http://" + serverAddr + "/grace/server/namespace/createNamespace",
                createNamespaceDTO, ResponseResult.class);

        return result.getData();
    }

    @Override
    public boolean createService(String serviceName) {
        return false;
    }

    @Override
    public boolean createService(String serviceName, String namespace) {
        return false;
    }

    @Override
    public boolean createService(String serviceName, String namespace, String groupName) {
        return false;
    }

    @Override
    public boolean createService(String serviceName, String namespace, String groupName, String metaData) {
        return false;
    }

    @Override
    public boolean registerInstance(String serviceName, String ipAddr, int port) {
        return false;
    }

    @Override
    public boolean registerInstance(String serviceName, String ipAddr, int port, int weight) {
        return false;
    }

    @Override
    public boolean registerInstance(String serviceName, String ipAddr, int port, int weight, String metaData) {
        return false;
    }

    @Override
    public List<Instance> getAllInstances(String namespace, String serviceName) {
        return null;
    }

    @Override
    public Instance getInstance(String namespace, String serviceName, String ipAddr, int port) {
        return null;
    }


}
