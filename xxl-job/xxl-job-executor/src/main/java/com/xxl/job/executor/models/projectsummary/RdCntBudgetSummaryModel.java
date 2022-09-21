package com.xxl.job.executor.models.projectsummary;

import com.xxl.job.executor.entity.ms.ProjectSummaryData;

import java.math.BigDecimal;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2022-01-20 15:17
 * @Description: rd统计和预算统计
 */
public class RdCntBudgetSummaryModel extends BaseSummaryModel {
    private Integer rdCount;
    private BigDecimal budget;
    private Integer lastRdCnt;
    private Integer nextRdCnt;

    public Integer getRdCount() {
        return rdCount;
    }

    public void setRdCount(Integer rdCount) {
        this.rdCount = rdCount;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getLastRdCnt() {
        return lastRdCnt;
    }

    public void setLastRdCnt(Integer lastRdCnt) {
        this.lastRdCnt = lastRdCnt;
    }

    public Integer getNextRdCnt() {
        return nextRdCnt;
    }

    public void setNextRdCnt(Integer nextRdCnt) {
        this.nextRdCnt = nextRdCnt;
    }

    @Override
    public void copyToData(ProjectSummaryData ps) {
        ps.setRdCount(rdCount);
        ps.setBudget(budget);
        ps.setLastRdCnt(lastRdCnt);
        ps.setNextRdCnt(nextRdCnt);
    }
}
