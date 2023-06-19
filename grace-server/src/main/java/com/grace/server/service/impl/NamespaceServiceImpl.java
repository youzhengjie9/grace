package com.grace.server.service.impl;

import com.grace.common.dto.CreateNamespaceDTO;
import com.grace.common.enums.ResponseType;
import com.grace.common.utils.ResponseResult;
import com.grace.server.service.NamespaceService;
import org.springframework.stereotype.Service;

@Service
public class NamespaceServiceImpl implements NamespaceService {

    @Override
    public ResponseResult<Boolean> createNamespace(CreateNamespaceDTO createNamespaceDTO) {
        System.out.println("createNamespace--666");
        return ResponseResult.build(ResponseType.SUCCESS,false);
    }
}
