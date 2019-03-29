package com.totoro.basic.framework.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页返回体基类
 *
 * @author Maleah
 * @create 2017-09-27 14:25
 **/
@Data
public class PageResponse<T extends BaseResponse> extends BaseResponse {
    @ApiModelProperty(value = "记录总数")
    private Long total;

    @ApiModelProperty(value = "数据集合")
    private List<T> items;

    @ApiModelProperty(value = "页码")
    private Integer pageNo ;

    @ApiModelProperty(value = "页面条数")
    private Integer limit ;

    @ApiModelProperty(value = "是否有下一页")
    private Boolean morePage;
}
