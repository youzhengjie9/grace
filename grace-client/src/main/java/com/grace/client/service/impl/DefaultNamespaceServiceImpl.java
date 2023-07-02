package com.grace.client.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSON;
import com.grace.client.service.NamespaceService;
import com.grace.common.dto.CreateSysNamespaceDTO;
import com.grace.common.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class DefaultNamespaceServiceImpl implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(DefaultNamespaceServiceImpl.class);

    @Override
    public ResponseResult<Boolean> createNamespace(String name, String desc) {
        CreateSysNamespaceDTO createSysNamespaceDTO = new CreateSysNamespaceDTO(name, desc);
        String json = JSON.toJSONString(createSysNamespaceDTO);

        HttpResponse httpResponse = HttpRequest.post("http://localhost:8500/grace/server/namespace/createNamespace")
                .body(json)
                .execute();

        // 返回http状态码
        int status = httpResponse.getStatus();

        if(status == HttpStatus.OK.value()){
            // 返回响应内容
            String body = httpResponse.body();

            return JSON.parseObject(body, ResponseResult.class);
        }else {
            log.error("createNamespace请求失败");
            return ResponseResult.fail(false);
        }

    }
}
