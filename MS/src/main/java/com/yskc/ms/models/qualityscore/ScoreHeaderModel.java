package com.yskc.ms.models.qualityscore;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/6/9 11:19
 * @Description:审核序时账统计header model
 * @author: hsx
 */
public class ScoreHeaderModel implements Serializable {

    private Integer count;

    //立项报告
    private Integer reportNum;

    //留存备查资料
    private Integer backupFileNum;

    //过程文件
    private Integer docNum;

    //创新体系
    private Integer innovationNum;

    //查新报告
    private Integer noveltyNum;

    //完成数量
    private Integer finishNum;

    //未完成数量
    private Integer unfinishNum;

    //平均分
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal avgScore;

    //权值统计
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalWeight;

    //J值
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal jValue;

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    public Integer getBackupFileNum() {
        return backupFileNum;
    }

    public void setBackupFileNum(Integer backupFileNum) {
        this.backupFileNum = backupFileNum;
    }

    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public Integer getInnovationNum() {
        return innovationNum;
    }

    public void setInnovationNum(Integer innovationNum) {
        this.innovationNum = innovationNum;
    }

    public Integer getNoveltyNum() {
        return noveltyNum;
    }

    public void setNoveltyNum(Integer noveltyNum) {
        this.noveltyNum = noveltyNum;
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

    public BigDecimal getjValue() {
        return jValue;
    }

    public void setjValue(BigDecimal jValue) {
        this.jValue = jValue;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
