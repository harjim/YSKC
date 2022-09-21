package com.yskc.ms.models;

import com.yskc.ms.config.Constant;

import java.math.BigDecimal;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-18 14:09
 * @Description: 获取客户汇总
 */
public class CustomerTotalModel {

    private Integer customerCount;

    private Integer rdCount;

    private BigDecimal rdFundsSum;

    private String branchName;

    public static CustomerTotalModel build(String branchName) {
        CustomerTotalModel model = new CustomerTotalModel();
        model.customerCount = 0;
        model.rdCount = 0;
        model.rdFundsSum = BigDecimal.ZERO;
        model.branchName = branchName;
        return model;
    }

    public Integer getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(Integer customerCount) {
        this.customerCount = customerCount;
    }

    public Integer getRdCount() {
        return rdCount;
    }

    public void setRdCount(Integer rdCount) {
        this.rdCount = rdCount;
    }

    public BigDecimal getRdFundsSum() {
        return rdFundsSum;
    }

    public void setRdFundsSum(BigDecimal rdFundsSum) {
        this.rdFundsSum = rdFundsSum;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void addRdCount(Integer rdCount) {
        if (rdCount != null) {
            this.rdCount += rdCount;
        }
    }

    public void addRdFundsSum(BigDecimal rdFunds) {
        if (rdFunds != null) {
            this.rdFundsSum = this.rdFundsSum.add(rdFunds);
        }
    }

    @Override
    public String toString() {
        return "CustomerTotalModel{" +
                "customerCount=" + customerCount +
                ", rdCount=" + rdCount +
                ", rdFundsSum=" + rdFundsSum +
                ", branchName='" + branchName + '\'' +
                '}';
    }

    public void rdFundsSumToBit() {
        if (rdFundsSum != null) {
            rdFundsSum = rdFundsSum.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
