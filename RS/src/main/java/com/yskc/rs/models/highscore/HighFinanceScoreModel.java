package com.yskc.rs.models.highscore;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2021/11/4 10:51
 * @Description:
 * @author: hsx
 */
public class HighFinanceScoreModel implements Serializable {

    private Integer id;

    private Integer year;

    private BigDecimal salesYear1;      // 第一年销售额(万元)

    private BigDecimal salesYear2;      // 第二年销售额(万元)

    private BigDecimal salesYear3;      // 第三年销售额(万元)

    private BigDecimal totalSales;      // 近三年销售收入(万元)

    private BigDecimal nAVYear1;        // 第一年净资产(万元)

    private BigDecimal nAVYear2;        // 第二年净资产(万元)

    private BigDecimal nAVYear3;        // 第三年净资产(万元)

    private BigDecimal income;          // 近一年企业总收入(万元)

    private Integer nAVScore;           // 净资产增长率得分

    private Integer salesScore;         // 销售收入增长率得分

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getSalesYear1() {
        return salesYear1;
    }

    public void setSalesYear1(BigDecimal salesYear1) {
        this.salesYear1 = salesYear1;
    }

    public BigDecimal getSalesYear2() {
        return salesYear2;
    }

    public void setSalesYear2(BigDecimal salesYear2) {
        this.salesYear2 = salesYear2;
    }

    public BigDecimal getSalesYear3() {
        return salesYear3;
    }

    public void setSalesYear3(BigDecimal salesYear3) {
        this.salesYear3 = salesYear3;
    }

    public BigDecimal getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getnAVYear1() {
        return nAVYear1;
    }

    public void setnAVYear1(BigDecimal nAVYear1) {
        this.nAVYear1 = nAVYear1;
    }

    public BigDecimal getnAVYear2() {
        return nAVYear2;
    }

    public void setnAVYear2(BigDecimal nAVYear2) {
        this.nAVYear2 = nAVYear2;
    }

    public BigDecimal getnAVYear3() {
        return nAVYear3;
    }

    public void setnAVYear3(BigDecimal nAVYear3) {
        this.nAVYear3 = nAVYear3;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Integer getnAVScore() {
        return nAVScore;
    }

    public void setnAVScore(Integer nAVScore) {
        this.nAVScore = nAVScore;
    }

    public Integer getSalesScore() {
        return salesScore;
    }

    public void setSalesScore(Integer salesScore) {
        this.salesScore = salesScore;
    }
}
