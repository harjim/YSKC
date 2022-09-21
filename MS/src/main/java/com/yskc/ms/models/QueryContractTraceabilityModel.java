package com.yskc.ms.models;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/6/4 15:39
 * description:
 */
public class QueryContractTraceabilityModel extends PageParams implements Serializable {
    private String contractNumber;
    private String customerName;

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
