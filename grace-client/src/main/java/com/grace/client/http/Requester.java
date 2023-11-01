package com.grace.client.http;

import com.grace.client.http.client.GraceRestTemplate;
import com.grace.client.http.factory.GraceRestTemplateFactory;
import com.grace.client.http.param.RequestHeader;
import com.grace.client.http.param.RequestParam;
import com.grace.client.properties.GraceRegistryProperties;
import com.grace.common.constant.Constants;
import com.grace.common.constant.URLPrefixConstants;
import com.grace.common.utils.InternetAddressUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * 请求发送者
 *
 * @author youzhengjie
 * @date 2023/10/26 21:20:50
 */
public class Requester {

    private static final Logger log = LoggerFactory.getLogger(Requester.class);

    /**
     * 默认的控制台端口
     */
    private static final int DEFAULT_CONSOLE_PORT = 8848;

    private final GraceRestTemplate graceRestTemplate = GraceRestTemplateFactory.getInstance().createGraceRestTemplate();

    /**
     * grace控制台地址
     */
    private final String graceConsoleAddress;

    public Requester(String graceConsoleAddress) {
        this.graceConsoleAddress = graceConsoleAddress;
    }

    public Requester(Properties properties) {
        this.graceConsoleAddress = properties.getProperty("graceConsoleAddress");
    }

    /**
     * 请求api接口（推荐使用这个方法）
     *
     * @param api       api接口地址
     * @param requestMethod    请求方法
     * @param requestHeaderMap       请求头（没有就写null）
     * @param requestParamMap    请求参数（没有就写null）
     * @param requestBodyMap      请求体（没有就写null）
     * @return result
     */
    public RestResult<Object> requestApi(String api, String requestMethod ,Map<String, String> requestHeaderMap,
                                         Map<String, String> requestParamMap, Map<String, String> requestBodyMap) {
        // 如果grace控制台地址为空
        if (StringUtils.isBlank(graceConsoleAddress)) {
            throw new RuntimeException("grace控制台地址列表为空");
        }
        try {
            // 调用刚刚选出来的grace控制台地址所在的服务器的controller接口
            return callServer(api, requestMethod , requestHeaderMap, requestParamMap,
                    requestBodyMap,graceConsoleAddress);
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
     * @param requestHeaderMap       请求头（没有就写null）
     * @param requestParamMap       请求参数（没有就写null）
     * @param requestBodyMap         请求体（没有就写null）
     * @param graceConsoleAddress 一个grace控制台地址（从graceConsoleAddressList中选出来）
     * @return result
     */
    public RestResult<Object> callServer(String api, String requestMethod , Map<String, String> requestHeaderMap,
                                         Map<String, String> requestParamMap, Map<String, String> requestBodyMap ,
                                         String graceConsoleAddress) {
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
            if(requestHeaderMap != null && requestHeaderMap.size() > 0) {
                requestHeader.addAll(requestHeaderMap);
            }
            // 请求参数
            RequestParam requestParam = RequestParam.newInstance();
            if(requestParamMap != null && requestParamMap.size() > 0) {
                requestParam.addRequestParams(requestParamMap);
            }
            // 发送请求
            RestResult<Object> restResult = graceRestTemplate.exchange(url, requestMethod, requestHeader, requestParam,
                    requestBodyMap, Object.class);
            // TODO: 2023/10/24 记录结束时间
            end = System.currentTimeMillis();
            return restResult;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
