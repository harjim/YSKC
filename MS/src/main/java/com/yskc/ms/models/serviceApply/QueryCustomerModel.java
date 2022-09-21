package com.yskc.ms.models.serviceApply;

import java.util.List;

/**
 * @program: ms
 * @description: 查找公司
 * @author: cyj
 * @create: 2022-08-12 10:45
 **/
public class QueryCustomerModel {
    private List<Integer> customerIds;
    private String companyName;

    public List<Integer> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
