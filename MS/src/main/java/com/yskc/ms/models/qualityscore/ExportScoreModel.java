package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/6/10 16:19
 * @Description: 审核序时账导出model
 * @author: hsx
 */
public class ExportScoreModel implements Serializable {

    //服务区域
    private String coverage;

    private String deptName;

    private String companyName;

    private Integer year;

    @JsonFormat(pattern = "yyyyMM", timezone = "GMT+8")
    private Date month;

    private Integer rsProjectId;

    private String rdTitle;

    private String pname;

    private String engineerName;

    private String scorer;

    private String type;

    private String status;

    //一提日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date oneSubmitTime;

    //卓效一审日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date oneScoreTime;

    //一审通过数量
    private Integer onePassNum;

    //一审得分
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal oneScore;

    //二提日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date twoSubmitTime;

    //卓效二审日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date twoScoreTime;

    //二审通过数量
    private Integer twoPassNum;

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

    private Integer num;

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
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

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public String getScorer() {
        return scorer;
    }

    public void setScorer(String scorer) {
        this.scorer = scorer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOnePassNum() {
        return onePassNum;
    }

    public void setOnePassNum(Integer onePassNum) {
        this.onePassNum = onePassNum;
    }

    public BigDecimal getOneScore() {
        return oneScore;
    }

    public void setOneScore(BigDecimal oneScore) {
        this.oneScore = oneScore;
    }

    public Date getOneSubmitTime() {
        return oneSubmitTime;
    }

    public void setOneSubmitTime(Date oneSubmitTime) {
        this.oneSubmitTime = oneSubmitTime;
    }

    public Date getOneScoreTime() {
        return oneScoreTime;
    }

    public void setOneScoreTime(Date oneScoreTime) {
        this.oneScoreTime = oneScoreTime;
    }

    public Date getTwoSubmitTime() {
        return twoSubmitTime;
    }

    public void setTwoSubmitTime(Date twoSubmitTime) {
        this.twoSubmitTime = twoSubmitTime;
    }

    public Date getTwoScoreTime() {
        return twoScoreTime;
    }

    public void setTwoScoreTime(Date twoScoreTime) {
        this.twoScoreTime = twoScoreTime;
    }

    public Integer getTwoPassNum() {
        return twoPassNum;
    }

    public void setTwoPassNum(Integer twoPassNum) {
        this.twoPassNum = twoPassNum;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
