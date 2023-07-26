package com.grace.client.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.grace.client.service.NamespaceService;
import com.grace.common.constant.ParentMappingConstant;
import com.grace.common.constant.PropertiesKeyConstant;
import com.grace.common.constant.PropertiesValueConstant;
import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.dto.CreateServiceDTO;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.Instance;
import com.grace.common.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.List;
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

    private static final int DEFAULT_WEIGHT = 1;

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
                boolean createNamespaceSuccess = createNamespace(namespace, namespaceDesc);
                log.info("命名空间:" + namespace + "创建" + (createNamespaceSuccess ? "成功" : "失败"));
            }
        }
        log.info(">>>>>>DefaultNamespaceServiceImpl初始化成功<<<<<<");
    }

    @Override
    public boolean createNamespace(String namespace) {
        return createNamespace(namespace,"");
    }

    @Override
    public boolean createNamespace(String namespace, String desc) {
        CreateNamespaceDTO createNamespaceDTO = new CreateNamespaceDTO(namespace, desc);
        // 将createSysNamespaceDTO对象转成json
        String json = JSON.toJSONString(createNamespaceDTO);

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.post(
                "http://"+ serverAddr + ParentMappingConstant.NAMESPACE_CONTROLLER +"/createNamespace")
                .body(json)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();
            ResponseResult<Boolean> result=JSON.parseObject(body, ResponseResult.class);
            return result.getData();
        }else {
            log.error("createNamespace 请求失败");
            return false;
        }
    }

    @Override
    public boolean createService(String serviceName) {
        return createService(serviceName,namespace,DEFAULT_GROUP,"");
    }

    @Override
    public boolean createService(String serviceName, String namespace) {
        return createService(serviceName, namespace,DEFAULT_GROUP,"");
    }

    @Override
    public boolean createService(String serviceName, String namespace, String groupName) {
        return createService(serviceName, namespace,groupName,"");
    }

    @Override
    public boolean createService(String serviceName, String namespace, String groupName, String metaData) {
        CreateServiceDTO createServiceDTO = new CreateServiceDTO();
        createServiceDTO.setServiceName(serviceName);
        createServiceDTO.setNamespaceName(namespace);
        createServiceDTO.setGroupName(groupName);
        createServiceDTO.setMetaData(metaData);

        String json = JSON.toJSONString(createServiceDTO);

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.post(
                "http://"+ serverAddr + ParentMappingConstant.SERVICE_CONTROLLER +"/createService")
                .body(json)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();

            ResponseResult<Boolean> result=JSON.parseObject(body, ResponseResult.class);
            return result.getData();
        }else {
            log.error("createService 请求失败");
            return false;
        }
    }

    @Override
    public boolean registerInstance(String serviceName, String ipAddr, int port) {
        return registerInstance(serviceName, ipAddr, port, DEFAULT_WEIGHT,"");
    }

    @Override
    public boolean registerInstance(String serviceName, String ipAddr, int port, int weight) {
        return registerInstance(serviceName, ipAddr, port, weight,"");
    }

    @Override
    public boolean registerInstance(String serviceName, String ipAddr, int port, int weight, String metaData) {
        RegisterInstanceDTO registerInstanceDTO = new RegisterInstanceDTO();
        registerInstanceDTO.setNamespace(namespace);
        registerInstanceDTO.setServiceName(serviceName);
        registerInstanceDTO.setIpAddr(ipAddr);
        registerInstanceDTO.setPort(port);
        registerInstanceDTO.setWeight(weight);
        registerInstanceDTO.setMetaData(metaData);

        String json = JSON.toJSONString(registerInstanceDTO);

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.post(
                "http://"+ serverAddr + ParentMappingConstant.INSTANCE_CONTROLLER +"/registerInstance")
                .body(json)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();

            ResponseResult<Boolean> result=JSON.parseObject(body, ResponseResult.class);
            return result.getData();
        }else {
            log.error("registerInstance 请求失败");
            return false;
        }
    }

    @Override
    public List<Instance> getAllInstances(String namespace, String serviceName) {

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.get(
                        "http://"+ serverAddr + ParentMappingConstant.INSTANCE_CONTROLLER
                                +"/getAllInstances/"+ namespace + "/" + serviceName)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String resultJson = httpResponse.body();
            ResponseResult<JSONArray> responseResult = JSON.parseObject(resultJson, ResponseResult.class);
            JSONArray data = responseResult.getData();
            String dataJson = JSONArray.toJSONString(data);
            return JSON.parseArray(dataJson, Instance.class);
        }else {
            log.error("getAllInstances 请求失败");
            return null;
        }
    }

    @Override
    public Instance getInstance(String namespace, String serviceName, String ipAddr, int port) {

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.get(
                        "http://"+ serverAddr + ParentMappingConstant.INSTANCE_CONTROLLER
                                +"/getInstance/"+ namespace + "/" + serviceName + "/" +ipAddr +"/" + port)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();
            ResponseResult<JSONObject> result=JSON.parseObject(body, ResponseResult.class);
            JSONObject data = result.getData();
            if(data !=null){
                return JSONObject.parseObject(result.getData().toJSONString(),Instance.class);
            }
            return null;
        }else {
            log.error("getInstance 请求失败");
            return null;
        }
    }


    /**
     * 是否存在该名称空间
     *
     * @param namespace 命名空间
     * @return boolean
     */
    private boolean hasNamespace(String namespace){

        // 使用hutool的HttpRequest类发送post请求
        HttpResponse httpResponse = HttpRequest.get(
                "http://"+ serverAddr + ParentMappingConstant.NAMESPACE_CONTROLLER +"/hasNamespace/"+namespace)
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
