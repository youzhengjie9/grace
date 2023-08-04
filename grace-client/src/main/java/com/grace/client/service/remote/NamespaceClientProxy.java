package com.grace.client.service.remote;

import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.utils.ListView;

import java.util.List;

public interface NamespaceClientProxy {

    void registerInstance(String serviceName, String groupName, RegisterInstanceDTO registerInstanceDTO);

    void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances);

    void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances);

    void deregisterInstance(String serviceName, String groupName, Instance instance);

    /**
     * Update instance to service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @param instance    instance
     */
    void updateInstance(String serviceName, String groupName, Instance instance);

    /**
     * Query Service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @return service
     */
    Service queryService(String serviceName, String groupName);

    /**
     * Create service.
     *
     * @param service  service
     */
    void createService(Service service);

    /**
     * Delete service.
     *
     * @param serviceName service name
     * @param groupName   group name
     * @return true if delete ok
     */
    boolean deleteService(String serviceName, String groupName);


    /**
     * Update service.
     *
     * @param service  service
     */
    void updateService(Service service);

    /**
     * Get service list.
     *
     * @param page    page number
     * @param size  size per page
     * @param groupName group name of service
     * @return list of service
     */
    ListView<String> getServiceList(int page, int size, String groupName);

}
