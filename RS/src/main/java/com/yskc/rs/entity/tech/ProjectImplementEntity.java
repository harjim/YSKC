package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.tech
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:13
 * @Description: 项目实施结果实体类
 */
@TableName("t_projectImplement")
public class ProjectImplementEntity implements Serializable {
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
    private Integer projectId;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String businessIncome;
    /**
     *
     */
    private String profit;
    private String drivenFundsInput;
    /**
     *
     */
    private String taxRevenue;
    /**
     *
     */
    private String expect;
    /**
     *
     */
    private String lineOfInfo;
    /**
     *
     */
    private String manpowerSavings;
    /**
     *
     */
    private String goodOfRate;
    /**
     *
     */
    private String consumptionPer;
    /**
     *
     */
    private String carbonDioxide;
    /**
     *
     */
    private String unitWaterUse;
    /**
     *
     */
    private String casualti;
    /**
     *
     */
    private String weedOut;
    /**
     *
     */
    private String digitalize;
    /**
     *
     */
    private String numericalControl;
    /**
     *
     */
    private String talentTeam;
    /**
     *
     */
    private String remark;

    public String getDrivenFundsInput() {
        return drivenFundsInput;
    }

    public void setDrivenFundsInput(String drivenFundsInput) {
        this.drivenFundsInput = drivenFundsInput;
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

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setBusinessIncome(String businessIncome) {
        this.businessIncome = businessIncome;
    }

    public String getBusinessIncome() {
        return businessIncome;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getProfit() {
        return profit;
    }

    public void setTaxRevenue(String taxRevenue) {
        this.taxRevenue = taxRevenue;
    }

    public String getTaxRevenue() {
        return taxRevenue;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getExpect() {
        return expect;
    }

    public void setLineOfInfo(String lineOfInfo) {
        this.lineOfInfo = lineOfInfo;
    }

    public String getLineOfInfo() {
        return lineOfInfo;
    }

    public void setManpowerSavings(String manpowerSavings) {
        this.manpowerSavings = manpowerSavings;
    }

    public String getManpowerSavings() {
        return manpowerSavings;
    }

    public void setGoodOfRate(String goodOfRate) {
        this.goodOfRate = goodOfRate;
    }

    public String getGoodOfRate() {
        return goodOfRate;
    }

    public void setConsumptionPer(String consumptionPer) {
        this.consumptionPer = consumptionPer;
    }

    public String getConsumptionPer() {
        return consumptionPer;
    }

    public void setCarbonDioxide(String carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public String getCarbonDioxide() {
        return carbonDioxide;
    }

    public void setUnitWaterUse(String unitWaterUse) {
        this.unitWaterUse = unitWaterUse;
    }

    public String getUnitWaterUse() {
        return unitWaterUse;
    }

    public void setCasualti(String casualti) {
        this.casualti = casualti;
    }

    public String getCasualti() {
        return casualti;
    }

    public void setWeedOut(String weedOut) {
        this.weedOut = weedOut;
    }

    public String getWeedOut() {
        return weedOut;
    }

    public void setDigitalize(String digitalize) {
        this.digitalize = digitalize;
    }

    public String getDigitalize() {
        return digitalize;
    }

    public void setNumericalControl(String numericalControl) {
        this.numericalControl = numericalControl;
    }

    public String getNumericalControl() {
        return numericalControl;
    }

    public void setTalentTeam(String talentTeam) {
        this.talentTeam = talentTeam;
    }

    public String getTalentTeam() {
        return talentTeam;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
}
