package com.totoro.basic.framework.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 传输对象基类
 *
 * @author Maleah
 * @create 2017-09-27 14:11
 **/

@ApiModel(value = "请求实体")
public class BaseRequest implements Serializable {

    /**
     * 请求版本号
     */

    @ApiModelProperty(value = "请求版本号")
    private String version;

    /**
     * 请求来源（web，ios，android……）
     */
    @ApiModelProperty(value = "请求来源")
    private String requestSource;

    /**
     * 请求唯一标识
     */
    @ApiModelProperty(value = "请求唯一标识")
    private String requestId;


    public String getVersion() {
        if(version==null||version.equals(""))return "1.0";
        String regEx = "v?(([A-Z]*)[0-9]+([.]{1}[0-9]+){0,1}$)";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(version);
        boolean rs = matcher.matches();
        return rs? matcher.group(1):"1.0";
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
