package com.grace.client.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.grace.client.http.Requester;
import com.grace.client.http.RestResult;
import com.grace.client.misc.TokenStorage;
import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.constant.RequestMethodConstants;
import com.grace.common.dto.ClientAddressBindConfigDTO;
import com.grace.common.entity.Config;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 操作grace配置中心
 *
 * @author youzhengjie
 * @date 2023/10/26 21:17:37
 */
public class GraceConfigService implements ConfigService {

    private final Requester requester;

    private final TokenStorage tokenStorage = TokenStorage.getSingleton();

    public GraceConfigService(String consoleAddress) {
        this.requester = new Requester(consoleAddress);
    }
    public GraceConfigService(Properties properties) {
        this.requester = new Requester(properties);
    }

    @Override
    public Config getConfig(String namespaceId, String groupName, String dataId) {

        final Map<String, String> requestHeaderMap = new ConcurrentHashMap<>(32);
        // 往请求头中放入我们刚刚登录成功后拿到的accessToken（没有这个token则无法访问目标接口）
        requestHeaderMap.put("accessToken",tokenStorage.getAccessToken());

        final Map<String, String> requestParamMap = new ConcurrentHashMap<>(32);
        requestParamMap.put(Constants.NAMESPACE_ID, namespaceId);
        requestParamMap.put(Constants.GROUP_NAME, groupName);
        requestParamMap.put(Constants.DATA_ID, dataId);

        RestResult<Object> result =
                requester.requestApi(ParentMappingConstants.CONFIG_CONTROLLER + "/getConfig",
                RequestMethodConstants.GET,requestHeaderMap, requestParamMap, null);
        JSONObject jsonObject = (JSONObject) result.getData();
        return JSONObject.parseObject(jsonObject.toJSONString(), Config.class);
    }

    @Override
    public Boolean clientAddressBindConfig(ClientAddressBindConfigDTO clientAddressBindConfigDTO) {
        final Map<String, String> requestHeaderMap = new ConcurrentHashMap<>(32);
        // 往请求头中放入我们刚刚登录成功后拿到的accessToken（没有这个token则无法访问目标接口）
        requestHeaderMap.put("accessToken",tokenStorage.getAccessToken());

        final Map<String, String> requestBodyMap = new ConcurrentHashMap<>(32);
        requestBodyMap.put(Constants.NAMESPACE_ID, String.valueOf(clientAddressBindConfigDTO.getNamespaceId()));
        requestBodyMap.put(Constants.GROUP_NAME, clientAddressBindConfigDTO.getGroupName());
        requestBodyMap.put(Constants.DATA_ID, clientAddressBindConfigDTO.getDataId());
        requestBodyMap.put("clientAddress", clientAddressBindConfigDTO.getClientAddress());
        RestResult<Object> result =
                requester.requestApi(ParentMappingConstants.CONFIG_CONTROLLER + "/clientAddressBindConfig",
                RequestMethodConstants.POST,requestHeaderMap, null, requestBodyMap);
        return (Boolean) result.getData();
    }
}
