package com.totoro.basic.framework.core.exception;

/**
 * 自定义异常
 *
 * @author Maleah
 * @create 2017-09-26 15:26
 **/
public class TotoroException extends SysException{

    public TotoroException(Throwable cause) {
        super(cause);
    }

    public TotoroException(Integer status, String msg) {
        super(status, msg);
    }
}
