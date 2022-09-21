package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.qualityscore
 * @Author: zhangdingfu
 * @CreateTime: 2022-06-21 10:42
 * @Description: 评分信息
 */
public class QualityScoreInfoModel {
    private BigDecimal weight;

    private Integer scoreCount;

    private Integer engineerId;

    @JsonProperty(value = "isFinal")
    private Boolean isFinal;

    private Integer qualityId;

    private BigDecimal totalScore;

    private Map<Integer, Integer> scores;
    private String scoresDetail;

    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;

    private List<EngineerModel> engineerList;

    private List<QualityScoreMonthModel> months;

    public String getScoresDetail() {
        return scoresDetail;
    }

    public void setScoresDetail(String scoresDetail) {
        this.scoresDetail = scoresDetail;
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

    public Boolean getIsFinal() {
        return isFinal;
    }

    public void setFinal(Boolean aFinal) {
        isFinal = aFinal;
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

    public List<EngineerModel> getEngineerList() {
        return engineerList;
    }

    public void setEngineerList(List<EngineerModel> engineerList) {
        this.engineerList = engineerList;
    }

    public List<QualityScoreMonthModel> getMonths() {
        return months;
    }

    public void setMonths(List<QualityScoreMonthModel> months) {
        this.months = months;
    }
}
