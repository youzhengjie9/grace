package com.grace.client.service.remote.http;

import com.grace.client.core.ServerListManager;
import com.grace.client.service.remote.NamespaceClientProxy;
import com.grace.common.constant.RequestParamConstant;
import com.grace.common.constant.ParentMappingConstant;
import com.grace.common.constant.URLPrefixConstant;
import com.grace.common.entity.Instance;
import com.grace.common.entity.Service;
import com.grace.common.exception.GraceException;
import com.grace.common.utils.*;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 命名名称http客户端代理
 *
 * @author youzhengjie
 * @date 2023/08/08 16:32:29
 */
public class NamespaceHttpClientProxy implements NamespaceClientProxy {

    private static final Logger log = LoggerFactory.getLogger(NamespaceHttpClientProxy.class);

    private static final int DEFAULT_SERVER_PORT = 8500;

    private static final String IP_PARAM = "ip";

    private static final String PORT_PARAM = "port";

    private static final String WEIGHT_PARAM = "weight";

    private static final String ENABLE_PARAM = "enabled";

    private static final String EPHEMERAL_PARAM = "ephemeral";

    private static final String META_PARAM = "metadata";

    private static final String SELECTOR_PARAM = "selector";

    private static final String HEALTHY_PARAM = "healthy";

    private static final String PROTECT_THRESHOLD_PARAM = "protectThreshold";

    private static final String CLUSTERS_PARAM = "clusters";

    private static final String UDP_PORT_PARAM = "udpPort";

    private static final String CLIENT_IP_PARAM = "clientIP";

    private static final String HEALTHY_ONLY_PARAM = "healthyOnly";

    private final Long namespaceId;

    private final ServerListManager serverListManager;

    private int serverPort = DEFAULT_SERVER_PORT;

    public NamespaceHttpClientProxy(Long namespaceId, ServerListManager serverListManager,
                                 Properties properties) {
        this.serverListManager = serverListManager;
        this.setServerPort(DEFAULT_SERVER_PORT);
        this.namespaceId = namespaceId;
    }

