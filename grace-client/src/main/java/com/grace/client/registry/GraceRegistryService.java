package com.grace.client.registry;

import com.alibaba.fastjson2.JSON;
import com.grace.client.http.Requester;
import com.grace.client.http.RestResult;
import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.constant.RequestMethodConstants;
import com.grace.common.dto.HeartBeat;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.entity.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册中心的服务的实现类（客户端操作注册中心就是使用这个类！）
 *
 * @author youzhengjie
 * @date 2023/09/28 00:46:55
 */
public class GraceRegistryService implements RegistryService {

    private static final Logger log = LoggerFactory.getLogger(GraceRegistryService.class);

    private static final String PAGE = "page";

    private static final String SIZE = "size";

    private final Requester requester;

    public GraceRegistryService(String consoleAddress) {
        this.requester = new Requester(consoleAddress);
    }

    public GraceRegistryService(Properties properties) {
        this.requester = new Requester(properties);
    }

    @Override
    public Boolean registerInstance(RegisterInstanceDTO registerInstanceDTO) {
        final Map<String, String> requestBodyMap = new ConcurrentReferenceHashMap<>(32);
        requestBodyMap.put(Constants.NAMESPACE_ID, String.valueOf(registerInstanceDTO.getNamespaceId()));
        requestBodyMap.put(Constants.GROUP_NAME, registerInstanceDTO.getGroupName());
        requestBodyMap.put(Constants.SERVICE_NAME, registerInstanceDTO.getServiceName());
        requestBodyMap.put(Constants.IP_ADDR, registerInstanceDTO.getIpAddr());
        requestBodyMap.put(Constants.PORT, String.valueOf(registerInstanceDTO.getPort()));
        requestBodyMap.put(Constants.WEIGHT, String.valueOf(registerInstanceDTO.getWeight()));
        requestBodyMap.put(Constants.HEALTHY, String.valueOf(registerInstanceDTO.getHealthy()));
        requestBodyMap.put(Constants.ONLINE, String.valueOf(registerInstanceDTO.getOnline()));
        requestBodyMap.put(Constants.EPHEMERAL, String.valueOf(registerInstanceDTO.getEphemeral()));
        requestBodyMap.put(Constants.META_DATA, JSON.toJSONString(registerInstanceDTO.getMetadata()));
        RestResult<Object> result = requester.requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/registerInstance",
                RequestMethodConstants.POST, null, requestBodyMap);
        return (Boolean) result.getData();
    }

    @Override
    public Boolean sendHeartBeat(HeartBeat heartBeat) {

        final Map<String, String> requestBodyMap = new ConcurrentHashMap<>(32);
        requestBodyMap.put(Constants.NAMESPACE_ID, String.valueOf(heartBeat.getNamespaceId()));
        requestBodyMap.put(Constants.GROUP_NAME, heartBeat.getGroupName());
        requestBodyMap.put(Constants.SERVICE_NAME, heartBeat.getServiceName());
        requestBodyMap.put(Constants.IP_ADDR, heartBeat.getIpAddr());
        requestBodyMap.put(Constants.PORT, String.valueOf(heartBeat.getPort()));

        RestResult<Object> result = requester.requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/heartBeat",
                RequestMethodConstants.PUT, null, requestBodyMap);

        return (Boolean) result.getData();
    }

    @Override
    public List<Instance> getAllInstance(String namespaceId, String groupName, String serviceName) {

        final Map<String, String> requestParamMap = new ConcurrentHashMap<>(32);
        requestParamMap.put(Constants.NAMESPACE_ID, namespaceId);
        requestParamMap.put(Constants.GROUP_NAME, groupName);
        requestParamMap.put(Constants.SERVICE_NAME, serviceName);

        RestResult<Object> result = requester.requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/getAllInstance",
                RequestMethodConstants.GET, requestParamMap, null);

        return (List<Instance>) result.getData();
    }


}
