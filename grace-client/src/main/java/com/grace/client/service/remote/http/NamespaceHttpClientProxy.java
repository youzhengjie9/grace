package com.grace.client.service.remote.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.grace.client.service.remote.NamespaceClientProxy;
import com.grace.common.constant.ParentMappingConstant;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.utils.ListView;
import com.grace.common.utils.ResponseResult;
import org.springframework.http.HttpStatus;

import java.util.List;

public class NamespaceHttpClientProxy implements NamespaceClientProxy {


    @Override
    public void registerInstance(String serviceName, String groupName, RegisterInstanceDTO registerInstanceDTO) {
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
    public void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances) {

    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, Instance instance) {

    }

    @Override
    public void updateInstance(String serviceName, String groupName, Instance instance) {

    }

    @Override
    public Service queryService(String serviceName, String groupName) {
        return null;
    }

    @Override
    public void createService(Service service) {

    }

    @Override
    public boolean deleteService(String serviceName, String groupName) {
        return false;
    }

    @Override
    public void updateService(Service service) {

    }

    @Override
    public ListView<String> getServiceList(int page, int size, String groupName) {
        return null;
    }
}
