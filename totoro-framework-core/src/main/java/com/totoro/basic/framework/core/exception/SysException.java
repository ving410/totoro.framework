package com.totoro.basic.framework.core.exception;

/**
 * 系统异常。框架的顶级异常
 *
 * @author Maleah
 * @create 2017-09-26 15:20
 **/
public class SysException extends RuntimeException{

    private int errorCode;

    public SysException(Throwable cause) {
        super(cause);
    }
    public SysException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
    public SysException(int errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
