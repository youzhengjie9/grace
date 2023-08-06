package com.grace.client.service.remote.http;

import com.grace.client.core.ServerListManager;
import com.grace.client.service.remote.NamespaceClientProxy;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.utils.ListView;

import java.util.List;
import java.util.Properties;

public class NamespaceHttpClientProxy implements NamespaceClientProxy {

    private static final int DEFAULT_SERVER_PORT = 8500;

    private final String namespaceId;

    private final ServerListManager serverListManager;

    private int serverPort = DEFAULT_SERVER_PORT;

    public NamespaceHttpClientProxy(String namespaceId, ServerListManager serverListManager,
                                 Properties properties) {
        this.serverListManager = serverListManager;
        this.setServerPort(DEFAULT_SERVER_PORT);
        this.namespaceId = namespaceId;
    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) {

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

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
