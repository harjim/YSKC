package com.yskc.ms.models.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 研发费用model
 *
 * @author zhangdingfu
 */
public class DataReportModel {

//	工资10000  五险一金10100(汇总五险一金)  设备折旧30000 ，仪器折旧30100，材料：20000
//	动力：20100，燃料：20200，试制：（20300，20301）,修理：20600，检测：20500，
//	设计：50000  摊销：（40000，40100, 40200）,其他：69900
    /**
     * 项目id
     */
    private Integer id;

    private String rdTitle;
    /**
     * 项目名称
     */
    private String pname;
    /**
     * 年
     */
    private Date ryear;
    /**
     * 月
     */
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
     * 差旅：（60400）,
     */
    private BigDecimal travel;
    /**
     * 其他：69900
     */
    private BigDecimal other;

    /**
     * rd合计
     */
    private BigDecimal rdTotal;

    private BigDecimal sampleMachine;
    /**
     * 起止日期
     *
     */
    @JsonFormat(pattern = "yyyy年MM月", timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy年MM月", timezone = "GMT+8")
    private Date endDate;
    private String beginAndEnd;

    public static void sum(DataReportModel source, DataReportModel target) {
        target.salary = add(source.salary, target.salary);
        target.insurance = add(source.insurance, target.insurance);
        target.prod = add(source.prod, target.prod);
        target.lab = add(source.lab, target.lab);
        target.material = add(source.material, target.material);
        target.stimulus = add(source.stimulus, target.stimulus);
        target.fuel = add(source.fuel, target.fuel);
        target.trial = add(source.trial, target.trial);
        target.repair = add(source.repair, target.repair);
        target.inspection = add(source.inspection, target.inspection);
        target.design = add(source.design, target.design);
        target.amortization = add(source.amortization, target.amortization);
        target.travel = add(source.travel, target.travel);
        target.other = add(source.other, target.other);
        target.rdTotal = add(source.rdTotal, target.rdTotal);
        target.sampleMachine = add(source.sampleMachine,target.sampleMachine);
    }

    private static BigDecimal add(BigDecimal add, BigDecimal addPlus) {
        if (add == null) {
            return addPlus == null ? BigDecimal.ZERO : addPlus;
        }
        if (addPlus == null) {
            return add;
        }
        return add.add(addPlus);
    }

    public BigDecimal getSampleMachine() {
        return sampleMachine;
    }

    public void setSampleMachine(BigDecimal sampleMachine) {
        this.sampleMachine = sampleMachine;
    }

    public BigDecimal getTravel() {
        return travel;
    }

    public void setTravel(BigDecimal travel) {
        this.travel = travel;
    }

    public String getBeginAndEnd() {
        return beginAndEnd;
    }

    public void setBeginAndEnd(String beginAndEnd) {
        this.beginAndEnd = beginAndEnd;
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

    public BigDecimal getRdTotal() {
        return add(salary, insurance).add(add(prod, lab)).add(add(material, stimulus))
                .add(add(fuel, trial)).add(add(repair, inspection)).add(add(design, amortization)).add(add(travel, other));
    }

    public void setRdTotal(BigDecimal rdTotal) {
        this.rdTotal = rdTotal;
    }

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

    public Date getRyear() {
        return ryear;
    }

    public void setRyear(Date ryear) {
        this.ryear = ryear;
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

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }
}
