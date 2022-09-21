package com.yskc.rs.models.summary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.summary
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 10:41
 * @Description:辅助账model
 */
public class TotalSummaryModel implements Serializable {

    private Integer id;

    private String rdTitle;

    private Integer rdIndex;

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
     * 材料
     * 200
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal material;
    /**
     * 动力
     * 201
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal stimulus;

    /**
     * 燃料
     * 202
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal fuel;

    /**
     * 试制
     * 203
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal trialProd;

    /**
     * 试检
     * 204
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal trialTest;

    /**
     * 检测
     * 205
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal inspection;

    /**
     * 设备折旧
     * 300
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal prod;

    /**
     * 仪器折旧
     * 301
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal lab;

    /**
     * 软件摊销
     * 400
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal softAmortization;
    /**
     * 专利摊销
     * 401
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal patentAmortization;
    /**
     * 其他摊销
     * 402
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal otherAmortization;

    /**
     * 设计
     * 500
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal design;

    /**
     * 规程制定
     * 501
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal techProcedure;

    /**
     * 临床
     * 502
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal clinicalTrials;

    /**
     * 勘探
     * 503
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal explore;

    /**
     * 资料
     * 600
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal book;

    /**
     * 研发成果
     * 601
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdProduction;

    /**
     * 知识产权
     * 602
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal copyRight;

    /**
     * 福利
     * 603
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal benefits;

    /**
     * 差旅费
     * 604
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal travel;

    /**
     * 其他
     * 699
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal other;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal salaryValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal insuranceValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal materialValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal stimulusValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal fuelValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal trialProdValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal trialTestValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal inspectionValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal prodValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal labValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal softAmortizationValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal patentAmortizationValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal otherAmortizationValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal designValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal techProcedureValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal clinicalTrialsValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal exploreValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal bookValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdProductionValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal copyRightValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal benefitsValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal travelValue;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal otherValue;


    public BigDecimal getSalaryValue() {
        return salaryValue;
    }

    public void setSalaryValue(BigDecimal salaryValue) {
        this.salaryValue = salaryValue;
    }

    public BigDecimal getInsuranceValue() {
        return insuranceValue;
    }

    public void setInsuranceValue(BigDecimal insuranceValue) {
        this.insuranceValue = insuranceValue;
    }

    public BigDecimal getMaterialValue() {
        return materialValue;
    }

    public void setMaterialValue(BigDecimal materialValue) {
        this.materialValue = materialValue;
    }

    public BigDecimal getStimulusValue() {
        return stimulusValue;
    }

    public void setStimulusValue(BigDecimal stimulusValue) {
        this.stimulusValue = stimulusValue;
    }

    public BigDecimal getFuelValue() {
        return fuelValue;
    }

    public void setFuelValue(BigDecimal fuelValue) {
        this.fuelValue = fuelValue;
    }

    public BigDecimal getTrialProdValue() {
        return trialProdValue;
    }

    public void setTrialProdValue(BigDecimal trialProdValue) {
        this.trialProdValue = trialProdValue;
    }

    public BigDecimal getTrialTestValue() {
        return trialTestValue;
    }

    public void setTrialTestValue(BigDecimal trialTestValue) {
        this.trialTestValue = trialTestValue;
    }

    public BigDecimal getInspectionValue() {
        return inspectionValue;
    }

    public void setInspectionValue(BigDecimal inspectionValue) {
        this.inspectionValue = inspectionValue;
    }

    public BigDecimal getProdValue() {
        return prodValue;
    }

    public void setProdValue(BigDecimal prodValue) {
        this.prodValue = prodValue;
    }

    public BigDecimal getLabValue() {
        return labValue;
    }

    public void setLabValue(BigDecimal labValue) {
        this.labValue = labValue;
    }

    public BigDecimal getSoftAmortizationValue() {
        return softAmortizationValue;
    }

    public void setSoftAmortizationValue(BigDecimal softAmortizationValue) {
        this.softAmortizationValue = softAmortizationValue;
    }

    public BigDecimal getPatentAmortizationValue() {
        return patentAmortizationValue;
    }

    public void setPatentAmortizationValue(BigDecimal patentAmortizationValue) {
        this.patentAmortizationValue = patentAmortizationValue;
    }

    public BigDecimal getOtherAmortizationValue() {
        return otherAmortizationValue;
    }

    public void setOtherAmortizationValue(BigDecimal otherAmortizationValue) {
        this.otherAmortizationValue = otherAmortizationValue;
    }

    public BigDecimal getDesignValue() {
        return designValue;
    }

    public void setDesignValue(BigDecimal designValue) {
        this.designValue = designValue;
    }

    public BigDecimal getTechProcedureValue() {
        return techProcedureValue;
    }

    public void setTechProcedureValue(BigDecimal techProcedureValue) {
        this.techProcedureValue = techProcedureValue;
    }

    public BigDecimal getClinicalTrialsValue() {
        return clinicalTrialsValue;
    }

    public void setClinicalTrialsValue(BigDecimal clinicalTrialsValue) {
        this.clinicalTrialsValue = clinicalTrialsValue;
    }

    public BigDecimal getExploreValue() {
        return exploreValue;
    }

    public void setExploreValue(BigDecimal exploreValue) {
        this.exploreValue = exploreValue;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public BigDecimal getRdProductionValue() {
        return rdProductionValue;
    }

    public void setRdProductionValue(BigDecimal rdProductionValue) {
        this.rdProductionValue = rdProductionValue;
    }

    public BigDecimal getCopyRightValue() {
        return copyRightValue;
    }

    public void setCopyRightValue(BigDecimal copyRightValue) {
        this.copyRightValue = copyRightValue;
    }

    public BigDecimal getBenefitsValue() {
        return benefitsValue;
    }

    public void setBenefitsValue(BigDecimal benefitsValue) {
        this.benefitsValue = benefitsValue;
    }

    public BigDecimal getTravelValue() {
        return travelValue;
    }

    public void setTravelValue(BigDecimal travelValue) {
        this.travelValue = travelValue;
    }

    public BigDecimal getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(BigDecimal otherValue) {
        this.otherValue = otherValue;
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

    public BigDecimal getTrialProd() {
        return trialProd;
    }

    public void setTrialProd(BigDecimal trialProd) {
        this.trialProd = trialProd;
    }

    public BigDecimal getTrialTest() {
        return trialTest;
    }

    public void setTrialTest(BigDecimal trialTest) {
        this.trialTest = trialTest;
    }

    public BigDecimal getInspection() {
        return inspection;
    }

    public void setInspection(BigDecimal inspection) {
        this.inspection = inspection;
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

    public BigDecimal getSoftAmortization() {
        return softAmortization;
    }

    public void setSoftAmortization(BigDecimal softAmortization) {
        this.softAmortization = softAmortization;
    }

    public BigDecimal getPatentAmortization() {
        return patentAmortization;
    }

    public void setPatentAmortization(BigDecimal patentAmortization) {
        this.patentAmortization = patentAmortization;
    }

    public BigDecimal getOtherAmortization() {
        return otherAmortization;
    }

    public void setOtherAmortization(BigDecimal otherAmortization) {
        this.otherAmortization = otherAmortization;
    }

    public BigDecimal getDesign() {
        return design;
    }

    public void setDesign(BigDecimal design) {
        this.design = design;
    }

    public BigDecimal getTechProcedure() {
        return techProcedure;
    }

    public void setTechProcedure(BigDecimal techProcedure) {
        this.techProcedure = techProcedure;
    }

    public BigDecimal getClinicalTrials() {
        return clinicalTrials;
    }

    public void setClinicalTrials(BigDecimal clinicalTrials) {
        this.clinicalTrials = clinicalTrials;
    }

    public BigDecimal getExplore() {
        return explore;
    }

    public void setExplore(BigDecimal explore) {
        this.explore = explore;
    }

    public BigDecimal getBook() {
        return book;
    }

    public void setBook(BigDecimal book) {
        this.book = book;
    }

    public BigDecimal getRdProduction() {
        return rdProduction;
    }

    public void setRdProduction(BigDecimal rdProduction) {
        this.rdProduction = rdProduction;
    }

    public BigDecimal getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(BigDecimal copyRight) {
        this.copyRight = copyRight;
    }

    public BigDecimal getBenefits() {
        return benefits;
    }

    public void setBenefits(BigDecimal benefits) {
        this.benefits = benefits;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static void sum(TotalSummaryModel resource, TotalSummaryModel target) {
        target.salary = (target.salary == null ? resource.salary : target.salary.add(resource.salary));
        target.insurance = (target.insurance == null ? resource.insurance : target.insurance.add(resource.insurance));
        target.material = (target.material == null ? resource.material : target.material.add(resource.material));
        target.stimulus = (target.stimulus == null ? resource.stimulus : target.stimulus.add(resource.stimulus));
        target.fuel = (target.fuel == null ? resource.fuel : target.fuel.add(resource.fuel));
        target.trialProd = (target.trialProd == null ? resource.trialProd : target.trialProd.add(resource.trialProd));
        target.trialTest = (target.trialTest == null ? resource.trialTest : target.trialTest.add(resource.trialTest));
        target.inspection = (target.inspection == null ? resource.inspection : target.inspection.add(resource.inspection));
        target.prod = (target.prod == null ? resource.prod : target.prod.add(resource.prod));
        target.lab = (target.lab == null ? resource.lab : target.lab.add(resource.lab));
        target.softAmortization = (target.softAmortization == null ? resource.softAmortization : target.softAmortization.add(resource.softAmortization));
        target.patentAmortization = (target.patentAmortization == null ? resource.patentAmortization : target.patentAmortization.add(resource.patentAmortization));
        target.otherAmortization = (target.otherAmortization == null ? resource.otherAmortization : target.otherAmortization.add(resource.otherAmortization));
        target.design = (target.design == null ? resource.design : target.design.add(resource.design));
        target.techProcedure = (target.techProcedure == null ? resource.techProcedure : target.techProcedure.add(resource.techProcedure));
        target.clinicalTrials = (target.clinicalTrials == null ? resource.clinicalTrials
                : target.clinicalTrials.add(resource.clinicalTrials));
        target.explore = (target.explore == null ? resource.explore : target.explore.add(resource.explore));
        target.book = (target.book == null ? resource.book : target.book.add(resource.book));
        target.rdProduction = (target.rdProduction == null ? resource.rdProduction : target.rdProduction.add(resource.rdProduction));
        target.copyRight = (target.copyRight == null ? resource.copyRight : target.copyRight.add(resource.copyRight));
        target.benefits = (target.benefits == null ? resource.benefits : target.benefits.add(resource.benefits));
        target.travel = (target.travel == null ? resource.travel : target.travel.add(resource.travel));
        target.other = (target.other == null ? resource.other : target.other.add(resource.other));
    }

    public static TotalSummaryModel sumAndSetRdName(List<TotalSummaryModel> totalSummaries) {
        TotalSummaryModel target = new TotalSummaryModel();
        totalSummaries.forEach(item -> {
            sum(item, target);
        });
        target.setRdIndex(0);
        return target;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }
}
