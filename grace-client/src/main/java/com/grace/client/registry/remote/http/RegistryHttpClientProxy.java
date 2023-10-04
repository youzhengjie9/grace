package com.grace.client.registry.remote.http;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.grace.client.registry.core.GraceConsoleAddressManager;
import com.grace.client.registry.remote.RegistryClientProxy;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.constant.RequestMethodConstants;
import com.grace.common.constant.URLPrefixConstants;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.http.RestResult;
import com.grace.common.http.client.GraceRestTemplate;
import com.grace.common.http.factory.GraceRestTemplateFactory;
import com.grace.common.http.param.Header;
import com.grace.common.http.param.RequestParam;
import com.grace.common.utils.CollectionUtils;
import com.grace.common.utils.InternetAddressUtil;
import com.grace.common.utils.PageData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 注意: 当前类为Http方式,不能注册和注销“临时实例”、不能批量注册和注销“所有实例”、（gRPC方式可以）
 * <p>
 * 注册中心的http客户端代理,底层使用GraceRestTemplate发送api请求，
 * （作用是: 以http的方式向注册中心发送api请求），注意: 所谓的客户端代理就是用来发送api请求的。
 *
 * @author youzhengjie
 * @date 2023-09-28 01:04:54
 */
public class RegistryHttpClientProxy implements RegistryClientProxy {

    private static final Logger log = LoggerFactory.getLogger(RegistryHttpClientProxy.class);

    private final GraceRestTemplate graceRestTemplate = GraceRestTemplateFactory.getInstance().createGraceRestTemplate();

    /**
     * 默认的控制台端口
     */
    private static final int DEFAULT_CONSOLE_PORT = 8848;

    /**
     * instance常量
     */
    public static final String NAMESPACE_ID = "namespaceId";

    public static final String GROUP_NAME = "groupName";

    public static final String SERVICE_NAME = "serviceName";

    private static final String IP_ADDR = "ipAddr";

    private static final String PORT = "port";

    private static final String WEIGHT = "weight";

    private static final String HEALTHY = "healthy";
    private static final String EPHEMERAL = "ephemeral";
    private static final String META_DATA = "metadata";
    private static final String ONLY_HEALTHY = "onlyHealthy";

    /**
     * 其他常量
     */
    private static final String PROTECT_THRESHOLD_PARAM = "protectThreshold";

    private final Long namespaceId;

    private static final String PAGE = "page";

    private static final String SIZE = "size";

    private final GraceConsoleAddressManager graceConsoleAddressManager;

    private int consolePort = DEFAULT_CONSOLE_PORT;

