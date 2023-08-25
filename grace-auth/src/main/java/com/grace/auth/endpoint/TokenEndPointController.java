package com.grace.auth.endpoint;

import cn.hutool.core.util.StrUtil;
import com.petal.auth.support.handler.CustomAuthenticationFailureHandler;
import com.petal.common.base.constant.OAuth2ErrorCodeConstant;
import com.petal.common.base.entity.SysOauth2Client;
import com.petal.common.base.enums.ResponseType;
import com.petal.common.base.utils.ResponseResult;
import com.petal.common.base.utils.SpringContextHolder;
import com.petal.common.openfeign.feign.SysOauth2ClientFeign;
import com.petal.common.security.annotation.PermitAll;
import com.petal.common.security.exception.OAuthClientException;
import com.petal.common.security.utils.OAuth2EndpointUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;
import java.util.Set;

;

/**
 * Token的端点控制器
 *
 * @author youzhengjie
 * @date 2023/04/22 00:25:44
 */
@Api("Token的端点控制器")
@Slf4j
@RestController
@RequestMapping("/token")
public class TokenEndPointController {

    private final HttpMessageConverter<OAuth2AccessTokenResponse> accessTokenHttpResponseConverter =
            new OAuth2AccessTokenResponseHttpMessageConverter();

    private final AuthenticationFailureHandler authenticationFailureHandler =
            new CustomAuthenticationFailureHandler();

    private OAuth2AuthorizationService authorizationService;

    private SysOauth2ClientFeign sysOauth2ClientFeign;

    @Autowired
    public void setAuthorizationService(OAuth2AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Autowired
    public void setSysOauth2ClientFeign(SysOauth2ClientFeign sysOauth2ClientFeign) {
        this.sysOauth2ClientFeign = sysOauth2ClientFeign;
    }

    /**
     * 认证页面
     *
     * @param modelAndView
     * @param error 表单登录失败处理回调的错误信息
     * @return ModelAndView
     */
    @GetMapping("/login")
    @ApiOperation("认证页面")
    public ModelAndView login(ModelAndView modelAndView, @RequestParam(required = false) String error) {
        modelAndView.setViewName("login");
        modelAndView.addObject("error", error);
        return modelAndView;
    }

    /**
     * 授权
     *
     * @param principal    主要
     * @param modelAndView 模型和视图
     * @param clientId     客户机id
     * @param scope        范围
     * @param state        状态
     * @return {@link ModelAndView}
     */
    @GetMapping("/consent")
    @ApiOperation("授权")
    public ModelAndView consent(Principal principal, ModelAndView modelAndView,
                                @RequestParam(OAuth2ParameterNames.CLIENT_ID) String clientId,
                                @RequestParam(OAuth2ParameterNames.SCOPE) String scope,
                                @RequestParam(OAuth2ParameterNames.STATE) String state) {

        ResponseResult responseResult = sysOauth2ClientFeign.getClientById(clientId,"123");

        if(responseResult != null){
            Object data = responseResult.getData();
            if(data != null){
                SysOauth2Client sysOauth2Client = (SysOauth2Client) data;
                Set<String> authorizedScopes = org.springframework.util.StringUtils.commaDelimitedListToSet(sysOauth2Client.getScope());
                modelAndView.addObject("clientId", clientId);
                modelAndView.addObject("state", state);
                modelAndView.addObject("scopeList", authorizedScopes);
                modelAndView.addObject("principalName", principal.getName());

                modelAndView.setViewName("consent");
                return modelAndView;
            }
            else {
                throw new OAuthClientException("clientId 不合法");
            }
        }else {
            throw new OAuthClientException("clientId 不合法");
        }
    }

    /**
     * 检查Token
     *
     * @param accessToken 访问令牌
     */
    @SneakyThrows
    @GetMapping("/checkToken")
    @ApiOperation("检查accessToken")
    public void checkToken(String accessToken, HttpServletResponse response, HttpServletRequest request) {
        ServletServerHttpResponse httpResponse = new ServletServerHttpResponse(response);

        if (StrUtil.isBlank(accessToken)) {
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
            //调用认证失败处理
            this.authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new InvalidBearerTokenException(OAuth2ErrorCodeConstant.ACCESS_TOKEN_MISSING));
            return;
        }
        OAuth2Authorization authorization = authorizationService.findByToken(accessToken, OAuth2TokenType.ACCESS_TOKEN);

        // 如果accessToken不存在
        if (authorization == null || authorization.getAccessToken() == null) {
            //调用认证失败处理
            this.authenticationFailureHandler.onAuthenticationFailure(request, response,
                    new InvalidBearerTokenException(OAuth2ErrorCodeConstant.ACCESS_TOKEN_NOT_EXIST));
            return;
        }

        Map<String, Object> claims = authorization.getAccessToken().getClaims();
        OAuth2AccessTokenResponse sendAccessTokenResponse = OAuth2EndpointUtil.sendAccessTokenResponse(authorization,
                claims);
        this.accessTokenHttpResponseConverter.write(sendAccessTokenResponse, MediaType.APPLICATION_JSON, httpResponse);
    }

    /**
     * 退出登录
     * <p>
     * 只要携带了token调用了这个退出登录接口，那么该token将会过期，无法在通过这个token去访问其他接口，必须重新登录
     * @param authHeader Authorization
     */
    @DeleteMapping("/logout")
    @ApiOperation("退出登录")
    public ResponseResult<Boolean> logout(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader)
    {
        //如果authHeader为空则说明早就退出成功了,直接返回即可
        if (StringUtils.isBlank(authHeader)) {
            return ResponseResult.build(ResponseType.LOGOUT_SUCCESS);
        }
        //替换掉Token中的Bearer，取出真正的Token
        String tokenValue = authHeader.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StrUtil.EMPTY).trim();
        return removeToken(tokenValue);
    }

    /**
     * 移除token
     *
     * @param token token
     */
    @PermitAll
    @DeleteMapping("/{token}")
    @ApiOperation("移除token")
    public ResponseResult<Boolean> removeToken(@PathVariable("token") String token) {
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null) {
            return ResponseResult.build(ResponseType.LOGOUT_SUCCESS);
        }
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (accessToken == null || StrUtil.isBlank(accessToken.getToken().getTokenValue())) {
            return ResponseResult.build(ResponseType.LOGOUT_SUCCESS);
        }
        // TODO: 2023/5/9 清空用户信息

        // 清空accessToken
        authorizationService.remove(authorization);
        // 处理自定义退出事件，保存相关日志
        SpringContextHolder.publishEvent(new LogoutSuccessEvent(new PreAuthenticatedAuthenticationToken(
                authorization.getPrincipalName(), authorization.getRegisteredClientId())));
        return ResponseResult.build(ResponseType.LOGOUT_SUCCESS);
    }


}
