package com.totoro.basic.framework.model.rest;

import com.totoro.basic.framework.model.dto.BaseResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 封装返回单值数据
 *
 * @author Maleah
 * @create 2017-09-27 17:43
 **/
@Data
public class SingleResponse<RKEY extends Serializable> extends BaseResponse {

    @ApiModelProperty(value = "返回值")
    private RKEY Value;

    public SingleResponse(RKEY value) {
        this.Value = value;
    }

    public SingleResponse() {

    }
}
