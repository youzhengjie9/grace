package com.grace.core.utils;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.grace.core.enums.ResponseType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 封装统一响应结果
 *
 * @author youzhengjie
 * @date 2023-06-15 22:49:18
 */
@JsonInclude(JsonInclude.Include.NON_NULL) //为null的字段不进行序列化
@Data
@Accessors(chain = true)
public class ResponseResult<T> {

    /**
     * 响应状态码
     */
    private Integer code;

    /**
     * 响应状态码对应的信息提示
     */
    private String msg;

    /**
     * 返回给前端的数据
     */
    private T data;

    public ResponseResult() {

    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg=msg;
        this.data = data;
    }

    /**
     * 构建ResponseResult对象
     *
     * @param code 代码
     * @param msg  味精
     * @param data 数据
     * @return {@link ResponseResult}<{@link D}>
     */
    public static<D> ResponseResult<D> build(Integer code,String msg,D data){

        return new ResponseResult<D>()
                .setCode(code)
                .setMsg(msg)
                .setData(data);
    }
    public static<D> ResponseResult<D> build(ResponseType responseType){

        return new ResponseResult<D>()
                .setCode(responseType.getCode())
                .setMsg(responseType.getMessage())
                .setData(null);
    }
    public static<D> ResponseResult<D> build(ResponseType responseType,D data){

        return new ResponseResult<D>()
                .setCode(responseType.getCode())
                .setMsg(responseType.getMessage())
                .setData(data);
    }
    public static<D> ResponseResult<D> ok(){

        return new ResponseResult<D>()
                .setCode(ResponseType.SUCCESS.getCode())
                .setMsg(ResponseType.SUCCESS.getMessage())
                .setData(null);
    }
    public static<D> ResponseResult<D> ok(D data){

        return new ResponseResult<D>()
                .setCode(ResponseType.SUCCESS.getCode())
                .setMsg(ResponseType.SUCCESS.getMessage())
                .setData(data);
    }

    public static<D> ResponseResult<D> ok(String msg,D data){

        return new ResponseResult<D>()
                .setCode(ResponseType.SUCCESS.getCode())
                .setMsg(msg)
                .setData(data);
    }


    public static<D> ResponseResult<D> fail(D data){

        return new ResponseResult<D>()
                .setCode(ResponseType.ERROR.getCode())
                .setMsg(ResponseType.ERROR.getMessage())
                .setData(data);
    }

    public static<D> ResponseResult<D> fail(String msg,D data){

        return new ResponseResult<D>()
                .setCode(ResponseType.ERROR.getCode())
                .setMsg(msg)
                .setData(data);
    }

    /**
     * 把当前对象序列化成JSON
     *
     * @return {@link String}
     */
    public String toJSONString(){
        return JSON.toJSONString(this);
    }

}
