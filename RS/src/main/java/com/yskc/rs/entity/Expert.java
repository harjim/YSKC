package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author 作者 Wangxing
 * @version 创建时间：2019年7月22日 下午3:27:13
 */
@TableName("expert")
public class Expert {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private String expertNumber;
    private String realName;
    private String photoUrl;

    private Date birthday;
    private int level;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date validDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date issueDate;
    private String idNumber;
    private int gender;
    private String industryCode;
    private String phone;
    private String email;
    private int policitalStatus;
    private int eduLevel;


    private String address;
    private String postcode;
    private String workHistory;
    private String partHistory;
    private String honour;
    private int status;
    private String remark;
    private String uuid;
    private String qrcode;

    private int creatorId;
    private Date createTime;
    private int lastUpdatorId;
    private Date lastUpdateTime;

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


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public int getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(int lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }
}
