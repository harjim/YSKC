package com.xxl.job.executor.models.district;

import java.math.BigDecimal;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.district
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-17 09:16
 * @Description: 归集汇总model
 */
public class RsSummaryModel {

    private Integer customerId;

    private Integer year;

    private BigDecimal rdFunds;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }
}
