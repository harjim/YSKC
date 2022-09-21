package com.yskc.rs.models.rdAgg;

import java.math.BigDecimal;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/rs/models/rdAgg
 * @Author: hm
 * @CreateTime: 2022/8/19
 * @Description: 研发费用总表合计返回模板
 */
public class AggSummaryModel {
    private String rdTitle;
    private BigDecimal[] funds;
    private BigDecimal sum;
    private String costTitle;

    public AggSummaryModel() {
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal[] getFunds() {
        return funds;
    }

    public void setFunds(BigDecimal[] funds) {
        this.funds = funds;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getCostTitle() {
        return costTitle;
    }

    public void setCostTitle(String costTitle) {
        this.costTitle = costTitle;
    }
}
