package com.yskc.rs.models.reportform;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 指标管控中心计算model
 * @author: cyj
 * @create: 2022-01-19 17:40
 **/
public class ComputeModel implements Serializable {
    private Integer accountType;
    private Integer revenueFcst;
    private Integer salesRdFee;
    private Integer rdFee;
    private Integer rdPlanCount;
    private Integer finaMode;
    private Integer employeeAmount;
    private Integer proposalCnt;
    private Integer rdCount;
    private Integer rdEmployeeCount;
    private Integer lastRdCnt;
    private Integer nextRdCnt;
    private Integer docFileCount;
    private Integer buildCount;
    private Integer patentCnt;
    private Integer achievementCnt;
    private Integer levelFileCnt;

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getRevenueFcst() {
        return revenueFcst;
    }

    public void setRevenueFcst(Integer revenueFcst) {
        this.revenueFcst = revenueFcst;
    }

    public Integer getSalesRdFee() {
        return salesRdFee;
    }

    public void setSalesRdFee(Integer salesRdFee) {
        this.salesRdFee = salesRdFee;
    }

    public Integer getRdFee() {
        return rdFee;
    }

    public void setRdFee(Integer rdFee) {
        this.rdFee = rdFee;
    }

    public Integer getRdPlanCount() {
        return rdPlanCount;
    }

    public void setRdPlanCount(Integer rdPlanCount) {
        this.rdPlanCount = rdPlanCount;
    }

    public Integer getFinaMode() {
        return finaMode;
    }

    public void setFinaMode(Integer finaMode) {
        this.finaMode = finaMode;
    }

    public Integer getEmployeeAmount() {
        return employeeAmount;
    }

    public void setEmployeeAmount(Integer employeeAmount) {
        this.employeeAmount = employeeAmount;
    }

    public Integer getProposalCnt() {
        return proposalCnt;
    }

    public void setProposalCnt(Integer proposalCnt) {
        this.proposalCnt = proposalCnt;
    }

    public Integer getRdCount() {
        return rdCount;
    }

    public void setRdCount(Integer rdCount) {
        this.rdCount = rdCount;
    }

    public Integer getRdEmployeeCount() {
        return rdEmployeeCount;
    }

    public void setRdEmployeeCount(Integer rdEmployeeCount) {
        this.rdEmployeeCount = rdEmployeeCount;
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

    public Integer getDocFileCount() {
        return docFileCount;
    }

    public void setDocFileCount(Integer docFileCount) {
        this.docFileCount = docFileCount;
    }

    public Integer getBuildCount() {
        return buildCount;
    }

    public void setBuildCount(Integer buildCount) {
        this.buildCount = buildCount;
    }

    public Integer getPatentCnt() {
        return patentCnt;
    }

    public void setPatentCnt(Integer patentCnt) {
        this.patentCnt = patentCnt;
    }

    public Integer getAchievementCnt() {
        return achievementCnt;
    }

    public void setAchievementCnt(Integer achievementCnt) {
        this.achievementCnt = achievementCnt;
    }

    public Integer getLevelFileCnt() {
        return levelFileCnt;
    }

    public void setLevelFileCnt(Integer levelFileCnt) {
        this.levelFileCnt = levelFileCnt;
    }
}
