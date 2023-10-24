package com.grace.client.registry;

import com.alibaba.fastjson2.JSON;
import com.grace.client.http.RestResult;
import com.grace.client.http.client.GraceRestTemplate;
import com.grace.client.http.factory.GraceRestTemplateFactory;
import com.grace.client.http.param.RequestHeader;
import com.grace.client.http.param.RequestParam;
import com.grace.client.properties.GraceRegistryProperties;
import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.constant.RequestMethodConstants;
import com.grace.common.constant.URLPrefixConstants;
import com.grace.common.dto.RegisterInstanceDTO;
import com.grace.common.utils.InternetAddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册中心的服务的实现类（客户端操作注册中心就是使用这个类！）
 *
 * @author youzhengjie
 * @date 2023/09/28 00:46:55
 */
public class GraceRegistryService implements RegistryService {

    private static final Logger log = LoggerFactory.getLogger(GraceRegistryService.class);

    private final GraceRestTemplate graceRestTemplate = GraceRestTemplateFactory.getInstance().createGraceRestTemplate();

    /**
     * 默认的控制台端口
     */
    private static final int DEFAULT_CONSOLE_PORT = 8848;

    private static final String PAGE = "page";

    private static final String SIZE = "size";

    @Autowired
    private GraceRegistryProperties graceRegistryProperties;

    @Override
    public Boolean registerInstance(RegisterInstanceDTO registerInstanceDTO) {
        log.info("registerInstanceDTO={}",registerInstanceDTO);
//        if (registerInstanceDTO.getEphemeral()) {
//            throw new UnsupportedOperationException(
//                    "不支持通过HTTP方式注册临时实例，请使用gRPC方式");
//        }
        final Map<String, String> requestBodyMap = new HashMap<>(32);
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

        RestResult<Object> result = requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/registerInstance",
                RequestMethodConstants.POST,
                null,
                requestBodyMap);
        System.out.println("======registerInstance的result=====");
        System.out.println(result);
        System.out.println(result.getCode());
        System.out.println(result.getMsg());
        System.out.println(result.getData());
        System.out.println("===========================");
        return (Boolean) result.getData();
    }

