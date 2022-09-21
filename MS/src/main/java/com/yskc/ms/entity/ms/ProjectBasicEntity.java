package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/10/31 9:58
 * description:项目基本信息
 */
@TableName("project_basic")
public class ProjectBasicEntity {
    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer projectId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
}
