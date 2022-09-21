package com.yskc.ms.models.newexpert.cooperation;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2021/12/2 16:50
 * @Description:简单意向model
 * @author: hsx
 */
public class CooperationInfoModel implements Serializable {

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

    //ms端操作用户姓名
    private String operatorName;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
