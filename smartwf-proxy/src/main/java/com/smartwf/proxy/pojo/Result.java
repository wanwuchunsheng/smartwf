package com.smartwf.proxy.pojo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartwf.proxy.constant.Constants;

import lombok.Getter;
import lombok.Setter;


/**
 * @author WCH
 * @Date: 2019/12/19 13:26
 * @Description: 返回结果类
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class Result<T> implements Serializable {

    public static final long serialVersionUID = 42L;

    /**
     * 消息
     */
    private String msg;
    /**
     * 状态标识
     */
    private Integer code;

    /**
     * 数据
     */
    private T data;


    /**
     * 总数
     */
    private Long count;


    public Result(String msg) {
        this.msg = msg;
    }
    
    public Result(Integer code,String msg) {
        this.msg = msg;
        this.code = code;
    }
    
    public Result(Integer code,T data) {
        this.data = data;
        this.code = code;
    }
    
    public Result(Integer code,Long total,T data) {
        this.data = data;
        this.code = code;
        this.count=total;
    }


    public Result(T data) {
        this.data = data;
    }
    public Result(Long total, T data) {
        this.count = total;
        this.data = data;
    }

    public Result(Long total, T data,int code,String msg) {
        this.count = total;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }


    /**
     * 返回成功信息
     * @return
     */
    public static Result<String> successMsg() {
        return new Result(Constants.SUCCESS);
    }


    /**
     * 返回错误信息
     * @return
     */
    public static Result<String> failMsg() {
        return new Result(Constants.FAIL);
    }



    /**
     * 返回自定义信息
     * @param msg
     * @return
     */
    public static Result<String> msg(String msg) {
        return new Result(msg);
    }
    
    /**
     * 返回自定义信息
     * @param msg
     * @return
     */
    public static Result<String> msg(Integer code,String msg) {
        return new Result(code,msg);
    }


    /**
     * 返回自定义数据
     * @param data
     * @return
     */
    public static<T> Result<T> data(T data) {
        return new Result(data);
    }


    /**
     * 返回查询总数量跟自定义数据
     * @param data
     * @return
     */
    public static<T> Result<T> data(Long total, T data) {
        return new Result(total, data);
    }
    
    /**
     * 返回查询总数量跟自定义数据
     * @param data
     * @return
     */
    public static<T> Result<T> data(Integer code, T data) {
        return new Result(code, data);
    }
    
    /**
     * 返回查询总数量跟自定义数据
     * @param data
     * @return
     */
    public static<T> Result<T> data(Integer code,Long total, T data) {
        return new Result(code, total ,data);
    }
    
    
    /**
     * 返回查询总数量跟自定义数据
     * @param data
     * @return
     */
    public static<T> Result<T> data(Long total, T data,Integer code,String msg) {
        return new Result(total, data,code,msg);
    }


    
    
}
