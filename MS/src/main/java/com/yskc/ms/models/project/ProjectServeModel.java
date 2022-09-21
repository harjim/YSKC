package com.yskc.ms.models.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/2 9:41
 * description:
 */
public class ProjectServeModel implements Serializable {

    private Integer projectId;

    private Integer basicId;//基本信息id
    private Integer isImplemented;//是否标准化
    private BigDecimal income;//收入
    private BigDecimal incomeTax;//所得税
    private Integer totalStaff;//人数
    private BigDecimal researchFee;//预计研发费
    private BigDecimal taxRefiefs;//减免税
    private String techStaff;//技术人员情况
    private String financeStaff;//财务人员情况
    private String manager;//高层情况
    private Date startTime;//启动时间
    private Integer applyStatus;//高新是否申请
    private Boolean hasPayPatent;//有误专利费
    private String other;//其他
    private Integer techId;//技术id
    private Date tStartTime;//启动时间
    private Date tDockingTime;//对接时间
    private String content;//内容
    private Integer financeId;
    private Date dockingTime;//启动时间
    private BigDecimal estimateRdAmount;//预计研发归集总额
    private List<ProjectTimelineModel> timelineModels;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getBasicId() {
        return basicId;
    }

    public void setBasicId(Integer basicId) {
        this.basicId = basicId;
    }

    public Integer getIsImplemented() {
        return isImplemented;
    }

    public void setIsImplemented(Integer isImplemented) {
        this.isImplemented = isImplemented;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(BigDecimal incomeTax) {
        this.incomeTax = incomeTax;
    }

    public Integer getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(Integer totalStaff) {
        this.totalStaff = totalStaff;
    }

    public BigDecimal getResearchFee() {
        return researchFee;
    }

    public void setResearchFee(BigDecimal researchFee) {
        this.researchFee = researchFee;
    }

    public BigDecimal getTaxRefiefs() {
        return taxRefiefs;
    }

    public void setTaxRefiefs(BigDecimal taxRefiefs) {
        this.taxRefiefs = taxRefiefs;
    }

    public String getTechStaff() {
        return techStaff;
    }

    public void setTechStaff(String techStaff) {
        this.techStaff = techStaff;
    }

    public String getFinanceStaff() {
        return financeStaff;
    }

    public void setFinanceStaff(String financeStaff) {
        this.financeStaff = financeStaff;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public Boolean getHasPayPatent() {
        return hasPayPatent;
    }

    public void setHasPayPatent(Boolean hasPayPatent) {
        this.hasPayPatent = hasPayPatent;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public Date gettStartTime() {
        return tStartTime;
    }

    public void settStartTime(Date tStartTime) {
        this.tStartTime = tStartTime;
    }

    public Date gettDockingTime() {
        return tDockingTime;
    }

    public void settDockingTime(Date tDockingTime) {
        this.tDockingTime = tDockingTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Integer financeId) {
        this.financeId = financeId;
    }

    public Date getDockingTime() {
        return dockingTime;
    }

    public void setDockingTime(Date dockingTime) {
        this.dockingTime = dockingTime;
    }

    public BigDecimal getEstimateRdAmount() {
        return estimateRdAmount;
    }

    public void setEstimateRdAmount(BigDecimal estimateRdAmount) {
        this.estimateRdAmount = estimateRdAmount;
    }

    public List<ProjectTimelineModel> getTimelineModels() {
        return timelineModels;
    }

    public void setTimelineModels(List<ProjectTimelineModel> timelineModels) {
        this.timelineModels = timelineModels;
    }
}
