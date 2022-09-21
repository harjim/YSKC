package com.yskc.docservice.entity.rs;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity
 * @Author: wangxing
 * @CreateTime: 2019-09-25 11:25
 * @Description: FinancialConditionEntity
 */
@TableName("c_financialCondition")
public class FinancialConditionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer msCreatorId;
    /**
     *
     */
    private Integer msLastUpdatorId;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer year;
    /**
     *
     */
    private BigDecimal businessIncome;
    /**
     *
     */
    private BigDecimal mainBusinessIncome;
    /**
     *
     */
    private BigDecimal netProfit;
    /**
     *
     */
    private BigDecimal grossOfIndustrial;
    /**
     *
     */
    private BigDecimal addedOfIndustrial;
    /**
     *
     */
    private BigDecimal totalAssets;
    /**
     *
     */
    private BigDecimal totalFixedAssets;
    /**
     *
     */
    private BigDecimal netAssets;
    /**
     *
     */
    private BigDecimal fixedAssetsOfInvestment;
    /**
     *
     */
    private BigDecimal netTotalCashFlow;
    /**
     *
     */
    private BigDecimal netCashFlowOfOperating;
    /**
     *
     */
    private BigDecimal assetLiabilityRatio;
    /**
     *
     */
    private BigDecimal totalExpenditureOfRD;
    /**
     *
     */
    private BigDecimal loanAmountOfGovernment;
    /**
     *
     */
    private BigDecimal dueLoanOfGovernment;
    /**
     *
     */
    private BigDecimal totalTax;
    /**
     *
     */
    private BigDecimal corporateIncomeTax;


    private BigDecimal totalProfit;

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(BigDecimal totalProfit) {
        this.totalProfit = totalProfit;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setBusinessIncome(BigDecimal businessIncome) {
        this.businessIncome = businessIncome;
    }

    public BigDecimal getBusinessIncome() {
        return businessIncome;
    }

    public void setMainBusinessIncome(BigDecimal mainBusinessIncome) {
        this.mainBusinessIncome = mainBusinessIncome;
    }

    public BigDecimal getMainBusinessIncome() {
        return mainBusinessIncome;
    }

    public void setNetProfit(BigDecimal netProfit) {
        this.netProfit = netProfit;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }

    public void setGrossOfIndustrial(BigDecimal grossOfIndustrial) {
        this.grossOfIndustrial = grossOfIndustrial;
    }

    public BigDecimal getGrossOfIndustrial() {
        return grossOfIndustrial;
    }

    public void setAddedOfIndustrial(BigDecimal addedOfIndustrial) {
        this.addedOfIndustrial = addedOfIndustrial;
    }

    public BigDecimal getAddedOfIndustrial() {
        return addedOfIndustrial;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalFixedAssets(BigDecimal totalFixedAssets) {
        this.totalFixedAssets = totalFixedAssets;
    }

    public BigDecimal getTotalFixedAssets() {
        return totalFixedAssets;
    }

    public void setNetAssets(BigDecimal netAssets) {
        this.netAssets = netAssets;
    }

    public BigDecimal getNetAssets() {
        return netAssets;
    }

    public void setFixedAssetsOfInvestment(BigDecimal fixedAssetsOfInvestment) {
        this.fixedAssetsOfInvestment = fixedAssetsOfInvestment;
    }

    public BigDecimal getFixedAssetsOfInvestment() {
        return fixedAssetsOfInvestment;
    }

    public void setNetTotalCashFlow(BigDecimal netTotalCashFlow) {
        this.netTotalCashFlow = netTotalCashFlow;
    }

    public BigDecimal getNetTotalCashFlow() {
        return netTotalCashFlow;
    }

    public void setNetCashFlowOfOperating(BigDecimal netCashFlowOfOperating) {
        this.netCashFlowOfOperating = netCashFlowOfOperating;
    }

    public BigDecimal getNetCashFlowOfOperating() {
        return netCashFlowOfOperating;
    }

    public void setAssetLiabilityRatio(BigDecimal assetLiabilityRatio) {
        this.assetLiabilityRatio = assetLiabilityRatio;
    }

    public BigDecimal getAssetLiabilityRatio() {
        return assetLiabilityRatio;
    }

    public void setTotalExpenditureOfRD(BigDecimal totalExpenditureOfRD) {
        this.totalExpenditureOfRD = totalExpenditureOfRD;
    }

    public BigDecimal getTotalExpenditureOfRD() {
        return totalExpenditureOfRD;
    }

    public void setLoanAmountOfGovernment(BigDecimal loanAmountOfGovernment) {
        this.loanAmountOfGovernment = loanAmountOfGovernment;
    }

    public BigDecimal getLoanAmountOfGovernment() {
        return loanAmountOfGovernment;
    }

    public void setDueLoanOfGovernment(BigDecimal dueLoanOfGovernment) {
        this.dueLoanOfGovernment = dueLoanOfGovernment;
    }

    public BigDecimal getDueLoanOfGovernment() {
        return dueLoanOfGovernment;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setCorporateIncomeTax(BigDecimal corporateIncomeTax) {
        this.corporateIncomeTax = corporateIncomeTax;
    }

    public BigDecimal getCorporateIncomeTax() {
        return corporateIncomeTax;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public void update(Integer userId, Integer msUserId, Date date) {
        this.lastUpdatorId = userId;
        this.lastUpdateTime = date;
        this.msLastUpdatorId = msUserId;
    }

    public void create(Integer userId, Integer msUserId, Date date) {
        this.lastUpdatorId = userId;
        this.lastUpdateTime = date;
        this.msLastUpdatorId = msUserId;
        this.creatorId = userId;
        this.createTime = date;
        this.msCreatorId = msUserId;
    }
}
