package com.yskc.rs.models.highscore;

import java.math.BigDecimal;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.highscore
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-25 08:33
 * @Description: 高新评分评价model
 */
public class HighTechScoreModel {

    private Integer id;
    private Integer year;
    private Boolean  register;
    private Boolean patent;
    private Boolean fitMember;

    private Double rdRatio;
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

    private Integer addScienceResult;
    private Boolean testFile;
    private Boolean advancedFile;
    private Boolean leadFile;
    private Boolean exportFile;
    private Boolean exportWestFile;
    private List<String> tecIndustries;
    public HighTechScoreModel() {
    }

    public HighTechScoreModel(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getFitMember() {
        return fitMember;
    }

    public void setFitMember(Boolean fitMember) {
        this.fitMember = fitMember;
    }

    public Double getRdRatio() {
        return rdRatio;
    }

    public void setRdRatio(Double rdRatio) {
        this.rdRatio = rdRatio;
        this.fitMember = rdRatio != null && rdRatio > 0.1;
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

    public Integer getAddScienceResult() {
        return addScienceResult;
    }

    public void setAddScienceResult(Integer addScienceResult) {
        this.addScienceResult = addScienceResult;
    }

    public Boolean getTestFile() {
        return testFile;
    }

    public void setTestFile(Boolean testFile) {
        this.testFile = testFile;
    }

    public Boolean getAdvancedFile() {
        return advancedFile;
    }

    public void setAdvancedFile(Boolean advancedFile) {
        this.advancedFile = advancedFile;
    }

    public Boolean getLeadFile() {
        return leadFile;
    }

    public void setLeadFile(Boolean leadFile) {
        this.leadFile = leadFile;
    }

    public Boolean getExportFile() {
        return exportFile;
    }

    public void setExportFile(Boolean exportFile) {
        this.exportFile = exportFile;
    }

    public Boolean getExportWestFile() {
        return exportWestFile;
    }

    public void setExportWestFile(Boolean exportWestFile) {
        this.exportWestFile = exportWestFile;
    }

    public List<String> getTecIndustries() {
        return tecIndustries;
    }

    public void setTecIndustries(List<String> tecIndustries) {
        this.tecIndustries = tecIndustries;
    }

    public void addGeneralLedger(int score) {
        this.generalLedger = null == this.generalLedger ? score : this.generalLedger + score;
    }

    public void addCooperation(int score) {
        this.cooperation = null == this.cooperation ? score : this.cooperation + score;
    }

    public void addExcitation(int score) {
        this.excitation = null == this.excitation ? score : this.excitation + score;
    }

    public void addFoster(int score) {
        this.foster = null == this.foster ? score : this.foster + score;
    }
}
