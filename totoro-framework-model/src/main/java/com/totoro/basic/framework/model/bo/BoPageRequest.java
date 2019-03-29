package com.totoro.basic.framework.model.bo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 业务层分页查询请求基类
 *
 * @author Maleah
 * @create 2017-09-28 11:00
 **/
public class BoPageRequest<T extends Serializable> implements Serializable{
    private Integer pageNum = 1;
    private Integer pageSize = 20;
    private T paramData;
    /**
     * 起始标量
     */
    private Integer start = 0 ;
    /**
     * 结束标量
     */
    private Integer end = 1;

    public BoPageRequest(){}

    public BoPageRequest(int pageNum, int pageSize){
        pageNum = pageNum <= 0 ? 1 : pageNum;
        pageSize = pageSize <= 0 ? 1 : pageSize;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        pageNum = pageNum <= 0 ? 1 : pageNum;
        this.pageNum = pageNum;
    }

    public Integer getPageSize(){
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        pageSize = pageSize <= 0 ? 1 : pageSize;
        this.pageSize = pageSize;
    }
    public T getParamData() {
        return paramData;
    }

    public void setParamData(T paramData) {
        this.paramData = paramData;
    }


    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
