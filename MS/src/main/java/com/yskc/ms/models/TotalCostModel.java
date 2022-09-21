package com.yskc.ms.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/1/25 9:47
 * @Description:
 * @author: hsx
 */
public class TotalCostModel implements Serializable {

    //本年营收预测统计
    private BigDecimal totalRevenueFcst;

    //业务预测研发费统计
    private BigDecimal totalSalesRdFee;

    //财务规划研发费统计
    private BigDecimal totalRdFee;

    //立项规划数统计
    private Integer totalRdPlanCount;

    //本月实际归集研发费统计
    private BigDecimal totalAmount;

    //本年累计实际归集研发费统计
    private BigDecimal totalAmount2;

    //本月归集原材料费统计
    private BigDecimal totalMaterial;

    //本年累计归集原材料费统计
    private BigDecimal totalMaterial2;

    //本年累计预计可加计扣除研发费统计
    private BigDecimal totalAMinusK;

    //本月营收统计
    private BigDecimal totalRevenue;

    //本年累计营收统计
    private BigDecimal totalRevenue2;

    //企业总人数统计
    private Integer totalEmployeeAmount;

    //本年提案数统计
    private Integer totalProposalCnt;

    //实际研发立项数统计
    private Integer totalRdCount;

    //研发人员统计
    private Integer totalRdEmployeeCount;

    //上年跨本年立项数统计
    private Integer totalLastRdCnt;

    //本年跨次年立项数统计
    private Integer totalNextRdCnt;

    //轨迹数量统计
    private Integer totalDocFileCount;

    //研发机构文件统计
    private Integer totalBuildCount;

    //知识产权数量统计
    private Integer totalPatentCnt;

    //成果项数统计
    private Integer totalAchievementCnt;

    //多层研发文件数统计
    private Integer totalLevelFileCnt;

    public TotalCostModel() {
        this.totalRevenueFcst = BigDecimal.ZERO;
        this.totalSalesRdFee = BigDecimal.ZERO;
        this.totalRdFee = BigDecimal.ZERO;
        this.totalRdPlanCount = 0;
        this.totalAmount = BigDecimal.ZERO;
        this.totalAmount2 = BigDecimal.ZERO;
        this.totalMaterial = BigDecimal.ZERO;
        this.totalMaterial2 = BigDecimal.ZERO;
        this.totalAMinusK = BigDecimal.ZERO;
        this.totalRevenue = BigDecimal.ZERO;
        this.totalRevenue2 = BigDecimal.ZERO;
        this.totalEmployeeAmount = 0;
        this.totalProposalCnt = 0;
        this.totalRdCount = 0;
        this.totalRdEmployeeCount = 0;
        this.totalLastRdCnt = 0;
        this.totalNextRdCnt = 0;
        this.totalDocFileCount = 0;
        this.totalBuildCount = 0;
        this.totalPatentCnt = 0;
        this.totalAchievementCnt = 0;
        this.totalLevelFileCnt = 0;
    }

    public BigDecimal getTotalRevenueFcst() {
        return totalRevenueFcst;
    }

    public void setTotalRevenueFcst(BigDecimal totalRevenueFcst) {
        this.totalRevenueFcst = totalRevenueFcst;
    }

    public BigDecimal getTotalSalesRdFee() {
        return totalSalesRdFee;
    }

    public void setTotalSalesRdFee(BigDecimal totalSalesRdFee) {
        this.totalSalesRdFee = totalSalesRdFee;
    }

    public BigDecimal getTotalRdFee() {
        return totalRdFee;
    }

    public void setTotalRdFee(BigDecimal totalRdFee) {
        this.totalRdFee = totalRdFee;
    }

    public Integer getTotalRdPlanCount() {
        return totalRdPlanCount;
    }

    public void setTotalRdPlanCount(Integer totalRdPlanCount) {
        this.totalRdPlanCount = totalRdPlanCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount2() {
        return totalAmount2;
    }

    public void setTotalAmount2(BigDecimal totalAmount2) {
        this.totalAmount2 = totalAmount2;
    }

    public BigDecimal getTotalMaterial() {
        return totalMaterial;
    }

    public void setTotalMaterial(BigDecimal totalMaterial) {
        this.totalMaterial = totalMaterial;
    }

    public BigDecimal getTotalMaterial2() {
        return totalMaterial2;
    }

    public void setTotalMaterial2(BigDecimal totalMaterial2) {
        this.totalMaterial2 = totalMaterial2;
    }

    public BigDecimal getTotalAMinusK() {
        return totalAMinusK;
    }

    public void setTotalAMinusK(BigDecimal totalAMinusK) {
        this.totalAMinusK = totalAMinusK;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public BigDecimal getTotalRevenue2() {
        return totalRevenue2;
    }

    public void setTotalRevenue2(BigDecimal totalRevenue2) {
        this.totalRevenue2 = totalRevenue2;
    }

    public Integer getTotalEmployeeAmount() {
        return totalEmployeeAmount;
    }

    public void setTotalEmployeeAmount(Integer totalEmployeeAmount) {
        this.totalEmployeeAmount = totalEmployeeAmount;
    }

    public Integer getTotalProposalCnt() {
        return totalProposalCnt;
    }

    public void setTotalProposalCnt(Integer totalProposalCnt) {
        this.totalProposalCnt = totalProposalCnt;
    }

    public Integer getTotalRdCount() {
        return totalRdCount;
    }

    public void setTotalRdCount(Integer totalRdCount) {
        this.totalRdCount = totalRdCount;
    }

    public Integer getTotalRdEmployeeCount() {
        return totalRdEmployeeCount;
    }

    public void setTotalRdEmployeeCount(Integer totalRdEmployeeCount) {
        this.totalRdEmployeeCount = totalRdEmployeeCount;
    }

    public Integer getTotalLastRdCnt() {
        return totalLastRdCnt;
    }

    public void setTotalLastRdCnt(Integer totalLastRdCnt) {
        this.totalLastRdCnt = totalLastRdCnt;
    }

    public Integer getTotalNextRdCnt() {
        return totalNextRdCnt;
    }

    public void setTotalNextRdCnt(Integer totalNextRdCnt) {
        this.totalNextRdCnt = totalNextRdCnt;
    }

    public Integer getTotalDocFileCount() {
        return totalDocFileCount;
    }

    public void setTotalDocFileCount(Integer totalDocFileCount) {
        this.totalDocFileCount = totalDocFileCount;
    }

    public Integer getTotalBuildCount() {
        return totalBuildCount;
    }

    public void setTotalBuildCount(Integer totalBuildCount) {
        this.totalBuildCount = totalBuildCount;
    }

    public Integer getTotalPatentCnt() {
        return totalPatentCnt;
    }

    public void setTotalPatentCnt(Integer totalPatentCnt) {
        this.totalPatentCnt = totalPatentCnt;
    }

    public Integer getTotalAchievementCnt() {
        return totalAchievementCnt;
    }

    public void setTotalAchievementCnt(Integer totalAchievementCnt) {
        this.totalAchievementCnt = totalAchievementCnt;
    }

    public Integer getTotalLevelFileCnt() {
        return totalLevelFileCnt;
    }

    public void setTotalLevelFileCnt(Integer totalLevelFileCnt) {
        this.totalLevelFileCnt = totalLevelFileCnt;
    }
}
