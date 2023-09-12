package com.grace.security.handler;

import com.alibaba.fastjson2.JSON;
import com.grace.common.enums.ResultType;
import com.grace.common.utils.Result;
import com.grace.common.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义权限不足处理器(权限不足时调用)
 *
 * @author youzhengjie
 * @date 2023/09/07 12:20:21
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        log.error("request="+request);
        log.error("accessDeniedException="+accessDeniedException);

        Result<Object> result = Result.build(ResultType.NOT_PERMISSION);
        String jsonString = JSON.toJSONString(result);
        WebUtil.writeJsonString(response,jsonString);
    }
}
