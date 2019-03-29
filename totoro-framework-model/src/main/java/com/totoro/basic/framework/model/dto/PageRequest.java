package com.totoro.basic.framework.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 业务层分页查询
 *
 * @author Maleah
 * @create 2017-09-27 18:02
 **/
@Data
public class PageRequest<T extends BaseRequest> extends BasePageRequest {

    @ApiModelProperty(value = "封装查询条件")
    private T param;

}
