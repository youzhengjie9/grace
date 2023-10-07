package com.grace.console.service.impl;

import com.grace.common.entity.Service;
import com.grace.common.utils.SnowId;
import com.grace.console.core.GroupManager;
import com.grace.console.service.NamespaceService;
import com.grace.console.service.SvcService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@org.springframework.stereotype.Service
public class SvcServiceImpl implements SvcService {

    private static final Logger log = LoggerFactory.getLogger(SvcServiceImpl.class);

    @Autowired
    private NamespaceService namespaceService;

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
