package com.yskc.rs.models.YearCost;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: rs
 * @description:
 * @author: cyj
 * @create: 2022-01-14 14:51
 **/
public class YearCostExModel implements Serializable {
    private Integer year;
    private Integer month;
    private Integer companyId;
    //工资
    private BigDecimal wages;
    //五险一金
    private BigDecimal insuranceAndFund;
    //材料
    private BigDecimal material;
    //折旧
    private BigDecimal depreciation;
    //动力
    private BigDecimal power;
    //燃料
    private BigDecimal fuel;
    //试制
    private BigDecimal trial;
    //检测
    private BigDecimal test;
    //修理
    private BigDecimal repair;
    //样机
    private BigDecimal machine;
    //设计
    private BigDecimal design;
    //软件
    private BigDecimal software;
    //其他摊销
    private BigDecimal otherAmortization;
    //其他
    private BigDecimal other;

    public YearCostExModel(){}

    public YearCostExModel(List<YearCostModel> list,Integer year,Integer companyId){
        this.year = year;
        this.companyId = companyId;
        for(YearCostModel model : list){
            Integer rdType = model.getRdType();
            BigDecimal cost = model.getCost();
            switch (rdType){
                case 10000:
                    this.wages = cost;
                    break;
                case 10100:
                    this.insuranceAndFund = cost;
                    break;
                case 20000:
                    this.material = cost;
                    break;
                case 20100:
                    this.power = cost;
                    break;
                case 20200:
                    this.fuel = cost;
                    break;
                case 20300:
                    this.trial = cost;
                    break;
                case 20500:
                    this.test = cost;
                    break;
                case 20600:
                    this.repair = cost;
                    break;
                case 20700:
                    this.machine = cost;
                    break;
                case 30000:
                    this.depreciation = cost;
                    break;
                case 40000:
                    this.software = cost;
                    break;
                case 40200:
                    this.otherAmortization = cost;
                    break;
                case 50000:
                    this.design = cost;
                    break;
                case 69900:
                    this.other = cost;
                    break;
                default:
            }
        }
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth( Integer month ) {
        this.month = month;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getWages() {
        return wages;
    }

    public void setWages(BigDecimal wages) {
        this.wages = wages;
    }

    public BigDecimal getInsuranceAndFund() {
        return insuranceAndFund;
    }

    public void setInsuranceAndFund(BigDecimal insuranceAndFund) {
        this.insuranceAndFund = insuranceAndFund;
    }

    public BigDecimal getMaterial() {
        return material;
    }

    public void setMaterial(BigDecimal material) {
        this.material = material;
    }

    public BigDecimal getDepreciation() {
        return depreciation;
    }

    public void setDepreciation(BigDecimal depreciation) {
        this.depreciation = depreciation;
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
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

    public BigDecimal getTest() {
        return test;
    }

    public void setTest(BigDecimal test) {
        this.test = test;
    }

    public BigDecimal getRepair() {
        return repair;
    }

    public void setRepair(BigDecimal repair) {
        this.repair = repair;
    }

    public BigDecimal getMachine() {
        return machine;
    }

    public void setMachine(BigDecimal machine) {
        this.machine = machine;
    }

    public BigDecimal getDesign() {
        return design;
    }

    public void setDesign(BigDecimal design) {
        this.design = design;
    }

    public BigDecimal getSoftware() {
        return software;
    }

    public void setSoftware(BigDecimal software) {
        this.software = software;
    }

    public BigDecimal getOtherAmortization() {
        return otherAmortization;
    }

    public void setOtherAmortization(BigDecimal otherAmortization) {
        this.otherAmortization = otherAmortization;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }
    public List<YearCostModel> getCosList(){
        List<YearCostModel> list = new ArrayList<>();
        list.add(new YearCostModel(10000, year, month, this.getWages()));
        list.add(new YearCostModel(10100, year, month, this.getInsuranceAndFund()));
        list.add(new YearCostModel(20000, year, month, this.getMaterial()));
        list.add(new YearCostModel(20100, year, month, this.getPower()));
        list.add(new YearCostModel(20200, year, month, this.getFuel()));
        list.add(new YearCostModel(20300, year, month, this.getTrial()));
        list.add(new YearCostModel(20500, year, month, this.getTest()));
        list.add(new YearCostModel(20600, year, month, this.getRepair()));
        list.add(new YearCostModel(20700, year, month, this.getMachine()));
        list.add(new YearCostModel(30000, year, month, this.getDepreciation()));
        list.add(new YearCostModel(40000, year, month, this.getSoftware()));
        list.add(new YearCostModel(40200, year, month, this.getOtherAmortization()));
        list.add(new YearCostModel(50000, year, month, this.getDesign()));
        list.add(new YearCostModel(69900, year, month, this.getOther()));
        return list;
    }
}
