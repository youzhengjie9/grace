package com.grace.security.annotation;

import java.lang.annotation.*;


/**
 * 作用:
 * (1)允许所有人访问,服务调用不需要鉴权（只要加了这个@PermitAll注解就会生效）
 * (2)控制接口是否只能通过openfeign所访问,如果在浏览器通过访问gateway的方式访问该接口则会访问失败。（onlyFeignCall属性设置为true）
 *
 * @author youzhengjie
 * @date 2023/08/25 00:59:22
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermitAll {


	/**
	 * 作用: 该接口是否只能通过openfeign所访问（如果在浏览器通过访问gateway的方式访问该接口则会访问失败。）
	 * <p>
	 * 如果我们不希望这个url可以直接被外网调用，仅能在Feign服务中调用则设置为true
	 * <p>
	 * 使用说明:
	 * 如果该PermitAll注解的onlyFeignCall属性值设置了为true,但是名为only_feign_call的请求头内容为空,则禁止访问,
	 * 也就是说要是想访问onlyFeignCall属性值设置了为true的接口,必须要放上一个名为only_feign_call的请求头
	 * <p>
	 * >>>>>>>>>>>>> 如何使用feign给接口传请求头: <<<<<<<<<<<<<<<<<<<<<,,
	 * 1：接口定义：
	 *     @ PermitAll <br/>
	 *     @ GetMapping("/getClientById/{clientId}") <br/>
	 *     @ ApiOperation("通过客户端id查询Oauth2客户端信息") <br/>
	 *    public ResponseResult getClientById(@PathVariable String clientId) { <br/>
	 * 		SysOauth2Client sysOauth2Client = sysOauth2ClientService.getOne( <br/>
	 * 				Wrappers.<SysOauth2Client>lambdaQuery().eq(SysOauth2Client::getClientId, clientId), false); <br/>
	 * 		String json = JSON.toJSONString(sysOauth2Client); <br/>
	 * 		return ResponseResult.ok(json); <br/>
	 *    } <br/>
	 * <p>
	 * 2: 定义feign:(并通过一个参数来接收请求头（注意！）) <br/>
	 *   @ GetMapping(value = "/getClientById/{clientId}") <br/>
	 *   public ResponseResult getClientById(@PathVariable("clientId") String clientId, <br/>
	 *                                         @ RequestHeader(Oauth2Constant.ONLY_FEIGN_CALL_HEADER_NAME)
	 *                                         String onlyFeignCallHeader); <br/>
	 * <p>
	 * 3：使用这个feign:(Oauth2Constant.ONLY_FEIGN_CALL_HEADER_NAME请求头只需要传一个空字符串即可) <br/>
	 * ResponseResult responseResult = sysOauth2ClientFeign.getClientById(clientId,"123"); <br/>
	 *
	 * <p>
	 * >> 注意: 错误使用（如果不传请求头）则会返回这个错误： [{"code":401,"msg":"令牌过期","data":"Full authentication is required to access this resource"}]
	 *
	 * @return boolean
	 */
	boolean onlyFeignCall() default true;

	/**
	 * 需要特殊判空的字段(预留)
	 */
	String[] field() default {};

}
