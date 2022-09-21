package com.yskc.ms.models.patent;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.entity.rs.RsPatentCostEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: wangxing
 * @CreateTime: 2019-10-25 16:28
 * @Description: 专利Model
 */
public class PatentCostModel {
    private Integer id;
    private String patentNo;
    private String costType;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date limitDate;
    private BigDecimal amount;
    private String mainTypeNo;
    private Boolean isPay;
    private String payDetail;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payDateTime;
    private Boolean isRemind;
    private String remindDetail;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date remindDateTime;
    private String remark;
    private String patentName;
    private String status;
    private String payer;
    private Integer companyId;
    private String companyName;
    private List<RsPatentCostEntity> rsEntityList;

    public List<RsPatentCostEntity> getRsEntityList() {
        return rsEntityList;
    }

    public void setRsEntityList(List<RsPatentCostEntity> rsEntityList) {
        this.rsEntityList = rsEntityList;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    /**
     * 收据号
     */
    private String receiptNo;

    public String getPayDetail() {
        return payDetail;
    }

    public void setPayDetail(String payDetail) {
        this.payDetail = payDetail;
    }

    public String getRemindDetail() {
        return remindDetail;
    }

    public void setRemindDetail(String remindDetail) {
        this.remindDetail = remindDetail;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public Date getPayDateTime() {
        return payDateTime;
    }

    public void setPayDateTime(Date payDateTime) {
        this.payDateTime = payDateTime;
    }

    public Date getRemindDateTime() {
        return remindDateTime;
    }

    public void setRemindDateTime(Date remindDateTime) {
        this.remindDateTime = remindDateTime;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Boolean getRemind() {
        return isRemind;
    }

    public void setRemind(Boolean remind) {
        isRemind = remind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getCostType() {
        return costType;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }



    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getMainTypeNo() {
        return mainTypeNo;
    }

    public void setMainTypeNo(String mainTypeNo) {
        this.mainTypeNo = mainTypeNo;
    }



    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
