package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression.DateTime;

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
    public String expertNumber;
    /// <summary>
    /// 姓名
    public String realName;
    /// 照片路径
    public String photoUrl;

    public Date birthday;
    /// 专家级别，0: 特聘专家，1：高级研究员
    public int level;
    // 有效期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date validDate;
    // 发证日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date issueDate;
    /// 身份证
    public String idNumber;
    /// 性别，0：未知 ，1：男，2：女
    public int gender;
    /// 所属行业
    public String industryCode;
    /// 电话
    public String phone;
    /// 邮箱
    public String email;
    /// 政治面貌
    public int policitalStatus;
    /// 学历， 0:无，1：高中及以下，2：中专，3：大专，4：本科 5：硕士，6：博士
    public int eduLevel;


    /// 通讯地址
    public String address;
    /// 邮编
    public String postcode;
    /// 工作经历
    public String workHistory;

    /// 社会活动 及兼职情况
    public String partHistory;
    /// 获得荣誉
    public String honour;
    /// 审核状态，0:待审核，1：审核通过，2：审核未通过
    public int status;
    public String remark;
    /// 专家标识Id,添加的时候系统生成
    public String uuid;
    /// 二维码图片地址
    public String qrcode;

    public int creatorId;
    public Date createTime;
    public int lastUpdatorId;
    public Date lastUpdateTime;

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
