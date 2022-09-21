package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-25 08:23
 * @Description: 高新评分评价表
 */
@TableName("high_tech_score")
public class HighTechScoreEntity extends BaseEntity {

    @TableId
    private Integer id;
    private Integer companyId;
    private Integer year;

    /**
     * 研发个数
     */
    private Integer rdCnt;
    /**
     * 研发费（万元）
     */
    private BigDecimal rdFunds;
    /**
     * 高品数
     */
    private Integer highTechCnt;
    /**
     * 高品编码
     */
    private String highTechCodes;

    private BigDecimal income;

    /**
     * 先进得分
     */
    private Integer advanced;
    /**
     * 作用得分
     */
    private Integer effect;
    /**
     * 专利数量得分
     */
    private Integer patentAmount;

    /**
     * 获得方式得分
     */
    private Integer acquirement;
    /**
     * 获得方式:0：有自主研发，1：仅有受让、受赠和并购等
     */
    private Integer acquirementMode;
    /**
     * 是否参与编制国家标准、行业标准、检测方法、技术规范的情况（贡献得分）
     */
    private Integer contribution;

    /**
     * 科技成果转化得分
     */
    private Integer scienceResult;
    /**
     * 投入费用，辅助帐得分
     */
    private Integer generalLedger;

    /**
     * 开展合作得分
     */
    private Integer cooperation;

    /**
     * 建立激励开放式创新平台得分
     */
    private Integer excitation;
    /**
     * 培养、奖励得分
     */
    private Integer foster;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getRdCnt() {
        return rdCnt;
    }

    public void setRdCnt(Integer rdCnt) {
        this.rdCnt = rdCnt;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Integer getHighTechCnt() {
        return highTechCnt;
    }

    public void setHighTechCnt(Integer highTechCnt) {
        this.highTechCnt = highTechCnt;
    }

    public String getHighTechCodes() {
        return highTechCodes;
    }

    public void setHighTechCodes(String highTechCodes) {
        this.highTechCodes = highTechCodes;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
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

    public Integer getAcquirementMode() {
        return acquirementMode;
    }

    public void setAcquirementMode(Integer acquirementMode) {
        this.acquirementMode = acquirementMode;
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

}
