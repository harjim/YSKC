package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: wangxing
 * @CreateTime: 2019-09-26 09:57
 * @Description: 财务状况
 */
public class FinancialConditionExcel implements Serializable {
    @Excel(name = "年份", order = 1, fieldName = "year")
    private Integer  year;
    @Excel(name = "营业收入(万元)", order = 2, fieldName = "businessIncome")
    private BigDecimal businessIncome;
    @Excel(name = "主营业务收入(万元)", order = 3, fieldName = "mainBusinessIncome")
    private BigDecimal mainBusinessIncome;
    @Excel(name = "净利润(万元)", order = 4, fieldName = "netProfit")
    private BigDecimal netProfit;
    @Excel(name = "总资产(万元)", order = 7, fieldName = "totalAssets")
    private BigDecimal totalAssets;
    @Excel(name = "净资产(万元)", order = 8, fieldName = "netAssets")
    private  BigDecimal netAssets;
    @Excel(name = "企业所得税(万元)", order = 17, fieldName = "corporateIncomeTax")
    private BigDecimal corporateIncomeTax;

    @Excel(name = "工业总产值(万元)", order = 5, fieldName = "grossOfIndustrial")
    private BigDecimal grossOfIndustrial;
    @Excel(name = " 工业增加值（万元", order = 6, fieldName = "addedOfIndustrial")
    private BigDecimal addedOfIndustrial;

    @Excel(name = "固定资产投资额(万元)", order = 9, fieldName = "fixedAssetsOfInvestment")
    private BigDecimal fixedAssetsOfInvestment;
    @Excel(name = "总现金流量净额(万元)", order = 10, fieldName = "netTotalCashFlow")
    private BigDecimal netTotalCashFlow;
    @Excel(name = "固定资产总额(万元)", order = 8, fieldName = "totalFixedAssets")
    private BigDecimal totalFixedAssets;
    @Excel(name = "经营活动现金流量净额(万元)", order = 11, fieldName = "netCashFlowOfOperating")
    private BigDecimal netCashFlowOfOperating;
    @Excel(name = "资产负债率（%）", order = 12, fieldName = "assetLiabilityRatio")
    private BigDecimal assetLiabilityRatio;
    @Excel(name = "R&D支出总额(万元)", order = 13, fieldName = "totalExpenditureOfRD")
    private BigDecimal totalExpenditureOfRD;
    @Excel(name = "政府借款金额(万元)", order = 14, fieldName = "loanAmountOfGovernment")
    private BigDecimal loanAmountOfGovernment;
    @Excel(name = "到期未还的政府借款额(万元)", order = 15, fieldName = "dueLoanOfGovernment")
    private BigDecimal dueLoanOfGovernment;
    @Excel(name = "纳税总额(万元)", order = 16, fieldName = "totalTax")
    private BigDecimal totalTax;

    @Excel(name = "利润总额(万元)", order = 18, fieldName = "totalProfit")
    private BigDecimal totalProfit;


    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getBusinessIncome() {
        return businessIncome;
    }

    public void setBusinessIncome(BigDecimal businessIncome) {
        this.businessIncome = businessIncome;
    }

    public BigDecimal getMainBusinessIncome() {
        return mainBusinessIncome;
    }

    public void setMainBusinessIncome(BigDecimal mainBusinessIncome) {
        this.mainBusinessIncome = mainBusinessIncome;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public BigDecimal getGrossOfIndustrial() {
        return grossOfIndustrial;
    }

    public void setGrossOfIndustrial(BigDecimal grossOfIndustrial) {
        this.grossOfIndustrial = grossOfIndustrial;
    }

    public BigDecimal getAddedOfIndustrial() {
        return addedOfIndustrial;
    }

    public void setAddedOfIndustrial(BigDecimal addedOfIndustrial) {
        this.addedOfIndustrial = addedOfIndustrial;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getTotalFixedAssets() {
        return totalFixedAssets;
    }

    public void setTotalFixedAssets(BigDecimal totalFixedAssets) {
        this.totalFixedAssets = totalFixedAssets;
    }

    public BigDecimal getFixedAssetsOfInvestment() {
        return fixedAssetsOfInvestment;
    }

    public void setFixedAssetsOfInvestment(BigDecimal fixedAssetsOfInvestment) {
        this.fixedAssetsOfInvestment = fixedAssetsOfInvestment;
    }

    public BigDecimal getNetTotalCashFlow() {
        return netTotalCashFlow;
    }

    public void setNetTotalCashFlow(BigDecimal netTotalCashFlow) {
        this.netTotalCashFlow = netTotalCashFlow;
    }

    public BigDecimal getNetCashFlowOfOperating() {
        return netCashFlowOfOperating;
    }

    public void setNetCashFlowOfOperating(BigDecimal netCashFlowOfOperating) {
        this.netCashFlowOfOperating = netCashFlowOfOperating;
    }

    public BigDecimal getAssetLiabilityRatio() {
        return assetLiabilityRatio;
    }

    public void setAssetLiabilityRatio(BigDecimal assetLiabilityRatio) {
        this.assetLiabilityRatio = assetLiabilityRatio;
    }

    public BigDecimal getTotalExpenditureOfRD() {
        return totalExpenditureOfRD;
    }

    public void setTotalExpenditureOfRD(BigDecimal totalExpenditureOfRD) {
        this.totalExpenditureOfRD = totalExpenditureOfRD;
    }

    public BigDecimal getLoanAmountOfGovernment() {
        return loanAmountOfGovernment;
    }

    public void setLoanAmountOfGovernment(BigDecimal loanAmountOfGovernment) {
        this.loanAmountOfGovernment = loanAmountOfGovernment;
    }

    public BigDecimal getDueLoanOfGovernment() {
        return dueLoanOfGovernment;
    }

    public void setDueLoanOfGovernment(BigDecimal dueLoanOfGovernment) {
        this.dueLoanOfGovernment = dueLoanOfGovernment;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getCorporateIncomeTax() {
        return corporateIncomeTax;
    }

    public void setCorporateIncomeTax(BigDecimal corporateIncomeTax) {
        this.corporateIncomeTax = corporateIncomeTax;
    }

    public BigDecimal getNetAssets() {
        return netAssets;
    }

    public void setNetAssets(BigDecimal netAssets) {
        this.netAssets = netAssets;
    }
}
