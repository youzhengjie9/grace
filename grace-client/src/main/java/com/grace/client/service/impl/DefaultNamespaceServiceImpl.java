package com.grace.client.service.impl;

import com.grace.client.service.NamespaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DefaultNamespaceServiceImpl implements NamespaceService {

    private static final Logger log = LoggerFactory.getLogger(DefaultNamespaceServiceImpl.class);


    @Override
    public boolean createNamespace(String name) {

        
        return false;
    }
}
