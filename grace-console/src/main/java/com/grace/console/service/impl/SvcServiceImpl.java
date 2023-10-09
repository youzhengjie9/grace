package com.grace.console.service.impl;

import com.grace.common.entity.Service;
import com.grace.console.core.GroupManager;
import com.grace.console.service.SvcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@org.springframework.stereotype.Service
public class SvcServiceImpl implements SvcService {

    private static final Logger log = LoggerFactory.getLogger(SvcServiceImpl.class);

    private final GroupManager groupManager = GroupManager.getInstance();

    @Override
    public Boolean createService(Service service) {
        return null;
    }

    @Override
    public Boolean hasService(String namespaceId, String groupName, String serviceName) {
        return null;
    }

    @Override
    public List<Service> getAllServiceByPage(String namespaceId, String groupName, int page, int size) {
        return null;
    }

    @Override
    public List<String> getAllServiceNameByPage(long namespaceId, String groupName, int page, int size) {
        return null;
    }

    @Override
    public int getServiceCount() {
        return 0;
    }

    @Override
    public Service getService(String namespaceId, String groupName, String serviceName) {
        return null;
    }
}
