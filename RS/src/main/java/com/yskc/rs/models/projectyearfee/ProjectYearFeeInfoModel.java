package com.yskc.rs.models.projectyearfee;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectyearfee
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-07 11:25
 * @Description: 优惠明细表信息
 */
public class ProjectYearFeeInfoModel {
    private BigDecimal k46 = BigDecimal.ZERO;
    private BigDecimal k48 = BigDecimal.ZERO;
    private BigDecimal k49 = BigDecimal.ZERO;
    private BigDecimal rdRatio = new BigDecimal("0.75");

    public BigDecimal getK46() {
        return k46;
    }

    public void setK46(BigDecimal k46) {
        this.k46 = k46;
    }

    public BigDecimal getK48() {
        return k48;
    }

    public void setK48(BigDecimal k48) {
        this.k48 = k48;
    }

    public BigDecimal getK49() {
        return k49;
    }

    public void setK49(BigDecimal k49) {
        this.k49 = k49;
    }

    public BigDecimal getRdRatio() {
        return rdRatio;
    }

    public void setRdRatio(BigDecimal rdRatio) {
        this.rdRatio = rdRatio;
    }
}
