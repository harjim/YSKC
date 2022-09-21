package com.yskc.rs.entity.hightech;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.tech.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2021/11/4 10:06
 * @Description:财务评分实体类
 * @author: hsx
 */
@TableName("high_finance_score")
public class HighFinanceScoreEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 408862042733011991L;

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer year;

    private BigDecimal salesYear1;      // 第一年销售额(万元)

    private BigDecimal salesYear2;      // 第二年销售额(万元)

    private BigDecimal salesYear3;      // 第三年销售额(万元)

    private BigDecimal totalSales;      // 近三年销售收入(万元)

    @TableField("NAVYear1")
    private BigDecimal nAVYear1;        // 第一年净资产(万元)

    @TableField("NAVYear2")
    private BigDecimal nAVYear2;        // 第二年净资产(万元)

    @TableField("NAVYear3")
    private BigDecimal nAVYear3;        // 第三年净资产(万元)

    private BigDecimal income;          // 近一年企业总收入(万元)

    @TableField("NAVScore")
    private Integer nAVScore;           // 净资产增长率得分

    private Integer salesScore;         // 销售收入增长率得分

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getSalesYear1() {
        return null == salesYear1?BigDecimal.ZERO:salesYear1;
    }

    public void setSalesYear1(BigDecimal salesYear1) {
        this.salesYear1 = salesYear1;
    }

    public BigDecimal getSalesYear2() {
        return null == salesYear2?BigDecimal.ZERO:salesYear2;
    }

    public void setSalesYear2(BigDecimal salesYear2) {
        this.salesYear2 = salesYear2;
    }

    public BigDecimal getSalesYear3() {
        return null == salesYear3?BigDecimal.ZERO:salesYear3;
    }

    public void setSalesYear3(BigDecimal salesYear3) {
        this.salesYear3 = salesYear3;
    }

    public BigDecimal getTotalSales() {
        return null == totalSales?BigDecimal.ZERO:totalSales;
    }

    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }

    public BigDecimal getnAVYear1() {
        return null == nAVYear1?BigDecimal.ZERO:nAVYear1;
    }

    public void setnAVYear1(BigDecimal nAVYear1) {
        this.nAVYear1 = nAVYear1;
    }

    public BigDecimal getnAVYear2() {
        return null == nAVYear2?BigDecimal.ZERO:nAVYear2;
    }

    public void setnAVYear2(BigDecimal nAVYear2) {
        this.nAVYear2 = nAVYear2;
    }

    public BigDecimal getnAVYear3() {
        return null == nAVYear3?BigDecimal.ZERO:nAVYear3;
    }

    public void setnAVYear3(BigDecimal nAVYear3) {
        this.nAVYear3 = nAVYear3;
    }

    public BigDecimal getIncome() {
        return null == income?BigDecimal.ZERO:income;
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
