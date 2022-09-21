package com.yskc.rs.models.report;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.report
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-26 17:20
 * @Description: 申报费用汇总
 */
public class ReportSumamryModel implements Serializable {

    private Integer projectId;

    private String pname;

    private Integer rdIndex;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 薪资
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal salary;

    /**
     * 保险 // 五险一金等
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal insurance;

    /**
     * 折旧
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciation;
    /**
     * 材料
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal material;
    /**
     * 设计
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal design;

    /**
     * 动力
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal stimulus;

    /**
     * 燃料
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal fuel;

    /**
     * 检测
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal inspection;

    /**
     * 修理
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal repair;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
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

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
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

    public BigDecimal getDesign() {
        return design;
    }

    public void setDesign(BigDecimal design) {
        this.design = design;
    }

    public BigDecimal getInspection() {
        return inspection;
    }

    public void setInspection(BigDecimal inspection) {
        this.inspection = inspection;
    }

    public BigDecimal getRepair() {
        return repair;
    }

    public void setRepair(BigDecimal repair) {
        this.repair = repair;
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

}
