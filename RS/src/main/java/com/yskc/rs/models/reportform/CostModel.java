package com.yskc.rs.models.reportform;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 企业成本总额model
 * @author: cyj
 * @create: 2022-01-19 16:57
 **/
public class CostModel implements Serializable {
    //工资
    private String wages;
    //五险一金
    private String insuranceAndFund;
    //材料
    private String material;
    //动力
    private String power;
    //燃料
    private String fuel;
    //试制
    private String trial;
    //检测
    private String test;
    //修理
    private String repair;
    //样机
    private String machine;
    //折旧
    private String depreciation;
    //软件
    private String software;
    //其他摊销
    private String otherAmortization;
    //其他
    private String other;
    //设计
    private String design;

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public String getInsuranceAndFund() {
        return insuranceAndFund;
    }

    public void setInsuranceAndFund(String insuranceAndFund) {
        this.insuranceAndFund = insuranceAndFund;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(String depreciation) {
        this.depreciation = depreciation;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getSoftware() {
        return software;
    }

    public void setSoftware(String software) {
        this.software = software;
    }

    public String getOtherAmortization() {
        return otherAmortization;
    }

    public void setOtherAmortization(String otherAmortization) {
        this.otherAmortization = otherAmortization;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
