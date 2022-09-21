package com.xxl.job.executor.models.h3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LoadBizObjectsData<T> {
    @JsonProperty(value = "BizObjectArray")
    private List<T> bizObjectArray;
    @JsonProperty(value = "TotalCount")
    private Integer totalCount ;

    public List<T> getBizObjectArray() {
        return bizObjectArray;
    }

    public void setBizObjectArray(List<T> bizObjectArray) {
        this.bizObjectArray = bizObjectArray;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
