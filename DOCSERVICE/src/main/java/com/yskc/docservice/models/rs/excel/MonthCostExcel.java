package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;
import com.yskc.docservice.entity.rs.MonthCostEntity;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2012-8-16
 * @Description: 月度成本excel
 */
public class MonthCostExcel implements Serializable {
    @Excel( name = "年份", order = 0, fieldName = "year" )
    private String year;

    @Excel( name = "月份", order = 1, fieldName = "month" )
    private String month;

    @Excel( name = "工资", order = 2, fieldName = "wages" )
    private String wages;

    @Excel( name = "五险一金", order = 3, fieldName = "insuranceAndFund" )
    private String insuranceAndFund;

    @Excel( name = "原材料成本", order = 4, fieldName = "material" )
    private String material;

    @Excel( name = "折旧费", order = 5, fieldName = "depreciation" )
    private String depreciation;

    @Excel( name = "动力费", order = 6, fieldName = "power" )
    private String power;

    @Excel( name = "燃料费", order = 7, fieldName = "fuel" )
    private String fuel;

    @Excel( name = "备品件", order = 8, fieldName = "trial" )
    private String trial;

    @Excel( name = "检测费对应成本", order = 9, fieldName = "test" )
    private String test;

    @Excel( name = "修理费", order = 10, fieldName = "repair" )
    private String repair;

    @Excel( name = "样机费", order = 11, fieldName = "machine" )
    private String machine;

    @Excel( name = "设计费", order = 12, fieldName = "design" )
    private String design;

    @Excel( name = "软件费", order = 13, fieldName = "software" )
    private String software;

    @Excel( name = "其他摊销费", order = 14, fieldName = "otherAmortization" )
    private String otherAmortization;

    @Excel( name = "其他费用", order = 15, fieldName = "other" )
    private String other;

    public final static Map< Integer, String > rdType = new HashMap() {{
        put("10000", "wages");
        put("10100", "insuranceAndFund");
        put("20000", "material");
        put("20100", "power");
        put("20200", "fuel");
        put("20300", "trial");
        put("20500", "test");
        put("20600", "repair");
        put("20700", "machine");
        put("30000", "depreciation");
        put("40000", "software");
        put("40200", "otherAmortization");
        put("50000", "design");
        put("69900", "other");
    }};


    /**
     * excel对象转entity对象
     * @return
     */
    public List< MonthCostEntity > excelToEntity(Integer creatorId, Integer msCreatorId, Integer companyId) {
        List< MonthCostEntity > monthCostEntityList = new ArrayList<>();

            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 10000, this.getWages(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 10100, this.getInsuranceAndFund(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 20000, this.getMaterial(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 20100, this.getPower(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 20200, this.getFuel(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 20300, this.getTrial(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 20500, this.getTest(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 20600, this.getRepair(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 20700, this.getMachine(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 30000, this.getDepreciation(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 40000, this.getSoftware(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 40200, this.getOtherAmortization(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 50000, this.getDesign(), Integer.parseInt(this.getMonth())));
            monthCostEntityList.add(new MonthCostEntity(creatorId, msCreatorId, companyId, Integer.parseInt(this.getYear()), 69900, this.getOther(), Integer.parseInt(this.getMonth())));

        return monthCostEntityList;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

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
