package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/1/20 11:08
 * @Description:
 * @author: hsx
 */
@TableName("c_rd_summary_district")
public class RdSummaryDistrictEntity implements Serializable {

    private static final long serialVersionUID = -2937504435417462410L;

    @TableId
    private Integer id;

    private Integer deptId;

    private Integer year;

    private Integer rdPlanCount;

    // rd数
    private Integer rdCount;

    //财务总预算
    private BigDecimal budget;

    // 核算类型 0:成本重分类核算研发费,1:冲减主营业务成本列支研发费
    private Integer accountType;

    // 财务规划研发费(万元)
    private Integer rdFee;

    // 人员总数
    private Integer employeeAmount;

    // 营收预测（万元）
    private Integer revenueFcst;

    // 业务预测研发费（万元）
    private Integer salesRdFee;

    // 财务处理方式
    private Integer finaMode;

    //研发费用
    private BigDecimal rdFunds;

    // 研发人员数
    private Integer rdEmployeeCount;

    // 研发设备数
    private Integer rdEquipmentCount;

    //过程文档数(轨迹数)
    private Integer docFileCount;

    private Integer buildCount;

    //高品数
    private Integer highTechCount;

    //高品占比
    private BigDecimal highTechIncome;

    //累计主营业务收入
    private BigDecimal totalIncome;

    // 上年延续RD数
    private Integer lastRdCnt;

    // 本年rd延续下年rd数
    private Integer nextRdCnt;

    //提案数
    private Integer proposalCnt;

    //知识产权数(专利数)
    private Integer patentCnt;

    //成果项数（只统计成果数）
    private Integer achievementCnt;

    //多层级文件数统计
    private Integer levelFileCnt;

    // 查新报告数
    private Integer reportCnt;

    // 成本工资
    private BigDecimal c10000;

    // 成本五险一金
    private BigDecimal c10100;

    // 成本材料
    private BigDecimal c20000;

    // 成本动力
    private BigDecimal c20100;

    // 成本燃料
    private BigDecimal c20200;

    // 成本试制
    private BigDecimal c20300;

    // 成本检测
    private BigDecimal c20500;

    // 成本修理
    private BigDecimal c20600;

    // 成本样机
    private BigDecimal c20700;

    // 成本设备折旧(300,301)
    private BigDecimal c30000;

    // 成本软件摊销
    private BigDecimal c40000;

    // 成本其他摊销(401,402)
    private BigDecimal c40200;

    // 成本设计
    private BigDecimal c50000;

    // 成本其他(所有>=60000)
    private BigDecimal c69900;

    // 总计。费用总计=sum(k*)
    private Integer costAmount;

    //总营收
    private BigDecimal revenue;

    private Date createTime;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRdPlanCount() {
        return rdPlanCount;
    }

    public void setRdPlanCount(Integer rdPlanCount) {
        this.rdPlanCount = rdPlanCount;
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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getRdFee() {
        return rdFee;
    }

    public void setRdFee(Integer rdFee) {
        this.rdFee = rdFee;
    }

    public Integer getEmployeeAmount() {
        return employeeAmount;
    }

    public void setEmployeeAmount(Integer employeeAmount) {
        this.employeeAmount = employeeAmount;
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

    public Integer getFinaMode() {
        return finaMode;
    }

    public void setFinaMode(Integer finaMode) {
        this.finaMode = finaMode;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Integer getRdEmployeeCount() {
        return rdEmployeeCount;
    }

    public void setRdEmployeeCount(Integer rdEmployeeCount) {
        this.rdEmployeeCount = rdEmployeeCount;
    }

    public Integer getRdEquipmentCount() {
        return rdEquipmentCount;
    }

    public void setRdEquipmentCount(Integer rdEquipmentCount) {
        this.rdEquipmentCount = rdEquipmentCount;
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

    public Integer getHighTechCount() {
        return highTechCount;
    }

    public void setHighTechCount(Integer highTechCount) {
        this.highTechCount = highTechCount;
    }

    public BigDecimal getHighTechIncome() {
        return highTechIncome;
    }

    public void setHighTechIncome(BigDecimal highTechIncome) {
        this.highTechIncome = highTechIncome;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
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

    public Integer getProposalCnt() {
        return proposalCnt;
    }

    public void setProposalCnt(Integer proposalCnt) {
        this.proposalCnt = proposalCnt;
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

    public Integer getReportCnt() {
        return reportCnt;
    }

    public void setReportCnt(Integer reportCnt) {
        this.reportCnt = reportCnt;
    }

    public BigDecimal getC10000() {
        return c10000;
    }

    public void setC10000(BigDecimal c10000) {
        this.c10000 = c10000;
    }

    public BigDecimal getC10100() {
        return c10100;
    }

    public void setC10100(BigDecimal c10100) {
        this.c10100 = c10100;
    }

    public BigDecimal getC20000() {
        return c20000;
    }

    public void setC20000(BigDecimal c20000) {
        this.c20000 = c20000;
    }

    public BigDecimal getC20100() {
        return c20100;
    }

    public void setC20100(BigDecimal c20100) {
        this.c20100 = c20100;
    }

    public BigDecimal getC20200() {
        return c20200;
    }

    public void setC20200(BigDecimal c20200) {
        this.c20200 = c20200;
    }

    public BigDecimal getC20300() {
        return c20300;
    }

    public void setC20300(BigDecimal c20300) {
        this.c20300 = c20300;
    }

    public BigDecimal getC20500() {
        return c20500;
    }

    public void setC20500(BigDecimal c20500) {
        this.c20500 = c20500;
    }

    public BigDecimal getC20600() {
        return c20600;
    }

    public void setC20600(BigDecimal c20600) {
        this.c20600 = c20600;
    }

    public BigDecimal getC20700() {
        return c20700;
    }

    public void setC20700(BigDecimal c20700) {
        this.c20700 = c20700;
    }

    public BigDecimal getC30000() {
        return c30000;
    }

    public void setC30000(BigDecimal c30000) {
        this.c30000 = c30000;
    }

    public BigDecimal getC40000() {
        return c40000;
    }

    public void setC40000(BigDecimal c40000) {
        this.c40000 = c40000;
    }

    public BigDecimal getC40200() {
        return c40200;
    }

    public void setC40200(BigDecimal c40200) {
        this.c40200 = c40200;
    }

    public BigDecimal getC50000() {
        return c50000;
    }

    public void setC50000(BigDecimal c50000) {
        this.c50000 = c50000;
    }

    public BigDecimal getC69900() {
        return c69900;
    }

    public void setC69900(BigDecimal c69900) {
        this.c69900 = c69900;
    }

    public Integer getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(Integer costAmount) {
        this.costAmount = costAmount;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
