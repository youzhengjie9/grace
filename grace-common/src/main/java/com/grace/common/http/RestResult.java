package com.grace.common.http;

import com.grace.common.http.param.Header;
import com.grace.common.utils.Result;

/**
 * GraceRestTemplate的响应结果
 *
 * @author mai.jh
 */
public class RestResult<T> extends Result<T> {
    
    private static final long serialVersionUID = 3766947816720175947L;
    
    private Header header;
    
    public RestResult() {
    }
    
    public RestResult(Header header, int code, T data, String message) {
        super(code, message, data);
        this.header = header;
    }
    
    public Header getHeader() {
        return header;
    }
    
    public void setHeader(Header header) {
        this.header = header;
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
