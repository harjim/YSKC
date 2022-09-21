package com.yskc.rs.models.projectinspection;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectinspection
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-10 08:44
 * @Description: 统计摊销费用model
 */
public class SumProjectAmortizationModel {

    /**
     * 软件
     */

    private BigDecimal softwareSum;
    /**
     * 专利
     */
    private BigDecimal patentSum;

    /**
     * 其他摊销
     */
    private BigDecimal otherSum;

    public BigDecimal getPatentSum() {
        return patentSum;
    }

    public void setPatentSum(BigDecimal patentSum) {
        this.patentSum = patentSum;
    }

    public BigDecimal getSoftwareSum() {
        return softwareSum;
    }

    public void setSoftwareSum(BigDecimal softwareSum) {
        this.softwareSum = softwareSum;
    }

    public BigDecimal getOtherSum() {
        return otherSum;
    }

    public void setOtherSum(BigDecimal otherSum) {
        this.otherSum = otherSum;
    }
}
