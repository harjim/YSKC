package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @DateTime: 2022/6/2 11:21
 * @Description:月份组model
 * @author: hsx
 */
public class QualityScoreMonthModel implements Serializable {

    private Integer id;

    private String months;

    private Integer scoreCount;

    private Integer engineerId;

    private Integer isFinal;

    private BigDecimal weight;

    private BigDecimal totalScore;

    private Map<Integer, Integer> scores;
    private String scoresDetail;

    private Integer qualityId;

    private Integer year;

    /**
     * 评分月份
     */
    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    private Date month;

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    public String getScoresDetail() {
        return scoresDetail;
    }

    public void setScoresDetail(String scoresDetail) {
        this.scoresDetail = scoresDetail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public Integer getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Integer scoreCount) {
        this.scoreCount = scoreCount;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getQualityId() {
        return qualityId;
    }

    public void setQualityId(Integer qualityId) {
        this.qualityId = qualityId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
