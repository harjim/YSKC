package com.yskc.ms.models.newexpert.cooperation;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/11/12 11:37
 * @Description:专家合作意向model（带专家信息）
 * @author: hsx
 */
public class CooperationModel implements Serializable {

    private Integer id;

    //单位名称
    private String unitName;

    // 姓名
    private String fullname;

    //联系方式->电话
    private String linkInfo;

    //邮件
    private String email;

    //备注
    private String remark;

    // 意向信息(意向内容：达成合作填写)
    private String information;

    private Integer status;

    private Date lastUpdateTime;

    private Date createTime;

    private Integer type;

    //专家详细信息
    private String intentionUserName;
    private String researchArea;
    private String types;
    private String mobile;
    private String industries;
    private Integer gender;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLinkInfo() {
        return linkInfo;
    }

    public void setLinkInfo(String linkInfo) {
        this.linkInfo = linkInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getIntentionUserName() {
        return intentionUserName;
    }

    public void setIntentionUserName(String intentionUserName) {
        this.intentionUserName = intentionUserName;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIndustries() {
        return industries;
    }

    public void setIndustries(String industries) {
        this.industries = industries;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
