package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @DateTime: 2022/6/2 15:21
 * @Description:评分记录model
 * @author: hsx
 */
public class QualityScoreLogModel implements Serializable {

    private Integer id;

    private String engineer;

    private String scorer;

    private String scoreData;

    private Integer scoreCount;

    private Date scoreTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalScore;

    private String months;

    private Date month;

    private Integer rsProjectId;

    private String pname;

    private String rdTitle;

    //只有最后的一条评分记录能被编辑（一天内）
    private Boolean editable;

    private Map<Integer, Integer> scores;

    private Boolean isFinal;

    private Date createTime;

    private Boolean passed;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal weight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getScorer() {
        return scorer;
    }

    public void setScorer(String scorer) {
        this.scorer = scorer;
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

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getRsProjectId() {
        return rsProjectId;
    }

    public void setRsProjectId(Integer rsProjectId) {
        this.rsProjectId = rsProjectId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Boolean getEditable() {
        return editable == null?false:editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public String getScoreData() {
        return scoreData;
    }

    public void setScoreData(String scoreData) {
        this.scoreData = scoreData;
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<Integer, Integer> scores) {
        this.scores = scores;
    }

    public Boolean getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Boolean isFinal) {
        this.isFinal = isFinal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPass(Boolean passed) {
        this.passed = passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
}
