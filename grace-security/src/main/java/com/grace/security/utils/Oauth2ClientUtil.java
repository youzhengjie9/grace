package com.grace.security.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.map.MapUtil;
import com.grace.common.constant.Oauth2Constants;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * oauth2客户端工具类
 *
 * @author youzhengjie
 * @date 2023/08/25 17:05:02
 */
public final class Oauth2ClientUtil {

    /**
     * Authorization请求头
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 获取clientId
     * <p>
     * clientId所在的请求头名称: Authorization
     * clientId所在的请求头的内容格式: Basic base64加密过的clientId （例如 Basic cGlnOnBpZw==）
     * @param request 请求
     * @return {@link String}
     */
    public static String getClientId(HttpServletRequest request){

        String header = request.getHeader(AUTHORIZATION_HEADER);
        if (header == null) {
            return null;
        } else {
            //方法移除请求头两端的空白
            header = header.trim();
            // 如果这个请求头不是以Basic开头则返回空
            if (!StringUtils.startsWithIgnoreCase(header, "Basic")) {
                return null;
            }
            // 如果这个请求头的内容只有Basic则说明没有传递clientid,直接抛出异常
            else if (header.equalsIgnoreCase("Basic")) {
                throw new BadCredentialsException("在"+AUTHORIZATION_HEADER+"请求头中没有传递base64加密过的clientId");
            }
            // 到了这里说明传递了clientId
            else {
                // 获取base64加密过的clientId数组。将Basic cGlnOnBpZw== 截取成 cGlnOnBpZw==
                byte[] base64ClientIdArray = header.substring(6).getBytes(StandardCharsets.UTF_8);
                // 解密base64加密过的clientId数组
                byte[] clientIdArray = Base64.decode(base64ClientIdArray);
                //返回clientId
                return new String(clientIdArray,StandardCharsets.UTF_8);
            }

        }

    }

    /**
     * 获取客户端id
     *
     * @return clientId
     */
    public static String getClientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal auth2Authentication = (OAuth2AuthenticatedPrincipal) principal;
            return MapUtil.getStr(auth2Authentication.getAttributes(), Oauth2Constants.CLIENT_ID);
        }
        return null;
    }


}
