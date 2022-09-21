package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("patent_demand")
public class PatentDemand implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private String demandNo;
    private Integer customerId;
    private Integer year;
    private Integer type;
    private Integer inventionNum;
    private Integer utilityNum;
    private Integer appearanceDesignNum;
    private Integer softRightNum;
    private Integer total;
    private Integer ownerId;
    private Integer techId;
    private String remark;
    private String proxyPath;
    private String blPath;
    private String remissionPath;
    private String otherPath;
    private Date planSubmitDate;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getProxyPath() {
        return proxyPath;
    }

    public void setProxyPath(String proxyPath) {
        this.proxyPath = proxyPath;
    }

    public String getBlPath() {
        return blPath;
    }

    public void setBlPath(String blPath) {
        this.blPath = blPath;
    }

    public String getRemissionPath() {
        return remissionPath;
    }

    public void setRemissionPath(String remissionPath) {
        this.remissionPath = remissionPath;
    }

    public String getOtherPath() {
        return otherPath;
    }

    public void setOtherPath(String otherPath) {
        this.otherPath = otherPath;
    }

    public Date getPlanSubmitDate() {
        return planSubmitDate;
    }

    public void setPlanSubmitDate(Date planSubmitDate) {
        this.planSubmitDate = planSubmitDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSoftRightNum() {
        return softRightNum;
    }

    public void setSoftRightNum(Integer softRightNum) {
        this.softRightNum = softRightNum;
    }
}
