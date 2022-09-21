package com.yskc.rs.models.highscore;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.highscore
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-05 13:51
 * @Description: 高新综合评分评价
 */
public class HighTotalScoreModel {
    private Integer year;
    private Boolean register;
    private Boolean patent;
    private Double memberRatio;
    /**
     * 近三年研发费/近一年销售收入比例
     * 销售收入<= 5000万，比例>=5%
     * 销售收入> 5000万 && <=20000万，比例>=4%
     * 销售收入> 20000万,比例>=3%
     */
    private Double rdFeeSalesRatio;
    /**
     * 国内研发/总研发费 占比 达到60% 时，为true（默认国内研发=总研发费）
     */
    private Double rdFeeRatio;
    /**
     * 高新收入占同期总收入>=60%
     */
    private Double highFeeIncomeRatio;
    private Boolean fitMember;
    private Boolean fitRdFeeSales;
    private Boolean fitRdFee;
    private Boolean fitHighFeeIncome;
    private Integer advanced;
    private Integer effect;
    private Integer patentAmount;
    private Integer acquirement;
    private Integer contribution;
    private Integer scienceResult;
    private Integer generalLedger;
    private Integer cooperation;
    private Integer excitation;
    private Integer foster;
    @JsonProperty("NAVScore")
    private Integer NAVScore;
    private Integer salesScore;
    private Integer addScienceResult;
    private List<String> tecIndustries;

