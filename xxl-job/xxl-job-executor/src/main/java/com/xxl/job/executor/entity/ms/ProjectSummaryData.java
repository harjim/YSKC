package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/11/10 10:09
 * description:
 */
@TableName("project_summary_data")
public class ProjectSummaryData {
    @TableId
    private Integer id;
    @TableField(exist = false)
    private Integer companyId;
    @TableField(exist = false)
    private Integer deptId;
    private Integer customerId;
    /**
     * 规划研发数
     */
    private Integer cnt;
    /**
     * 财务核算类型
     */
    private Integer accountType;
    /**
     * 财务规划研发费(万元)
     */
    private Integer rdFee;
    /**
     * 人员总数(录入)
     */
    private Integer employeeAmount;
    /**
     * 营收预测（万元）
     */
    private Integer revenueFcst;
    private Integer rdCount;
    private BigDecimal budget;
    private BigDecimal rdFunds;
    private Integer docFileCount;
    private Date createTime;
    private Date updateTime;
    private Date operationTime;
    private Integer operationUserId;
    private Integer year;
    private Integer employeeCount;
    private Integer equipmentCount;
    private Integer rdEmployeeCount;
    private Integer employeeOpenidCnt;
    private Integer rdEquipmentCount;
    private Integer patentApplyCnt;
    private Integer lastRdCnt;
    private Integer nextRdCnt;
    private Integer buildCnt;
    private Integer reportCnt;
    /**
     * 成本工资
     */
    private BigDecimal c10000;
    /**
     * 成本五险一金
     */
    private BigDecimal c10100;
    /**
     * 成本材料
     */
    private BigDecimal c20000;
    /**
     * 成本动力
     */
    private BigDecimal c20100;
    /**
     * 成本燃料
     */
    private BigDecimal c20200;
    /**
     * 成本试制
     */
    private BigDecimal c20300;
    /**
     * 成本检测
     */
    private BigDecimal c20500;
    /**
     * 成本修理
     */
    private BigDecimal c20600;
    /**
     * 成本样机
     */
    private BigDecimal c20700;
    /**
     * 成本设备折旧(300,301)
     */
    private BigDecimal c30000;
    /**
     * 成本软件摊销
     */
    private BigDecimal c40000;
    /**
     * 成本其他摊销(401,402)
     */
    private BigDecimal c40200;
    /**
     * 成本设计
     */
    private BigDecimal c50000;
    /**
     * 成本其他(所有>=60000)
     */
    private BigDecimal c69900;
    /**
     * 总计。费用总计=sum(k*)
     */
    private BigDecimal costAmount;
    /**
     * 知识产权数(专利数)
     */
    private Integer patentCnt;
    /**
     * 多层级文件数统计
     */
    private Integer levelFileCnt;
    /**
     * 提案数
     */
    private Integer proposalCnt;
    /**
     * 总营收
     */
    private BigDecimal revenue;
    /**
     * 成果项数（只统计成果数）
     */
    private Integer achievementCnt;
    /**
     * 高品数
     */
    private Integer highTechCount;

    /**
     * 高品收入
     */
    private BigDecimal highTechIncome;

    /**
     * 累计主营业务收入
     */
    private BigDecimal totalIncome;

    public static ProjectSummaryData build(Integer year, Integer customerId, Date date, Integer companyId) {
        ProjectSummaryData summaryData = new ProjectSummaryData();
        summaryData.year = year;
        summaryData.customerId = customerId;
        summaryData.createTime = date;
        summaryData.updateTime = date;
        summaryData.companyId = companyId;
        // 初始化为0 方便后面累加研发费用
        summaryData.rdFunds = summaryData.revenue = BigDecimal.ZERO;
        return summaryData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public Integer getOperationUserId() {
        return operationUserId;
    }

    public void setOperationUserId(Integer operationUserId) {
        this.operationUserId = operationUserId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Integer getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(Integer equipmentCount) {
        this.equipmentCount = equipmentCount;
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

    public Integer getPatentApplyCnt() {
        return patentApplyCnt;
    }

    public void setPatentApplyCnt(Integer patentApplyCnt) {
        this.patentApplyCnt = patentApplyCnt;
    }

    public Integer getLastRdCnt() {
        return lastRdCnt;
    }

    public void setLastRdCnt(Integer lastRdCnt) {
        this.lastRdCnt = lastRdCnt;
    }

    public Integer getBuildCnt() {
        return buildCnt;
    }


    public void setBuildCnt(Integer buildCnt) {
        this.buildCnt = buildCnt;
    }

    public Integer getEmployeeOpenidCnt() {
        return employeeOpenidCnt;
    }

    public void setEmployeeOpenidCnt(Integer employeeOpenidCnt) {
        this.employeeOpenidCnt = employeeOpenidCnt;
    }

    public Integer getReportCnt() {
        return reportCnt;
    }

    public void setReportCnt(Integer reportCnt) {
        this.reportCnt = reportCnt;
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

    public Integer getNextRdCnt() {
        return nextRdCnt;
    }

    public void setNextRdCnt(Integer nextRdCnt) {
        this.nextRdCnt = nextRdCnt;
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

    public BigDecimal getCostAmount() {
        return costAmount;
    }

    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    public Integer getPatentCnt() {
        return patentCnt;
    }

    public void setPatentCnt(Integer patentCnt) {
        this.patentCnt = patentCnt;
    }

    public Integer getLevelFileCnt() {
        return levelFileCnt;
    }

    public void setLevelFileCnt(Integer levelFileCnt) {
        this.levelFileCnt = levelFileCnt;
    }

    public Integer getProposalCnt() {
        return proposalCnt;
    }

    public void setProposalCnt(Integer proposalCnt) {
        this.proposalCnt = proposalCnt;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public Integer getAchievementCnt() {
        return achievementCnt;
    }

    public void setAchievementCnt(Integer achievementCnt) {
        this.achievementCnt = achievementCnt;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void addRdFund(BigDecimal fee) {
        if (null != fee) {
            rdFunds = rdFunds.add(fee);
        }
    }

    public void addRevenue(BigDecimal fee) {
        if (null != fee) {
            revenue = revenue.add(fee);
        }
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }
}
