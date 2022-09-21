package com.yskc.ms.models.rs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-15 15:15
 * @Description: 项目归集费用model
 */
public class RdFundsModel {
    private Integer id;
    /**
     * 项目名称
     */
    private String pname;
    /**
     * 工资10000
     */
    private BigDecimal salary;
    /**
     * 五险一金10100(汇总五险一金)
     */
    private BigDecimal insurance;
    /**
     * 设备折旧30000
     */
    private BigDecimal prod;
    /**
     * 仪器折旧30100
     */
    private BigDecimal lab;
    /**
     * 材料：20000
     */
    private BigDecimal material;
    /**
     * 动力：20100，
     */
    private BigDecimal stimulus;
    /**
     * 燃料：20200，
     */
    private BigDecimal fuel;
    /**
     * 试制：（20300，20301）
     */
    private BigDecimal trial;
    /**
     * ,修理：20600，
     */
    private BigDecimal repair;
    /**
     * 检测：20500，
     */
    private BigDecimal inspection;
    /**
     * 设计：50000
     */
    private BigDecimal design;
    /**
     * 摊销：（40000，40100, 40200）,
     */
    private BigDecimal amortization;
    /**
     * 摊销：（60400）,
     */
    private BigDecimal travel;
    /**
     * 其他：69900
     */
    private BigDecimal other;

    private BigDecimal sampleMachine;

    /**
     * rd合计
     */
    private BigDecimal rdTotal;

    private String rdTitle;

    /**
     * 起止日期
     *
     * @return
     */
    @JsonFormat(pattern = "yyyy年MM月", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy年MM月", timezone = "GMT+8")
    private Date endDate;
    List<RdMonthFundsStatusModel> monthFundsStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getInsurance() {
        return insurance;
    }

    public void setInsurance(BigDecimal insurance) {
        this.insurance = insurance;
    }

    public BigDecimal getProd() {
        return prod;
    }

    public void setProd(BigDecimal prod) {
        this.prod = prod;
    }

    public BigDecimal getLab() {
        return lab;
    }

    public void setLab(BigDecimal lab) {
        this.lab = lab;
    }

    public BigDecimal getMaterial() {
        return material;
    }

    public void setMaterial(BigDecimal material) {
        this.material = material;
    }

    public BigDecimal getStimulus() {
        return stimulus;
    }

    public void setStimulus(BigDecimal stimulus) {
        this.stimulus = stimulus;
    }

    public BigDecimal getFuel() {
        return fuel;
    }

    public void setFuel(BigDecimal fuel) {
        this.fuel = fuel;
    }

    public BigDecimal getTrial() {
        return trial;
    }

    public void setTrial(BigDecimal trial) {
        this.trial = trial;
    }

    public BigDecimal getRepair() {
        return repair;
    }

    public void setRepair(BigDecimal repair) {
        this.repair = repair;
    }

    public BigDecimal getInspection() {
        return inspection;
    }

    public void setInspection(BigDecimal inspection) {
        this.inspection = inspection;
    }

    public BigDecimal getDesign() {
        return design;
    }

    public void setDesign(BigDecimal design) {
        this.design = design;
    }

    public BigDecimal getAmortization() {
        return amortization;
    }

    public void setAmortization(BigDecimal amortization) {
        this.amortization = amortization;
    }

    public BigDecimal getTravel() {
        return travel;
    }

    public void setTravel(BigDecimal travel) {
        this.travel = travel;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getRdTotal() {
        // 任意费用不为空，1为空，全为空，1不空，全不空
        if (insurance == null) {
            return null;
        }
        return salary.add(insurance).add(prod).add(lab).add(material).add(stimulus)
                .add(fuel).add(trial).add(repair).add(inspection).add(design).add(amortization).add(travel)
                .add(other).add(sampleMachine);
    }

    public void setRdTotal(BigDecimal rdTotal) {
        this.rdTotal = rdTotal;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
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

    public List<RdMonthFundsStatusModel> getMonthFundsStatus() {
        return monthFundsStatus;
    }

    public BigDecimal getSampleMachine() {
        return sampleMachine;
    }

    public void setSampleMachine(BigDecimal sampleMachine) {
        this.sampleMachine = sampleMachine;
    }

    public void setMonthFundsStatus(List<RdMonthFundsStatusModel> monthFundsStatus) {
        this.monthFundsStatus = monthFundsStatus;

    }


    public void addFund(RdMonthFundsStatusModel fund) {
        if (null == fund.getSalary()) {
            return;
        }
        salary = salary == null ? fund.getSalary() : salary.add(fund.getSalary());
        insurance = insurance == null ? fund.getInsurance() : insurance.add(fund.getInsurance());
        prod = prod == null ? fund.getProd() : prod.add(fund.getProd());
        lab = lab == null ? fund.getLab() : lab.add(fund.getLab());
        material = material == null ? fund.getMaterial() : material.add(fund.getMaterial());
        stimulus = stimulus == null ? fund.getStimulus() : stimulus.add(fund.getStimulus());
        fuel = fuel == null ? fund.getFuel() : fuel.add(fund.getFuel());
        trial = trial == null ? fund.getTrial() : trial.add(fund.getTrial());
        repair = repair == null ? fund.getRepair() : repair.add(fund.getRepair());
        inspection = inspection == null ? fund.getInspection() : inspection.add(fund.getInspection());
        design = design == null ? fund.getDesign() : design.add(fund.getDesign());
        amortization = amortization == null ? fund.getAmortization() : amortization.add(fund.getAmortization());
        travel = travel == null ? fund.getTravel() : travel.add(fund.getTravel());
        other = other == null ? fund.getOther() : other.add(fund.getOther());
        sampleMachine = sampleMachine == null ? fund.getSampleMachine() : sampleMachine.add(fund.getSampleMachine());
    }
}
