package com.yskc.rs.models.trialprod;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/6/16 18:07
 * description:
 */
public class TrialProdPlanModel {
    private String rdIndex;//项目编号

    private String deptName;//部门

    private String processSection;//工艺段

    private String pname;//项目名称

    private Integer estimateExpense;//预算总费用

    private Date beginDate;

    private Date endDate;

    private String beginAndEndDate;//项目实施期

    private Boolean trialProd;//是否试制

    private Integer planPO;//计划试制量

    private Integer actualPO;//实际试制量

    private String pos;//试制机台号

    private String trialGroup;//试产班组

    private BigDecimal mainMaterial;//主材消耗

    private BigDecimal auxMaterial;//辅材消耗

    private BigDecimal fuel;//燃料消耗

    private BigDecimal power;//动力消耗

    private BigDecimal gas;//气体消耗

    private BigDecimal spare;//预计备品备件

    private Date trialDate;//试制日期

    private Date startTime;//开始时间

    private Date endTime;//结束时间

    private String planTime;//试制时间

    private Integer beginYear;

    private BigDecimal budget;

    private String trial;

    private String unit;//单位

    private String trialMonth;//试制月份

    private String rdTitle;

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getTrialMonth() {
        return trialMonth;
    }

    public void setTrialMonth(String trialMonth) {
        this.trialMonth = trialMonth;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public Date getTrialDate() {
        return trialDate;
    }

    public void setTrialDate(Date trialDate) {
        this.trialDate = trialDate;
    }

    public String getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(String rdIndex) {
        this.rdIndex = rdIndex;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getEstimateExpense() {
        return estimateExpense;
    }

    public void setEstimateExpense(Integer estimateExpense) {
        this.estimateExpense = estimateExpense;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBeginAndEndDate() {
        return beginAndEndDate;
    }

    public void setBeginAndEndDate(String beginAndEndDate) {
        this.beginAndEndDate = beginAndEndDate;
    }

    public Boolean getTrialProd() {
        return trialProd;
    }

    public void setTrialProd(Boolean trialProd) {
        this.trialProd = trialProd;
    }

    public Integer getPlanPO() {
        return planPO;
    }

    public void setPlanPO(Integer planPO) {
        this.planPO = planPO;
    }

    public Integer getActualPO() {
        return actualPO;
    }

    public void setActualPO(Integer actualPO) {
        this.actualPO = actualPO;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getTrialGroup() {
        return trialGroup;
    }

    public void setTrialGroup(String trialGroup) {
        this.trialGroup = trialGroup;
    }

    public BigDecimal getMainMaterial() {
        return mainMaterial;
    }

    public void setMainMaterial(BigDecimal mainMaterial) {
        this.mainMaterial = mainMaterial;
    }

    public BigDecimal getAuxMaterial() {
        return auxMaterial;
    }

    public void setAuxMaterial(BigDecimal auxMaterial) {
        this.auxMaterial = auxMaterial;
    }

    public BigDecimal getFuel() {
        return fuel;
    }

    public void setFuel(BigDecimal fuel) {
        this.fuel = fuel;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public BigDecimal getGas() {
        return gas;
    }

    public void setGas(BigDecimal gas) {
        this.gas = gas;
    }

    public BigDecimal getSpare() {
        return spare;
    }

    public void setSpare(BigDecimal spare) {
        this.spare = spare;
    }
}
