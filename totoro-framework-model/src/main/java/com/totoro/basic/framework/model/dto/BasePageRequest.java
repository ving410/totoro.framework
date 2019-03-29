package com.totoro.basic.framework.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页请求基类
 *
 * @author Maleah
 * @create 2017-09-27 14:20
 **/
@ApiModel(value = "请求实体")
public class BasePageRequest extends BaseRequest {

    @ApiModelProperty(value = "页码")
    private int pageNo;

    @ApiModelProperty(value = "每页条数")
    private int limit;

    public Integer getPageNo() {
        return (this.pageNo <= 0) ? 1 : this.pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo <= 0 ? 1 : pageNo;
    }

    public Integer getLimit() {
        return (this.limit <= 0) ? 1 : this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit <= 0 ? 1 : limit;
    }
}
