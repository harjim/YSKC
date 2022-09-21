package com.yskc.ms.models.newexpert.techrequirement;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Administrator
 */
public class EsTechRequirementModel implements Serializable {

    private Integer id;

    /**
     * 需求状态：0：草稿，1：发布，2：服务中，3：完成，4：终止
     * 请注意：，当status=1时且closeDate小于当前日期显示[过期]
     * 不能发布closeDate小于当前日期的需求
     * status=2/3时，不可编辑
     */
    private Integer status;

    //是否可编辑
    private boolean isEditalbe;

    /// 需求名称
    private String requirementName;

    /// 行业分类
    private String industry;

    /// 领域分类
    private String researchArea;

    /**
     *  合作方式:
     *     0：技术转让，1：技术服务，2：技术许可
     *     3：技术融资，4：技术授权，5：其他
     */
    private Integer cooperateType;

    /// 紧急程度 0:一周以内 1:一月以内 2:三个月以内 3:六个月以内 4:一年以内 5:两年以上
    private Integer urgency;

    /// 截至日期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date closeDate;

    /// 需求预算（万元）[当negotiation为false时必须录入]
    private BigDecimal budget;

    // 双方洽谈（0/1判断）
    private Integer negotiation;

    // 关键字【最多三个，用逗号隔开。存储形式：,xxx,xxx,xxx,  显示：xxx,xxx,xxx 查询：like %,{xxx},%】
    private String keywords;

    // 客户名称(单位名称)
    private String customerName;

    //联系人
    private String linkName;

    // 职位（部门职务）
    private String position;

    //  联系电话
    private String linkTel;

    //  联系邮箱
    private String linkEmail;

    // 地址码
    private String addressCode;

    // 详细地址
    private String address;

    //项目背景
    private String background;

    //技术难点
    private String difficulty;

    //指标,预期效果
    private String target;

    //创建时间
    private Date createTime;

    //最后更新时间
    private Date lastUpdateTime;

    //创建人
    private String creatorName;

    //是否发布 0保存，1直接发布
    private Integer issue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public boolean isEditalbe() {
        return isEditalbe;
    }

    public void setEditalbe(boolean editalbe) {
        isEditalbe = editalbe;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public Integer getCooperateType() {
        return cooperateType;
    }

    public void setCooperateType(Integer cooperateType) {
        this.cooperateType = cooperateType;
    }

    public Integer getUrgency() {
        return urgency;
    }

    public void setUrgency(Integer urgency) {
        this.urgency = urgency;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getNegotiation() {
        return negotiation;
    }

    public void setNegotiation(Integer negotiation) {
        this.negotiation = negotiation;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getLinkEmail() {
        return linkEmail;
    }

    public void setLinkEmail(String linkEmail) {
        this.linkEmail = linkEmail;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getIssue() {
        return issue;
    }

    public void setIssue(Integer issue) {
        this.issue = issue;
    }
}