//    /**
//     * 获取所有实例
//     *
//     * @param serviceName 服务名称
//     * @param groupName   分组名称
//     * @param onlyHealthy 是否只挑选健康的实例
//     * @return {@link List}<{@link Instance}>
//     */
//    @Override
//    public List<Instance> getAllInstances(String serviceName, String groupName, boolean onlyHealthy) {
//        log.info("namespaceId={}, serviceName={}, groupName={} , onlyHealthy={}",
//                namespaceId, serviceName, groupName , onlyHealthy);
//
//        final Map<String, String> requestParamMap = new HashMap<>(32);
//        requestParamMap.put(Constants.NAMESPACE_ID, String.valueOf(namespaceId));
//        requestParamMap.put(Constants.SERVICE_NAME, serviceName);
//        requestParamMap.put(Constants.GROUP_NAME, groupName);
//        requestParamMap.put(Constants.ONLY_HEALTHY, String.valueOf(onlyHealthy));
//
//        requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/getAllInstances",
//                RequestMethodConstants.GET,
//                requestParamMap,
//                null);
//
//        // TODO: 2023/10/9 没实现
//        return null;
//    }
//
//    @Override
//    public com.grace.common.entity.Service queryService(String serviceName, String groupName) {
//        log.info("namespaceId={}, serviceName={}, groupName={}",
//                namespaceId, serviceName, groupName);
//
//        final Map<String, String> requestParamMap = new HashMap<>(16);
//        requestParamMap.put(ServiceConstants.NAMESPACE_ID, String.valueOf(namespaceId));
//        requestParamMap.put(ServiceConstants.SERVICE_NAME, serviceName);
//        requestParamMap.put(ServiceConstants.GROUP_NAME, groupName);
//
//        String resultData = requestApi(ParentMappingConstants.SERVICE_CONTROLLER + "/queryService",
//                RequestMethodConstants.GET,
//                requestParamMap,
//                null);
//        return JSONObject.parseObject(resultData, com.grace.common.entity.Service.class);
//    }
//
//    @Override
//    public PageData<String> getServiceNameList(String groupName, int page, int size) {
//
//        log.info("namespaceId={}, groupName={}, page={} , size={}",
//                namespaceId,groupName,page,size);
//
//        Map<String, String> requestParamMap = new HashMap<>(16);
//        requestParamMap.put(ServiceConstants.NAMESPACE_ID, String.valueOf(namespaceId));
//        requestParamMap.put(ServiceConstants.GROUP_NAME, groupName);
//        requestParamMap.put(PAGE, String.valueOf(page));
//        requestParamMap.put(SIZE, String.valueOf(size));
//
//        String resultData = requestApi(ParentMappingConstants.SERVICE_CONTROLLER + "/getServiceNameList",
//                RequestMethodConstants.GET,
//                requestParamMap,
//                null);
//        // 封装分页数据
//        PageData<String> pageData = new PageData<>();
//        JSONObject jsonObject = JSONObject.parseObject(resultData);
//        // 分页前数据的总记录数
//        pageData.setTotalCount(Integer.parseInt(jsonObject.get("count").toString()));
//        // 分页后的数据
//        pageData.setPagedList(JSON.parseArray(jsonObject.get("data").toString(), String.class));
//        return pageData;
//    }

    public RestResult<Object> requestApi(String api, String requestMethod , Map<String, String> requestParams) {
        return requestApi(api, requestMethod ,requestParams, Collections.EMPTY_MAP);
    }

    /**
     * 请求api接口（推荐使用这个方法）
     *
     * @param api       api接口地址
     * @param requestMethod    请求方法
     * @param requestParams    请求参数（没有就写null）
     * @param requestBody      请求体（没有就写null）
     * @return result
     */
    public RestResult<Object> requestApi(String api, String requestMethod , Map<String, String> requestParams,
                                         Map<String, String> requestBody) {
        // 获取grace控制台地址
        String graceConsoleAddress = graceRegistryProperties.getConsoleAddress();
        // 如果grace控制台地址为空
        if (StringUtils.isBlank(graceConsoleAddress)) {
            throw new RuntimeException("grace控制台地址列表为空");
        }
        try {
            // 调用刚刚选出来的grace控制台地址所在的服务器的controller接口
            return callServer(api, requestMethod , requestParams, requestBody, graceConsoleAddress);
        } catch (Exception e) {
            log.error("请求api接口: {} 失败, grace控制台地址: {}, 错误内容: {}",
                    api, graceConsoleAddress, e.getMessage());
            throw new RuntimeException("请求api接口失败:" + api + " grace控制台地址(" +
                    graceConsoleAddress + ") 错误内容: " + e.getMessage());
        }
    }

    /**
     * 调用后端服务器接口
     *
     * @param api                 api接口地址
     * @param requestMethod       请求方法
     * @param requestParams       请求参数（没有就写null）
     * @param requestBody         请求体（没有就写null）
     * @param graceConsoleAddress 一个grace控制台地址（从graceConsoleAddressList中选出来）
     * @return result
     */
    public RestResult<Object> callServer(String api, String requestMethod ,Map<String, String> requestParams,
                             Map<String, String> requestBody , String graceConsoleAddress) {
        // 记录开始时间
        long start = System.currentTimeMillis();
        long end = 0;
        String url;
        // 如果grace控制台地址的前缀包含“http://”或“https://”
        if (graceConsoleAddress.startsWith(URLPrefixConstants.HTTP_PREFIX) ||
                graceConsoleAddress.startsWith(URLPrefixConstants.HTTPS_PREFIX)) {
            // 拼接api接口路径
            url = graceConsoleAddress + api;
        }
        // 当grace控制台地址的前缀不包含“http://”或“https://”
        else {
            // 如果grace控制台地址不包含端口（例如graceConsoleAddress为192.168.184.100）
            if (!InternetAddressUtil.containsPort(graceConsoleAddress)) {
                // 则自动给graceConsoleAddress添加一个grace的默认端口（则graceConsoleAddress变为192.168.184.100:8848）
                graceConsoleAddress = graceConsoleAddress + InternetAddressUtil.IP_PORT_SPLITER + DEFAULT_CONSOLE_PORT;
            }
            // TODO: 2023/8/11 可以手动控制默认是http还是https
            // 拼接http前缀、和api接口路径
            url = URLPrefixConstants.HTTP_PREFIX + graceConsoleAddress + api;
        }
        try {
            // 请求头
            RequestHeader requestHeader = RequestHeader.newInstance();
            // 请求参数
            RequestParam requestParam = RequestParam.newInstance();
            requestParam.addRequestParams(requestParams);
            // 发送请求
            RestResult<Object> restResult = graceRestTemplate.exchange(url, requestMethod, requestHeader, requestParam,
                    requestBody, Object.class);
            // TODO: 2023/10/24 记录结束时间
            end = System.currentTimeMillis();
            return restResult;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
