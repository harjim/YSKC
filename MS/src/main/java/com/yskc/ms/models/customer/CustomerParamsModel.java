package com.yskc.ms.models.customer;

import java.io.Serializable;
import java.util.List;

public class CustomerParamsModel implements Serializable {

    private Integer groupId;

    private List<Integer> ids;

    private Integer companyId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
