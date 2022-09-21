package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/6/9 10:29
 * @Description: 审核序时账下拉列表数据model
 * @author: hsx
 */
public class ScoreModel implements Serializable {

    private Integer id;

    private Integer year;

    private String rdTitle;

    private String pname;

    private Integer rsProjectId;

    private Integer status;

    //一提日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date oneSubmitDate;

    //一审日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date oneAuditDate;

    //卓效一审日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date oneScoreDate;

    //一审得分
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal oneScore;

    //二提日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date twoSubmitDate;

    //二审日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date twoAuditDate;

    //卓效二审日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date twoScoreDate;

    //二审得分
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal twoScore;

    //平均分
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal avgScore;

    //权值
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalWeight;

    //通过情况
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal passRate;

    //J值
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal jValue;

    private String months;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOneSubmitDate() {
        return oneSubmitDate;
    }

    public void setOneSubmitDate(Date oneSubmitDate) {
        this.oneSubmitDate = oneSubmitDate;
    }

    public Date getOneAuditDate() {
        return oneAuditDate;
    }

    public void setOneAuditDate(Date oneAuditDate) {
        this.oneAuditDate = oneAuditDate;
    }

    public Date getOneScoreDate() {
        return oneScoreDate;
    }

    public void setOneScoreDate(Date oneScoreDate) {
        this.oneScoreDate = oneScoreDate;
    }

    public BigDecimal getOneScore() {
        return oneScore;
    }

    public void setOneScore(BigDecimal oneScore) {
        this.oneScore = oneScore;
    }

    public Date getTwoSubmitDate() {
        return twoSubmitDate;
    }

    public void setTwoSubmitDate(Date twoSubmitDate) {
        this.twoSubmitDate = twoSubmitDate;
    }

    public Date getTwoAuditDate() {
        return twoAuditDate;
    }

    public void setTwoAuditDate(Date twoAuditDate) {
        this.twoAuditDate = twoAuditDate;
    }

    public Date getTwoScoreDate() {
        return twoScoreDate;
    }

    public void setTwoScoreDate(Date twoScoreDate) {
        this.twoScoreDate = twoScoreDate;
    }

    public BigDecimal getTwoScore() {
        return twoScore;
    }

    public void setTwoScore(BigDecimal twoScore) {
        this.twoScore = twoScore;
    }

    public BigDecimal getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(BigDecimal avgScore) {
        this.avgScore = avgScore;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getPassRate() {
        return passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }

    public BigDecimal getjValue() {
        return jValue;
    }

    public void setjValue(BigDecimal jValue) {
        this.jValue = jValue;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
