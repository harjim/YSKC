package com.yskc.ms.models.rs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.enums.RsProjectStatusEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-15 15:11
 * @Description: 月份归集费用model
 */
public class RdMonthFundsStatusModel {

    private Integer projectId;

    @JsonFormat(pattern = "yyyy年MM月", timezone = "GMT+8")
    private Date month;
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
    /**
     * 样机： 20700
     */
    private BigDecimal sampleMachine;

    /**
     * rd合计
     */
    private BigDecimal rdTotal;

    private Integer status;

    private boolean nothing;

    public static RdMonthFundsStatusModel build(Integer projectId, Date currentMonth) {
        RdMonthFundsStatusModel rdMonthFund = new RdMonthFundsStatusModel();
        rdMonthFund.projectId = projectId;
        rdMonthFund.month = currentMonth;
        rdMonthFund.nothing = true;
        return rdMonthFund;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
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

    public void setRdTotal(BigDecimal rdTotal) {
        this.rdTotal = rdTotal;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStatus() {
        return RsProjectStatusEnum.getByStatus(status).getStatus();
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isNothing() {
        return nothing;
    }

    public void setNothing(boolean nothing) {
        this.nothing = nothing;
    }

    public BigDecimal getSampleMachine() {
        return sampleMachine;
    }

    public void setSampleMachine(BigDecimal sampleMachine) {
        this.sampleMachine = sampleMachine;
    }
}
