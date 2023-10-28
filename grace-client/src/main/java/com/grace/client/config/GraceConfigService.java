package com.grace.client.config;

import com.alibaba.fastjson2.JSONObject;
import com.grace.client.http.Requester;
import com.grace.client.http.RestResult;
import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.constant.RequestMethodConstants;
import com.grace.common.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;

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

    public GraceConfigService(String consoleAddress) {
        this.requester = new Requester(consoleAddress);
    }
    public GraceConfigService(Properties properties) {
        this.requester = new Requester(properties);
    }

    @Override
    public Config getConfig(String namespaceId, String groupName, String dataId) {

        final Map<String, String> requestParamMap = new ConcurrentHashMap<>(32);
        requestParamMap.put(Constants.NAMESPACE_ID, namespaceId);
        requestParamMap.put(Constants.GROUP_NAME, groupName);
        requestParamMap.put(Constants.DATA_ID, dataId);

        RestResult<Object> result = requester.requestApi(ParentMappingConstants.CONFIG_CONTROLLER + "/getConfig",
                RequestMethodConstants.GET, requestParamMap, null);
        JSONObject jsonObject = (JSONObject) result.getData();
        return JSONObject.parseObject(jsonObject.toJSONString(), Config.class);
    }
}
