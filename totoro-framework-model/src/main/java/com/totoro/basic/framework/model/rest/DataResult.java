package com.totoro.basic.framework.model.rest;

import com.totoro.basic.framework.model.dto.BaseResponse;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * API返回体的数据格式
 *
 * @author Maleah
 * @create 2017-09-27 14:31
 **/
public class DataResult<T extends BaseResponse> implements Serializable{

    @ApiModelProperty(value = "状态码")
    private int status;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回值")
    private T data;

    @ApiModelProperty(value = "时间戳")
    private Date timestamp = new Date();

    public DataResult() {
    }

    public DataResult(int status, String message, T data) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.setTimestamp(new Date());
    }

    public DataResult(T data) {
        this(0,null,data);
        //this.data = data;
    }

    public T getData() {
        return (T)data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public static <V extends BaseResponse> DataResult<V> fail(String message) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(-1);
        result.setMessage(message);
        return result;
    }

    public static <V extends BaseResponse> DataResult<V> fail(String message, V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(-1);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <V extends BaseResponse> DataResult<V> fail(int status, String message, V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(status);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <V extends BaseResponse> DataResult<V> ok() {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(0);
        result.setMessage("success");
        return result;
    }


    public static <V extends BaseResponse> DataResult<V> ok(V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(0);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static <V extends BaseResponse> DataResult<V> ok(String message, V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(0);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <V extends BaseResponse> DataResult<V> ok(Integer status,String message, V data) {
        DataResult<V> result = new DataResult<V>();
        result.setStatus(status);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    @Override
    public String toString() {
        return "DataResult{" + "status =" + status + ", message =" + message + ", data =" + data + ", timestamp = "+ timestamp + "}";
    }
}
