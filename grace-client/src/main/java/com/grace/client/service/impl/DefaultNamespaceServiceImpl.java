package com.grace.client.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.grace.client.service.NamespaceService;
import com.grace.common.constant.PropertiesKeyConstant;
import com.grace.common.constant.PropertiesValueConstant;
import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.Properties;

/**
 * 默认名称空间服务impl
 * <p>
 * 使用场景：纯Java环境(不使用Spring、SpringBoot)、Spring、SpringBoot
 *
 * @author youzhengjie
 * @date 2023/07/06 12:19:30
 */
public class DefaultNamespaceServiceImpl implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(DefaultNamespaceServiceImpl.class);

    private static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    private String serverAddr;

    private String namespace;

    private String namespaceDesc;

    public DefaultNamespaceServiceImpl(String serverAddr,String namespace) {
        Properties properties = new Properties();
        properties.setProperty(PropertiesKeyConstant.SERVER_ADDR,serverAddr);
        properties.setProperty(PropertiesKeyConstant.NAMESPACE,namespace);
        init(properties);
    }

    public DefaultNamespaceServiceImpl(Properties properties) {
        init(properties);
    }

    /**
     * 初始化方法
     *
     * @param properties 属性
     */
    private void init(Properties properties) {
        log.info(">>>>>>DefaultNamespaceServiceImpl开始初始化<<<<<<");
        this.serverAddr = properties.getProperty(PropertiesKeyConstant.SERVER_ADDR);
        this.namespace = properties.getProperty(PropertiesKeyConstant.NAMESPACE);
        String autoCreateNamespace = properties.getProperty(PropertiesKeyConstant.AUTO_CREATE_NAMESPACE, PropertiesValueConstant.OFF);
        //如果开启了自动创建命名空间
        if(autoCreateNamespace.equals(PropertiesValueConstant.ON)) {
            //如果该namespace没有被创建则创建
            if (!hasNamespace(namespace)) {
                this.namespaceDesc = properties.getProperty(PropertiesKeyConstant.NAMESPACE_DESC, "");
                Boolean createNamespaceSuccess = createNamespace(namespace, namespaceDesc).getData();
                log.info("命名空间:" + namespace + "创建" + (createNamespaceSuccess ? "成功" : "失败"));
            }
        }
        log.info(">>>>>>DefaultNamespaceServiceImpl初始化成功<<<<<<");
    }

    @Override
    public ResponseResult<Boolean> createNamespace(String namespace) {
        return createNamespace(namespace,"");
    }

    @Override
    public ResponseResult<Boolean> createNamespace(String namespace, String desc) {
        CreateNamespaceDTO createNamespaceDTO = new CreateNamespaceDTO(namespace, desc);
        // 将createSysNamespaceDTO对象转成json
        String json = JSON.toJSONString(createNamespaceDTO);

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.post("http://"+ serverAddr +"/grace/server/namespace/createNamespace")
                .body(json)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();

            return JSON.parseObject(body, ResponseResult.class);
        }else {
            log.error("createNamespace 请求失败");
            return ResponseResult.fail(false);
        }
    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName) {
        return createService(serviceName,namespace,DEFAULT_GROUP,"");
    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName, String namespace) {
        return createService(serviceName, namespace,DEFAULT_GROUP,"");
    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName, String namespace, String groupName) {
        return createService(serviceName, namespace,groupName,"");
    }

    @Override
    public ResponseResult<Boolean> createService(String serviceName, String namespace, String groupName, String metaData) {
        CreateServiceDTO createServiceDTO = new CreateServiceDTO();
        createServiceDTO.setServiceName(serviceName);
        createServiceDTO.setNamespaceName(namespace);
        createServiceDTO.setGroupName(groupName);
        createServiceDTO.setMetaData(metaData);

        String json = JSON.toJSONString(createServiceDTO);

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.post("http://"+ serverAddr +"/grace/server/service/createService")
                .body(json)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();

            return JSON.parseObject(body, ResponseResult.class);
        }else {
            log.error("createService 请求失败");
            return ResponseResult.fail(false);
        }
    }

    @Override
    public ResponseResult<Boolean> registerInstance(String serviceName, String ipAddr, int port) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> registerInstance(String serviceName, String ipAddr, int port, int weight) {
        return null;
    }

    @Override
    public ResponseResult<Boolean> registerInstance(String serviceName, String ipAddr, int port, int weight, String metaData) {
        return null;
    }


    /**
     * 是否存在该名称空间
     *
     * @param namespace 命名空间
     * @return boolean
     */
    private boolean hasNamespace(String namespace){

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.get("http://"+ serverAddr +"/grace/server/namespace/hasNamespace/"+namespace)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();
        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();
            ResponseResult<Boolean> responseResult = JSON.parseObject(body, ResponseResult.class);
            return responseResult.getData();
        }else {
            log.error("hasNamespace 请求失败");
            return false;
        }
    }


}
