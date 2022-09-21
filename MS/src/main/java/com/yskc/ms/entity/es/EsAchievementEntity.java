package com.yskc.ms.entity.es;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.entity.es.models.EsBaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2021/11/12 8:57
 * @Description:
 * @author: hsx
 */
@TableName("achievement")
public class EsAchievementEntity extends EsBaseEntity implements Serializable {

    private static final long serialVersionUID = 3093358906233561456L;

    @TableId
    private Integer id;

    private Integer userId;

    /**
     * 状态：0：审核中，1：审核通过（已发布），2：不通过（驳回），3：撤回，5：草稿
     * 0,1,2,5遵循流程实例状态设计，3为业务本身所需状态编号。
     * 状态流程：（通过）5/2/3->0->1->3->0...，（驳回）5/2/3->0->2->0...
     * 状态为5,2,3时，开始一轮审核发布流程
     */
    private Integer status;

    // 成果名称
    private String achievementName;

    // 行业分类
    private String industry;

    // 领域分类
    private String researchArea;

    // 成果类型: 0：发明专利，1：实用新型专利，2：软件著作权，3：著作权，4：商标权，5：新品种，6：外观设计，7：新技术
    private Integer type;

    /**
     * 技术成熟度: 1：1级-基本原理被发现或报告，2：2级-技术概念或用途被阐明，3：3级-关键功能或特征的概念验证
     * 4：4级-实验室环境下的部件或实验模型验证，5：5级-相关环境下的部件或时间模型验证，6：6级-相关环境下的系统/子系统模型或样机验证
     * 7：7级-模拟使用环境下的系统样机验证，8：8级-实际系统完成实验验证，9：9级-实际系统完成使用验证
     */
    private Integer trl;

    //起止日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 资助情况：0：863，1：973，2：科技重大项目，3：自然科学基金，
     * 4：国家科技支撑计划，5：科技型中小企业创新基金，6：其他
     */
    private Integer fundingType;

    // 是否协商（价格）
    private Integer negotiation;

    // 意向价格（标价）[当negotiation为false时必须录入]
    private BigDecimal price;

    /**
     * 合作方式:
     * 0：技术转让，1：技术服务，2：技术许可
     * 3：技术融资，4：技术授权，5：其他
     */
    private Integer cooperateType;

    // 关键字【最多三个，用逗号隔开。存储形式：,xxx,xxx,xxx,  显示：xxx,xxx,xxx 查询：like %,{xxx},%】
    private String keywords;

    // 成果来源
    private String source;

    // 部门职务（职位）
    private String job;

    // 持有人(所属人)【可以是】
    private String ownerName;

    // 联系电话
    private String tel;

    // 邮箱地址
    private String email;

    // 所在地区（code码）
    private String addressCode;

    // 所在地区
    private String address;

    // 成果描述
    private String description;

    // 技术/产品创新型
    private String innovation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTrl() {
        return trl;
    }

    public void setTrl(Integer trl) {
        this.trl = trl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getNegotiation() {
        return negotiation;
    }

    public void setNegotiation(Integer negotiation) {
        this.negotiation = negotiation;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCooperateType() {
        return cooperateType;
    }

    public void setCooperateType(Integer cooperateType) {
        this.cooperateType = cooperateType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInnovation() {
        return innovation;
    }

    public void setInnovation(String innovation) {
        this.innovation = innovation;
    }

    public Integer getFundingType() {
        return fundingType;
    }

    public void setFundingType(Integer fundingType) {
        this.fundingType = fundingType;
    }
}
