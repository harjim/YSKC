package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/6/9 10:28
 * @Description:审核序时账数据model
 * @author: hsx
 */
public class ScoreCollectModel implements Serializable {

    private Integer companyId;

    private String companyName;

    private Integer year;

    private String deptName;

    private Integer type;

    @JsonFormat(pattern = "yyyyMM", timezone = "GMT+8")
    private Date month;

    private String engineers;

    //服务区域
    private String coverage;

    private String scorer;

    private Integer count;

    private Integer finishNum;

    private Integer unfinishNum;

    private Integer onePassNum;

    private Integer twoPassNum;

    //平均分
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal avgScore;

    //权值
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal weight;

    //权值合计
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalWeight;

    //J值
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal jValue;

    private Integer engineerId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getEngineers() {
        return engineers;
    }

    public void setEngineers(String engineers) {
        this.engineers = engineers;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getScorer() {
        return scorer;
    }

    public void setScorer(String scorer) {
        this.scorer = scorer;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public Integer getUnfinishNum() {
        return unfinishNum;
    }

    public void setUnfinishNum(Integer unfinishNum) {
        this.unfinishNum = unfinishNum;
    }

    public Integer getOnePassNum() {
        return onePassNum;
    }

    public void setOnePassNum(Integer onePassNum) {
        this.onePassNum = onePassNum;
    }

    public Integer getTwoPassNum() {
        return twoPassNum;
    }

    public void setTwoPassNum(Integer twoPassNum) {
        this.twoPassNum = twoPassNum;
    }

    public BigDecimal getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(BigDecimal avgScore) {
        this.avgScore = avgScore;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getjValue() {
        return jValue;
    }

    public void setjValue(BigDecimal jValue) {
        this.jValue = jValue;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }
}
