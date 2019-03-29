package com.totoro.basic.framework.rest.signature;

public enum SessionVerifyMode {

    /**
     * 只验证token
     */
    ACCESS_TOKEN(1),

    /**
     * 公开服务
     */
    OPEN_API(2),
    /**
     * 管理后台专用
     */
    BACKGROUND(4),

    /**
     * 智慧城市专用
     */
    CITY(8),

    /**
     * 支付宝回调验签
     */
    ALIPAY(32),

    /**
     * 微信回调验签
     */
    WECHATPAY(64);

    private Integer code;

    private  SessionVerifyMode(int code){
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
