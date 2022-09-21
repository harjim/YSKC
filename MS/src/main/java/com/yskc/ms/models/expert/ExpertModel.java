package com.yskc.ms.models.expert;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ExpertModel implements Serializable {
    private Integer id;
    private String expertNumber;
    /// <summary>
    /// 姓名
    private String realName;
    /// 照片路径
    private String photoUrl;

    private Date birthday;
    /// 专家级别，0: 特聘专家，1：高级研究员
    public int level;
    // 有效期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;
    // 发证日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date issueDate;
    /// 身份证
    private String idNumber;
    /// 性别，0：未知 ，1：男，2：女
    public int gender;
    /// 所属行业
    private String industryCode;
    /// 电话
    private String phone;
    /// 邮箱
    private String email;
    /// 政治面貌
    private int policitalStatus;
    /// 学历， 0:无，1：高中及以下，2：中专，3：大专，4：本科 5：硕士，6：博士
    private int eduLevel;
    /// 通讯地址
    private String address;
    /// 邮编
    private String postcode;
    /// 工作经历
    private String workHistory;

    /// 社会活动 及兼职情况
    private String partHistory;
    /// 获得荣誉
    private String honour;
    /// 审核状态，0:待审核，1：审核通过，2：审核未通过
    private int status;
    private String remark;
    /// 专家标识Id,添加的时候系统生成
    private String uuid;
    /// 二维码图片地址
    private String qrcode;
    private String viewUrl;

    private int creatorId;
    private Date createTime;
    private int lastUpdatorId;
    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpertNumber() {
        return expertNumber;
    }

    public void setExpertNumber(String expertNumber) {
        this.expertNumber = expertNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPolicitalStatus() {
        return policitalStatus;
    }

    public void setPolicitalStatus(int policitalStatus) {
        this.policitalStatus = policitalStatus;
    }

    public int getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(int eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWorkHistory() {
        return workHistory;
    }

    public void setWorkHistory(String workHistory) {
        this.workHistory = workHistory;
    }

    public String getPartHistory() {
        return partHistory;
    }

    public void setPartHistory(String partHistory) {
        this.partHistory = partHistory;
    }

    public String getHonour() {
        return honour;
    }

    public void setHonour(String honour) {
        this.honour = honour;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(int lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setViewUrl(String viewUrl){
        this.viewUrl = viewUrl;
    }
    public String getViewUrl(){
        return viewUrl;
    }
}
