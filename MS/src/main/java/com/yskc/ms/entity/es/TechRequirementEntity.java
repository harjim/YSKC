package com.yskc.ms.entity.es;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@TableName("tech_requirement")
public class TechRequirementEntity implements Serializable {

    private static final long serialVersionUID = 2905440343881299723L;

    @TableId
    public Integer id;

    /**
     * 需求状态：0：草稿，1：发布，2：服务中，3：完成，4：终止
     * 请注意：，当status=1时且closeDate小于当前日期显示[过期]
     * 不能发布closeDate小于当前日期的需求
     * status=2/3时，不可编辑
     */
    public Integer status;
    
    /// 需求名称
    public String requirementName;
    
    /// 行业分类
    public String industry;
    
    /// 领域分类
    public String researchArea;

    /**
     *  合作方式:
     *     0：技术转让，1：技术服务，2：技术许可
     *     3：技术融资，4：技术授权，5：其他
     */
    public Integer cooperateType;

    /// 紧急程度 0:一周以内 1:一月以内 2:三个月以内 3:六个月以内 4:一年以内 5:两年以上
    public Integer urgency;
    
    /// 截至日期
    public Date closeDate;

    /// 需求预算（万元）[当negotiation为false时必须录入]
    public BigDecimal budget;

    // 双方洽谈（价格）
    public Integer negotiation;

    // 关键字【最多三个，用逗号隔开。存储形式：,xxx,xxx,xxx,  显示：xxx,xxx,xxx 查询：like %,{xxx},%】
    public String keywords;

    // 客户名称
    public String customerName;

    //联系人
    public String linkName;

    // 职位（部门职务）
    public String position;

    //  联系电话
    public String linkTel;

    //  联系邮箱
    public String linkEmail;

    // 地址码
    public String addressCode;

    // 详细地址
    public String address;

    //项目背景
    public String background;

    //技术难点
    public String difficulty;

    //指标,预期效果
    public String target;

    public Date createTime;

    public Date lastUpdateTime;

    // 管理端用户
    public Integer msCreatorId;

    // 管理端用户
    public Integer msLastUpdatorId;

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

    /**
     * 创建实例
     * @param now
     */
    public void create(Date now,Integer id) {
        this.createTime = now;
        this.msCreatorId = id;
        this.update(now,id);
    }

    /**
     * 更新实例
     * @param now
     */
    public void update(Date now,Integer id){
        this.lastUpdateTime = now;
        this.msLastUpdatorId = id;
    }
}