    public RegistryHttpClientProxy(Long namespaceId, GraceConsoleAddressManager graceConsoleAddressManager,
                                   Properties properties) {
        this.graceConsoleAddressManager = graceConsoleAddressManager;
        this.setConsolePort(DEFAULT_CONSOLE_PORT);
        this.namespaceId = namespaceId;
    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) {
        log.info("namespaceId={}, serviceName={}, groupName={} , instance={}",
                namespaceId, serviceName, groupName , instance.toString());
        if (instance.isEphemeral()) {
            throw new UnsupportedOperationException(
                    "不支持通过HTTP方式注册临时实例，请使用gRPC方式");
        }
        final Map<String, String> requestBodyMap = new HashMap<>(32);
        requestBodyMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestBodyMap.put(SERVICE_NAME, serviceName);
        requestBodyMap.put(GROUP_NAME, groupName);
        requestBodyMap.put(IP_ADDR, instance.getIpAddr());
        requestBodyMap.put(PORT, String.valueOf(instance.getPort()));
        requestBodyMap.put(WEIGHT, String.valueOf(instance.getWeight()));
        requestBodyMap.put(HEALTHY, String.valueOf(instance.isHealthy()));
        requestBodyMap.put(EPHEMERAL, String.valueOf(instance.isEphemeral()));
        requestBodyMap.put(META_DATA, JSON.toJSONString(instance.getMetadata()));
        requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/registerInstance",
                RequestMethodConstants.POST,
                null,
                requestBodyMap);
    }

    @Override
    public void batchRegisterInstance(String serviceName, String groupName, List<Instance> instances) {
        throw new UnsupportedOperationException(
                "不支持HTTP方式批量注册实例，请使用gRPC方式");
    }

    @Override
    public void batchDeregisterInstance(String serviceName, String groupName, List<Instance> instances) {
        throw new UnsupportedOperationException(
                "不支持HTTP方式批量注销实例，请使用gRPC方式");
    }

    @Override
    public void deregisterInstance(String serviceName, String groupName, Instance instance) {

        log.info("namespaceId={}, serviceName={}, groupName={} , instance={}",
                namespaceId, serviceName, groupName , instance.toString());
        if (instance.isEphemeral()) {
            throw new UnsupportedOperationException(
                    "不支持通过HTTP方式注销临时实例，请使用gRPC方式");
        }

        final Map<String, String> requestParamMap = new HashMap<>(16);
        requestParamMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestParamMap.put(SERVICE_NAME, serviceName);
        requestParamMap.put(GROUP_NAME, groupName);
        requestParamMap.put(IP_ADDR, instance.getIpAddr());
        requestParamMap.put(PORT, String.valueOf(instance.getPort()));

        requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/deregisterInstance",
                RequestMethodConstants.DELETE,
                requestParamMap,
                null);

    }

    @Override
    public void updateInstance(String serviceName, String groupName, Instance instance) {
        log.info("namespaceId={}, serviceName={}, groupName={} , instance={}",
                namespaceId, serviceName, groupName , instance.toString());
        final Map<String, String> requestBodyMap = new HashMap<>(32);
        requestBodyMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestBodyMap.put(SERVICE_NAME, serviceName);
        requestBodyMap.put(GROUP_NAME, groupName);
        requestBodyMap.put(IP_ADDR, instance.getIpAddr());
        requestBodyMap.put(PORT, String.valueOf(instance.getPort()));
        requestBodyMap.put(WEIGHT, String.valueOf(instance.getWeight()));
        requestBodyMap.put(HEALTHY, String.valueOf(instance.isHealthy()));
        requestBodyMap.put(EPHEMERAL, String.valueOf(instance.isEphemeral()));
        requestBodyMap.put(META_DATA, JSON.toJSONString(instance.getMetadata()));
        requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/updateInstance",
                RequestMethodConstants.PUT,
                null,
                requestBodyMap);
    }

    /**
     * 获取所有实例
     *
     * @param serviceName 服务名称
     * @param groupName   分组名称
     * @param onlyHealthy 是否只挑选健康的实例
     * @return {@link List}<{@link Instance}>
     */
    @Override
    public List<Instance> getAllInstances(String serviceName, String groupName, boolean onlyHealthy) {
        log.info("namespaceId={}, serviceName={}, groupName={} , onlyHealthy={}",
                namespaceId, serviceName, groupName , onlyHealthy);

        final Map<String, String> requestParamMap = new HashMap<>(32);
        requestParamMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestParamMap.put(SERVICE_NAME, serviceName);
        requestParamMap.put(GROUP_NAME, groupName);
        requestParamMap.put(ONLY_HEALTHY, String.valueOf(onlyHealthy));

        requestApi(ParentMappingConstants.INSTANCE_CONTROLLER + "/getAllInstances",
                RequestMethodConstants.GET,
                requestParamMap,
                null);
    }

    @Override
    public Service queryService(String serviceName, String groupName) {
        log.info("namespaceId={}, serviceName={}, groupName={}",
                namespaceId, serviceName, groupName);

        final Map<String, String> requestParamMap = new HashMap<>(16);
        requestParamMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestParamMap.put(SERVICE_NAME, serviceName);
        requestParamMap.put(GROUP_NAME, groupName);

        String resultData = requestApi(ParentMappingConstants.SERVICE_CONTROLLER + "/queryService",
                RequestMethodConstants.GET,
                requestParamMap,
                null);
        return JSONObject.parseObject(resultData,Service.class);
    }

    @Override
    public void createService(Service service) {
        log.info("namespaceId={}, service={}", namespaceId, service.toString());

        final Map<String, String> requestBodyMap = new HashMap<>(16);
        requestBodyMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestBodyMap.put(SERVICE_NAME, service.getServiceName());
        requestBodyMap.put(GROUP_NAME, service.getGroupName());
        requestBodyMap.put(META_DATA, JSON.toJSONString(service.getMetadata()));

        requestApi(ParentMappingConstants.SERVICE_CONTROLLER + "/createService",
                RequestMethodConstants.POST,
                null,
                requestBodyMap);
    }

    @Override
    public boolean deleteService(String serviceName, String groupName) {
        log.info("namespaceId={}, serviceName={}, groupName={}",
                namespaceId, serviceName, groupName);

        final Map<String, String> requestParamMap = new HashMap<>(16);
        requestParamMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestParamMap.put(SERVICE_NAME, serviceName);
        requestParamMap.put(GROUP_NAME, groupName);

        String resultData = requestApi(ParentMappingConstants.SERVICE_CONTROLLER + "/deleteService",
                RequestMethodConstants.DELETE,
                requestParamMap,
                null);
        return Boolean.parseBoolean(resultData);
    }

    @Override
    public void updateService(Service service) {
        log.info("namespaceId={}, service={}", namespaceId, service.toString());

        final Map<String, String> requestBodyMap = new HashMap<>(16);
        requestBodyMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestBodyMap.put(SERVICE_NAME, service.getServiceName());
        requestBodyMap.put(GROUP_NAME, service.getGroupName());
        requestBodyMap.put(META_DATA, JSON.toJSONString(service.getMetadata()));

        requestApi(ParentMappingConstants.SERVICE_CONTROLLER + "/updateService",
                RequestMethodConstants.PUT,
                null,
                requestBodyMap);
    }

    @Override
    public PageData<String> getServiceNameList(String groupName,int page, int size) {

        log.info("namespaceId={}, groupName={}, page={} , size={}",
                namespaceId,groupName,page,size);

        Map<String, String> requestParamMap = new HashMap<>(16);
        requestParamMap.put(NAMESPACE_ID, String.valueOf(namespaceId));
        requestParamMap.put(GROUP_NAME, groupName);
        requestParamMap.put(PAGE, String.valueOf(page));
        requestParamMap.put(SIZE, String.valueOf(size));

        String resultData = requestApi(ParentMappingConstants.SERVICE_CONTROLLER + "/getServiceNameList",
                RequestMethodConstants.GET,
                requestParamMap,
                null);
        // 封装分页数据
        PageData<String> pageData = new PageData<>();
        JSONObject jsonObject = JSONObject.parseObject(resultData);
        // 分页前数据的总记录数
        pageData.setCount(Integer.parseInt(jsonObject.get("count").toString()));
        // 分页后的数据
        pageData.setData(JSON.parseArray(jsonObject.get("data").toString(), String.class));
        return pageData;
    }

    public String requestApi(String api, String requestMethod , Map<String, String> requestParams) {
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
    public String requestApi(String api, String requestMethod ,Map<String, String> requestParams,
                             Map<String, String> requestBody) {
        // 获取grace控制台地址列表
        List<String> graceConsoleAddressList = graceConsoleAddressManager.getConsoleAddressList();
        // 如果grace控制台地址列表为空
        if (CollectionUtils.isEmpty(graceConsoleAddressList)) {
            throw new RuntimeException("GraceConsoleAddressManager类中的grace控制台地址列表为空");
        }
        Random random = new Random();
        // 从grace控制台地址列表中“随机”选出一个grace控制台地址
        String graceConsoleAddress =
                graceConsoleAddressList.get(random.nextInt(graceConsoleAddressList.size()));
        try {
            // 调用刚刚选出来的grace控制台地址所在的服务器的controller接口
            return callServer(api, requestMethod , requestParams, requestBody, graceConsoleAddress);
        } catch (Exception e) {
            log.error("请求api接口: {} 失败, grace控制台地址列表: {}, 错误内容: {}",
                    api, graceConsoleAddressList, e.getMessage());
            throw new RuntimeException("请求api接口失败:" + api + " grace控制台地址列表(" +
                    graceConsoleAddressList + ") 错误内容: " + e.getMessage());
        }
    }

    /**
     * 调用后端服务器接口
     *
     * @param api       api接口地址
     * @param requestMethod    请求方法
     * @param requestParams    请求参数（没有就写null）
     * @param requestBody      请求体（没有就写null）
     * @param graceConsoleAddress 一个grace控制台地址（从graceConsoleAddressList中选出来）
     * @return result
     */
    public String callServer(String api, String requestMethod ,Map<String, String> requestParams,
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
                graceConsoleAddress = graceConsoleAddress + InternetAddressUtil.IP_PORT_SPLITER + consolePort;
            }
            // TODO: 2023/8/11 可以手动控制默认是http还是https
            // 拼接http前缀、和api接口路径
            url = URLPrefixConstants.HTTP_PREFIX + graceConsoleAddress + api;
        }
        try {
            // 请求头
            Header requestHeader = Header.newInstance();
            // 请求参数
            RequestParam requestParam = RequestParam.newInstance();
            requestParam.addRequestParams(requestParams);
            // 发送请求
            RestResult<String> restResult = graceRestTemplate.exchange(url, requestMethod, requestHeader, requestParam,
                    requestBody, String.class);
            // 记录结束时间
            end = System.currentTimeMillis();
            if (restResult != null) {
                return restResult.getData();
            }
            throw new RuntimeException("restResult为空");
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public void setConsolePort(int consolePort) {
        this.consolePort = consolePort;
    }
}
