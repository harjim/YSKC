package com.yskc.ms.chuanyun.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yskc.ms.entity.ms.CyCustomer;

import java.util.List;

public class LoadBizObjectsData<T> {
    @JsonProperty(value = "BizObjectArray")
    private List<T>  bizObjectArray;
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
