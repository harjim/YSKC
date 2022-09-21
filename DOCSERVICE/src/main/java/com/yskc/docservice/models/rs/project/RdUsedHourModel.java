package com.yskc.docservice.models.rs.project;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 14:38
 * @Description: 项目使用工时情况
 */
public class RdUsedHourModel implements Serializable {
    private BigDecimal rdHour;
    private String rdTitle;
    private String key;
    private BigDecimal rdAmount;
    private BigDecimal rdInsuranceFund;
    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public BigDecimal getRdInsuranceFund() {
        return rdInsuranceFund;
    }

    public void setRdInsuranceFund(BigDecimal rdInsuranceFund) {
        this.rdInsuranceFund = rdInsuranceFund;
    }
}
