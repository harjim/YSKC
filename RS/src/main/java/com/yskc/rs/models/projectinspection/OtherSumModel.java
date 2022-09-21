package com.yskc.rs.models.projectinspection;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectinspection
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-27 15:05
 * @Description: 其他费用sum
 */
public class OtherSumModel {

    private BigDecimal book;
    private BigDecimal rdProduction;
    private BigDecimal copyright;
    private BigDecimal benefits;
    private BigDecimal other;

    public BigDecimal getBook() {
        return book;
    }

    public void setBook(BigDecimal book) {
        this.book = book;
    }

    public BigDecimal getRdProduction() {
        return rdProduction;
    }

    public void setRdProduction(BigDecimal rdProduction) {
        this.rdProduction = rdProduction;
    }

    public BigDecimal getCopyright() {
        return copyright;
    }

    public void setCopyright(BigDecimal copyright) {
        this.copyright = copyright;
    }

    public BigDecimal getBenefits() {
        return benefits;
    }

    public void setBenefits(BigDecimal benefits) {
        this.benefits = benefits;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }
}
