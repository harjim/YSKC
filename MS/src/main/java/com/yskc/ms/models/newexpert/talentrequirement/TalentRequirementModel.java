package com.yskc.ms.models.newexpert.talentrequirement;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: ms
 * @description: 人才需求模型
 * @author: cyj
 * @create: 2021-12-08 10:08
 **/
public class TalentRequirementModel implements Serializable {
    private Integer id;

    //职位名称
    private String job;

    //招聘单位
    private String customerName;

    // 学历 0：研究生，1：本科，2：大专，3：学历不限（其他）
    private Integer eduLevel;

    // 工作经验(年限) 0：1-3年，1：3-5年、2：5-10年，3：10年以上，4: 经验不限
    private Integer workYear;

    // 职位类型
    // 0：技术，1：产品，2：设计，3：运营，4：市场，5：人事/财务/行政，6：高级管理，7：销售
    // 8：传媒，9：金融，10：教育培训，11：医疗健康，12：采购/贸易，13：供应链/物流，14：房地产/建筑
    // 15：农/林/牧/渔，16：咨询/翻译/法律，17：旅游，18：服务业，19：生产制造，99：不限
    private Integer jobType;

    //状态：0：草稿，1：发布，4：终止
    // status为1且closeDate小于当前日期时，显示为过期
    private Integer status;

    //最小薪资（当只有最小薪资时，只显示最小薪资）
    private BigDecimal minSalary;

    //最大薪资
    private BigDecimal maxSalary;

    //面议(双方洽谈(价格))
    //为true时，薪资置空
    private Boolean negotiation;

    //截止日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date closeDate;

    // 招聘类型：0：社会招聘，1：校园招聘
    private Integer recruitment;

    // 关键字【最多三个，用逗号隔开。存储形式：,xxx,xxx,xxx,  显示：xxx,xxx,xxx 查询：like %,{xxx},%】
    private String keywords;

    //联系人
    private String linkName;

    //职位（部门职位）
    private String position;

    //联系电话
    private String linkTel;

    //联系邮箱
    private String linkEmail;

    //地址码
    private String addressCode;

    //详细地址
    private String address;

    //岗位职责
    private String duty;

    //岗位要求
    private String requirement;

    //投递人数
    private Integer num;

    private Date createTime;

    private Date lastUploadTime;

    private String creatorName;

    private Integer lastUpdaterId;

    //是否可编辑
    private Boolean editable;

    //false保存，true发布
    private Boolean issue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Boolean getNegotiation() {
        return negotiation;
    }

    public void setNegotiation(Boolean negotiation) {
        this.negotiation = negotiation;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Integer getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(Integer recruitment) {
        this.recruitment = recruitment;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUploadTime() {
        return lastUploadTime;
    }

    public void setLastUploadTime(Date lastUploadTime) {
        this.lastUploadTime = lastUploadTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Integer getLastUpdaterId() {
        return lastUpdaterId;
    }

    public void setLastUpdaterId(Integer lastUpdaterId) {
        this.lastUpdaterId = lastUpdaterId;
    }

    public Boolean getEditable() {
        return editable;
    }

    public void setEditable(Boolean editable) {
        this.editable = editable;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getIssue() {
        return issue;
    }

    public void setIssue(Boolean issue) {
        this.issue = issue;
    }
}
