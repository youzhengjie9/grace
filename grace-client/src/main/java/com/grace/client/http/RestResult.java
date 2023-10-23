package com.grace.client.http;

import com.grace.client.http.param.ResponseHeader;
import com.grace.common.utils.Result;

/**
 * GraceRestTemplate的响应结果
 *
 * @author mai.jh
 */
public class RestResult<T> extends Result<T> {
    
    private static final long serialVersionUID = 3766947816720175947L;

    /**
     * 响应头
     */
    private ResponseHeader responseHeader;
    
    public RestResult() {
    }
    
    public ResponseHeader getResponseHeader() {
        return responseHeader;
    }
    
    public void setResponseHeader(ResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    /**
     * 直接将Result<T>对象放进这个RestResult<T>对象中
     *
     * @param result 结果
     */
    public void setResult(Result<T> result){
        super.setCode(result.getCode());
        super.setMsg(result.getMsg());
        super.setData(result.getData());
    }

}