    public static HighTotalScoreModel build(HighTechScoreModel highTechScore, HighFinanceScoreModel financeScore) {
        HighTotalScoreModel totalScore = new HighTotalScoreModel();
        // 技术
        totalScore.year = highTechScore.getYear();
        totalScore.register = highTechScore.getRegister();
        totalScore.patent = highTechScore.getPatent();
        totalScore.tecIndustries = highTechScore.getTecIndustries();
        totalScore.memberRatio = highTechScore.getRdRatio();
        totalScore.fitMember = highTechScore.getFitMember();
        totalScore.advanced = highTechScore.getAdvanced();
        totalScore.effect = highTechScore.getEffect();
        totalScore.patentAmount = highTechScore.getPatentAmount();
        totalScore.acquirement = highTechScore.getAcquirement();
        totalScore.contribution = highTechScore.getContribution();
        totalScore.excitation = highTechScore.getExcitation();
        totalScore.foster = highTechScore.getFoster();
        totalScore.generalLedger = highTechScore.getGeneralLedger();
        totalScore.cooperation = highTechScore.getCooperation();
        totalScore.scienceResult = highTechScore.getScienceResult();
        totalScore.addScienceResult = highTechScore.getAddScienceResult();
        BigDecimal highTechIncome = highTechScore.getIncome();
        BigDecimal totalIncome = financeScore.getIncome();
        if (highTechIncome != null && highTechIncome.compareTo(BigDecimal.ZERO) > 0 && totalIncome != null && totalIncome.compareTo(BigDecimal.ZERO) > 0) {
            totalScore.highFeeIncomeRatio = highTechIncome.divide(totalIncome, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
            totalScore.fitHighFeeIncome = totalScore.highFeeIncomeRatio >= 0.6;
        }
        BigDecimal rdFunds = highTechScore.getRdFunds();
        if (rdFunds != null && rdFunds.compareTo(BigDecimal.ZERO) > 0) {
            // 默认国内研发=总研发费
            totalScore.fitRdFee = true;
            totalScore.rdFeeRatio = 1.0;
            BigDecimal salesYear1 = financeScore.getSalesYear1();
            if (salesYear1 != null && salesYear1.compareTo(BigDecimal.ZERO) > 0) {
                totalScore.rdFeeSalesRatio = rdFunds.divide(salesYear1, 4, BigDecimal.ROUND_HALF_UP).doubleValue();
                if (salesYear1.compareTo(BigDecimal.valueOf(5000)) <= 0) {
                    totalScore.fitRdFeeSales = totalScore.rdFeeSalesRatio >= 0.05;
                } else if (salesYear1.compareTo(BigDecimal.valueOf(20000)) <= 0) {
                    totalScore.fitRdFeeSales = totalScore.rdFeeSalesRatio >= 0.04;
                } else {
                    totalScore.fitRdFeeSales = totalScore.rdFeeSalesRatio >= 0.03;
                }
            }
        }
        // 财务
        totalScore.NAVScore = financeScore.getnAVScore();
        totalScore.salesScore = financeScore.getSalesScore();
        return totalScore;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getRegister() {
        return register;
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    public Boolean getPatent() {
        return patent;
    }

    public void setPatent(Boolean patent) {
        this.patent = patent;
    }

    public Integer getAdvanced() {
        return advanced;
    }

    public void setAdvanced(Integer advanced) {
        this.advanced = advanced;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }

    public Integer getPatentAmount() {
        return patentAmount;
    }

    public void setPatentAmount(Integer patentAmount) {
        this.patentAmount = patentAmount;
    }

    public Integer getAcquirement() {
        return acquirement;
    }

    public void setAcquirement(Integer acquirement) {
        this.acquirement = acquirement;
    }

    public Integer getContribution() {
        return contribution;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public Integer getScienceResult() {
        return scienceResult;
    }

    public void setScienceResult(Integer scienceResult) {
        this.scienceResult = scienceResult;
    }

    public Integer getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(Integer generalLedger) {
        this.generalLedger = generalLedger;
    }

    public Integer getCooperation() {
        return cooperation;
    }

    public void setCooperation(Integer cooperation) {
        this.cooperation = cooperation;
    }

    public Integer getExcitation() {
        return excitation;
    }

    public void setExcitation(Integer excitation) {
        this.excitation = excitation;
    }

    public Integer getFoster() {
        return foster;
    }

    public void setFoster(Integer foster) {
        this.foster = foster;
    }

    public Integer getNAVScore() {
        return NAVScore;
    }

    public void setNAVScore(Integer NAVScore) {
        this.NAVScore = NAVScore;
    }

    public Integer getSalesScore() {
        return salesScore;
    }

    public void setSalesScore(Integer salesScore) {
        this.salesScore = salesScore;
    }

    public Double getMemberRatio() {
        return memberRatio;
    }

    public void setMemberRatio(Double memberRatio) {
        this.memberRatio = memberRatio;
    }

    public Double getRdFeeSalesRatio() {
        return rdFeeSalesRatio;
    }

    public void setRdFeeSalesRatio(Double rdFeeSalesRatio) {
        this.rdFeeSalesRatio = rdFeeSalesRatio;
    }

    public Double getRdFeeRatio() {
        return rdFeeRatio;
    }

    public void setRdFeeRatio(Double rdFeeRatio) {
        this.rdFeeRatio = rdFeeRatio;
    }

    public Double getHighFeeIncomeRatio() {
        return highFeeIncomeRatio;
    }

    public void setHighFeeIncomeRatio(Double highFeeIncomeRatio) {
        this.highFeeIncomeRatio = highFeeIncomeRatio;
    }

    public Boolean getFitMember() {
        return fitMember;
    }

    public void setFitMember(Boolean fitMember) {
        this.fitMember = fitMember;
    }

    public Boolean getFitRdFeeSales() {
        return fitRdFeeSales;
    }

    public void setFitRdFeeSales(Boolean fitRdFeeSales) {
        this.fitRdFeeSales = fitRdFeeSales;
    }

    public Boolean getFitRdFee() {
        return fitRdFee;
    }

    public void setFitRdFee(Boolean fitRdFee) {
        this.fitRdFee = fitRdFee;
    }

    public Boolean getFitHighFeeIncome() {
        return fitHighFeeIncome;
    }

    public void setFitHighFeeIncome(Boolean fitHighFeeIncome) {
        this.fitHighFeeIncome = fitHighFeeIncome;
    }

    public Integer getAddScienceResult() {
        return addScienceResult;
    }

    public void setAddScienceResult(Integer addScienceResult) {
        this.addScienceResult = addScienceResult;
    }

    public List<String> getTecIndustries() {
        return tecIndustries;
    }

    public void setTecIndustries(List<String> tecIndustries) {
        this.tecIndustries = tecIndustries;
    }
}
