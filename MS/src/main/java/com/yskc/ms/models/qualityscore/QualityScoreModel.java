package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @DateTime: 2022/5/13 9:45
 * @Description:
 * @author: hsx
 */
public class QualityScoreModel implements Serializable {

    private Integer id;

    private Integer rsProjectId;

    private Integer companyId;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;

    private Integer scoreCount;

    private Date scoreTime;

    private Integer engineerId;

    private Map<Integer,Integer> scores;

    private Integer year;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalScore;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal weight;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal passRate;

    private Integer type;

    private String engineer;

    private Boolean isFinal;

    private Boolean passed;

    private Integer monthsId;

    private String months;

    private String scorer;

    private Integer qualityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Integer scoreCount) {
        this.scoreCount = scoreCount;
    }

    public Date getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(Date scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getPassRate() {
        return passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Boolean getFinal() {
        return isFinal;
    }

    public void setIsFinal(Boolean aFinal) {
        isFinal = aFinal;
    }
    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
    }

    public Integer getMonthsId() {
        return monthsId;
    }

    public void setMonthsId(Integer monthsId) {
        this.monthsId = monthsId;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getScorer() {
        return scorer;
    }

    public void setScorer(String scorer) {
        this.scorer = scorer;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }
}
