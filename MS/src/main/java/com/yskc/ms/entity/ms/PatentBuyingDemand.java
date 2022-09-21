package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:00
 * @Description:专利需求
 */
@TableName("patent_buying_demand")
public class PatentBuyingDemand extends MsBaseEntity {

    @TableId
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
    private Integer year;//年份
    private Integer type;//0-购买   1-撰写
    private Integer engineerId;

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
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
}
