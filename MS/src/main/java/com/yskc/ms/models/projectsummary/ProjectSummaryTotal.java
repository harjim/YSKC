package com.yskc.ms.models.projectsummary;

import java.math.BigDecimal;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.projectsummary
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-29 11:50
 * @Description: 费用汇总统计
 */
public class ProjectSummaryTotal {
    private Integer cnt = 0;
    private Integer rdCount = 0;
    private BigDecimal budget = BigDecimal.ZERO;
    private BigDecimal rdFunds = BigDecimal.ZERO;
    private Integer docFileCount = 0;
    private Integer reportCnt ;
    private Integer patentApplyCnt;
    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

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

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Integer getDocFileCount() {
        return docFileCount;
    }

    public void setDocFileCount(Integer docFileCount) {
        this.docFileCount = docFileCount;
    }

    public Integer getReportCnt() {
        return reportCnt;
    }

    public void setReportCnt(Integer reportCnt) {
        this.reportCnt = reportCnt;
    }

    public Integer getPatentApplyCnt() {
        return patentApplyCnt;
    }

    public void setPatentApplyCnt(Integer patentApplyCnt) {
        this.patentApplyCnt = patentApplyCnt;
    }
}
