package com.yskc.ms.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/1/20 11:49
 * @Description:集团研发管理model   research and development
 * @author: hsx
 */
public class GroupRAndDManagementModel implements Serializable {

    private Integer id;

    //集团部门名
    private String deptName;

    //本年营收预测
    private BigDecimal revenueFcst;

    //业务预测研发费
    private BigDecimal salesRdFee;

    //财务规划研发费
    private BigDecimal rdFee;

    //立项规划数
    private Integer rdPlanCount;

    //本月实际归集研发费
    private BigDecimal amount;

    //本年累计实际归集研发费
    private BigDecimal amount2;

    //本月归集原材料费
    private BigDecimal material;

    //本年累计归集原材料费
    private BigDecimal material2;

    //本年累计预计可加计扣除研发费
    private BigDecimal aMinusK;

    //本月营收
    private BigDecimal revenue;

    //本年累计营收
    private BigDecimal revenue2;

    //研发费总额除以累计营收入
    private BigDecimal aDivideR;

    //可加计扣除研发费除以总营收
    private BigDecimal aKDivideR;

    //研发费同期比%
    private String amountTST;

    //本年研发费累计与上年数比%
    private String amountCompare;

    //企业总人数
    private Integer employeeAmount;

    //本年提案数
    private Integer proposalCnt;

    //实际研发立项数
    private Integer rdCount;

    //研发人员
    private Integer rdEmployeeCount;

    //上年跨本年立项数
    private Integer lastRdCnt;

    //本年跨次年立项数
    private Integer nextRdCnt;

    //轨迹数量
    private Integer docFileCount;

    //研发机构文件
    private Integer buildCount;

    //知识产权数量
    private Integer patentCnt;

    //成果项数
    private Integer achievementCnt;

    //多层研发文件数
    private Integer levelFileCnt;

    //技术人员
    private String techRealName;

    //财务人员
    private String financeRealName;

    //业务人员
    private String owerName;

    //年份
    private Integer year;

    //所属部门名
    private String deptName2;

    //innovation_projectId,用于获取业务员和技术人员
    private Integer innovationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public BigDecimal getRevenueFcst() {
        return revenueFcst;
    }

    public void setRevenueFcst(BigDecimal revenueFcst) {
        this.revenueFcst = revenueFcst;
    }

    public BigDecimal getSalesRdFee() {
        return salesRdFee;
    }

    public void setSalesRdFee(BigDecimal salesRdFee) {
        this.salesRdFee = salesRdFee;
    }

    public BigDecimal getRdFee() {
        return rdFee;
    }

    public void setRdFee(BigDecimal rdFee) {
        this.rdFee = rdFee;
    }

    public Integer getRdPlanCount() {
        return rdPlanCount;
    }

    public void setRdPlanCount(Integer rdPlanCount) {
        this.rdPlanCount = rdPlanCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public BigDecimal getMaterial() {
        return material;
    }

    public void setMaterial(BigDecimal material) {
        this.material = material;
    }

    public BigDecimal getMaterial2() {
        return material2;
    }

    public void setMaterial2(BigDecimal material2) {
        this.material2 = material2;
    }

    public BigDecimal getaMinusK() {
        return aMinusK;
    }

    public void setaMinusK(BigDecimal aMinusK) {
        this.aMinusK = aMinusK;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public BigDecimal getRevenue2() {
        return revenue2;
    }

    public void setRevenue2(BigDecimal revenue2) {
        this.revenue2 = revenue2;
    }

    public BigDecimal getaDivideR() {
        return aDivideR;
    }

    public void setaDivideR(BigDecimal aDivideR) {
        this.aDivideR = aDivideR;
    }

    public BigDecimal getaKDivideR() {
        return aKDivideR;
    }

    public void setaKDivideR(BigDecimal aKDivideR) {
        this.aKDivideR = aKDivideR;
    }

    public String getAmountTST() {
        return amountTST;
    }

    public void setAmountTST(String amountTST) {
        this.amountTST = amountTST;
    }

    public String getAmountCompare() {
        return amountCompare;
    }

    public void setAmountCompare(String amountCompare) {
        this.amountCompare = amountCompare;
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

    public String getTechRealName() {
        return techRealName;
    }

    public void setTechRealName(String techRealName) {
        this.techRealName = techRealName;
    }

    public String getFinanceRealName() {
        return financeRealName;
    }

    public void setFinanceRealName(String financeRealName) {
        this.financeRealName = financeRealName;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDeptName2() {
        return deptName2;
    }

    public void setDeptName2(String deptName2) {
        this.deptName2 = deptName2;
    }

    public Integer getInnovationId() {
        return innovationId;
    }

    public void setInnovationId(Integer innovationId) {
        this.innovationId = innovationId;
    }
}
