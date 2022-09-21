package com.yskc.ms.models.patent;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:48
 * @Description:
 */
public class PatentBuyingDemandModel implements Serializable {

    private Integer id;
    private Integer customerId;
    private Integer status;
    private String filePath;//需求文档路径
    private String remark;//备注
    private Integer inventionNum;//发明专利需求数量
    private Integer utilityNum;//实用新型需求数量
    private Integer appearanceDesignNum;//外观设计需求数量
    private String inventorInfo;//发明人信息
    private Integer ownerId;//业务员id
    private Date submitTime;//提交时间
    private String companyName;//客户名称
    private String realName;//业务员
    private Date createTime;//创建时间
    private Integer year;//年份
    private Integer type;//0-购买   1-撰写
    private Integer engineerId;
    private String engineer;
    private Integer inventionCnt;
    private Integer utilityCnt;
    private Integer appearanceDesignCnt;
    private String groupName;
    private Integer groupId;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public String getEngineer() {
        return engineer;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getInventionNum() {
        return inventionNum;
    }

    public void setInventionNum(Integer inventionNum) {
        this.inventionNum = inventionNum;
    }

    public Integer getUtilityNum() {
        return utilityNum;
    }

    public void setUtilityNum(Integer utilityNum) {
        this.utilityNum = utilityNum;
    }

    public Integer getAppearanceDesignNum() {
        return appearanceDesignNum;
    }

    public void setAppearanceDesignNum(Integer appearanceDesignNum) {
        this.appearanceDesignNum = appearanceDesignNum;
    }

    public String getInventorInfo() {
        return inventorInfo;
    }

    public void setInventorInfo(String inventorInfo) {
        this.inventorInfo = inventorInfo;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getInventionCnt() {
        return inventionCnt;
    }

    public void setInventionCnt(Integer inventionCnt) {
        this.inventionCnt = inventionCnt;
    }

    public Integer getUtilityCnt() {
        return utilityCnt;
    }

    public void setUtilityCnt(Integer utilityCnt) {
        this.utilityCnt = utilityCnt;
    }

    public Integer getAppearanceDesignCnt() {
        return appearanceDesignCnt;
    }

    public void setAppearanceDesignCnt(Integer appearanceDesignCnt) {
        this.appearanceDesignCnt = appearanceDesignCnt;
    }

    public void setCnt(PatentBuyingDemandModel patentBuyingDemandModel) {
        if (patentBuyingDemandModel != null) {
            this.inventionCnt = patentBuyingDemandModel.inventionCnt;
            this.utilityCnt = patentBuyingDemandModel.utilityCnt;
            this.appearanceDesignCnt = patentBuyingDemandModel.appearanceDesignCnt;
        }
    }
}