    @Override
    public void registerInstance(String serviceName, String groupName, Instance instance) throws GraceException {
        log.info("[REGISTER-SERVICE] {} registering service {} with instance: {}", namespaceId, serviceName,
                instance);
        String groupedServiceName = NamespaceUtils.getGroupedName(serviceName, groupName);
        if (instance.isEphemeral()) {
            throw new UnsupportedOperationException(
                    "不支持通过HTTP注册临时实例，请用gRPC代替");
        }
        // 传递请求参数
        final Map<String, String> params = new HashMap<>(32);
        params.put(RequestParamConstant.NAMESPACE_ID, String.valueOf(namespaceId));
        params.put(RequestParamConstant.SERVICE_NAME, groupedServiceName);
        params.put(RequestParamConstant.GROUP_NAME, groupName);
        params.put(IP_PARAM, instance.getIpAddr());
        params.put(PORT_PARAM, String.valueOf(instance.getPort()));
        params.put(WEIGHT_PARAM, String.valueOf(instance.getWeight()));
        params.put(HEALTHY_PARAM, String.valueOf(instance.isHealthy()));
        params.put(EPHEMERAL_PARAM, String.valueOf(instance.isEphemeral()));
        params.put(META_PARAM, JacksonUtils.toJson(instance.getMetadata()));
        requestApi(ParentMappingConstant.INSTANCE_CONTROLLER + "/registerInstance", params, HttpMethod.POST);
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
    public ListView<String> getServiceNameList(int page, int size, String groupName) {
        return null;
    }

    @Override
    public List<Instance> getAllInstances(String serviceName, String groupName, boolean healthyOnly) {
        return null;
    }

    public String requestApi(String api, Map<String, String> params, String method) throws GraceException {
        return requestApi(api, params, Collections.EMPTY_MAP, method);
    }

    public String requestApi(String api, Map<String, String> params, Map<String, String> body, String method)
            throws GraceException {
        return requestApi(api, params, body, serverListManager.getServerList(), method);
    }

    /**
     * Request api.
     *
     * @param api     api
     * @param params  parameters
     * @param body    body
     * @param servers servers
     * @param method  http method
     * @return result
     * @throws GraceException grace exception
     */
    public String requestApi(String api, Map<String, String> params, Map<String, String> body, List<String> servers,
                         String method) throws GraceException {

        params.put(RequestParamConstant.NAMESPACE_ID, String.valueOf(getNamespaceId()));

        if (CollectionUtils.isEmpty(servers)) {
            throw new GraceException(GraceException.INVALID_PARAM, "没有可用的服务器");
        }

        GraceException exception = new GraceException();

        Random random = new Random();
        int index = random.nextInt(servers.size());
        // 从服务列表中“随机”选出一个服务
        String server = servers.get(index);
        try {
            // 调用选出来的服务
            return callServer(api, params, body, server, method);
        } catch (GraceException e) {
            exception = e;
            if (log.isDebugEnabled()) {
                log.debug("request {} failed.", server, e);
            }
        }

        //只有当调用服务失败才会执行下面的代码
        log.error("请求: {} 失败, 服务列表: {}, 错误码: {}, 错误内容: {}", api, servers, exception.getErrCode(),
                exception.getErrMsg());

        throw new GraceException(exception.getErrCode(),
                "请求Api失败:" + api + " 服务列表(" + servers + ") 失败原因: " + exception.getMessage());
    }

    /**
     * Call server.
     *
     * @param api       api
     * @param params    parameters
     * @param body      body
     * @param curServer 被调用的服务地址（从serverList中选出来）
     * @param method    http method
     * @return result
     * @throws GraceException grace exception
     */
    public String callServer(String api, Map<String, String> params, Map<String, String> body, String curServer,
                             String method) throws GraceException {
        long start = System.currentTimeMillis();
        long end = 0;
        String namespace = params.get(RequestParamConstant.NAMESPACE_ID);
        String group = params.get(RequestParamConstant.GROUP_NAME);
        String serviceName = params.get(RequestParamConstant.SERVICE_NAME);

        String url;
        // 如果被调用的服务地址的前缀包含“http://”或“https://”
        if (curServer.startsWith(URLPrefixConstant.HTTP_PREFIX) || curServer.startsWith(URLPrefixConstant.HTTPS_PREFIX)) {
            // 拼接调用的api接口
            url = curServer + api;
        }
        // 当被调用的服务地址的前缀不包含“http://”或“https://”
        else {
            // 如果被调用的服务地址不包含端口（例如curServer为192.168.184.100）
            if (!InternetAddressUtil.containsPort(curServer)) {
                // 则自动给curServer添加一个grace的默认端口（则curServer变为192.168.184.100:8500）
                curServer = curServer + InternetAddressUtil.IP_PORT_SPLITER + serverPort;
            }
            // TODO: 2023/8/11 可以手动控制默认是http还是https
            // 拼接http前缀（默认是这个）和调用的api接口
            url = URLPrefixConstant.HTTP_PREFIX + curServer + api;
        }
        try {
            HttpRestResult<String> restResult = nacosRestTemplate
                    .exchangeForm(url, header, Query.newInstance().initParams(params), body, method, String.class);
            // 记录执行结束时间
            end = System.currentTimeMillis();

            MetricsMonitor.getNamingRequestMonitor(method, url, String.valueOf(restResult.getCode()))
                    .observe(end - start);

            if (restResult.ok()) {
                return restResult.getData();
            }
            if (HttpStatus.SC_NOT_MODIFIED == restResult.getCode()) {
                return StringUtils.EMPTY;
            }
            throw new GraceException(restResult.getCode(), restResult.getMessage());
        } catch (GraceException e) {
            log.error("[NA] failed to request", e);
            throw e;
        } catch (Exception e) {
            log.error("[NA] failed to request", e);
            throw new GraceException(GraceException.SERVER_ERROR, e);
        }
    }

    public Long getNamespaceId() {
        return namespaceId;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}
