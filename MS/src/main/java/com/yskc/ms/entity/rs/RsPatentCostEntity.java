package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/2 10:45
 * description:
 */
@TableName("patent_cost")
public class RsPatentCostEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private int id;
    /**
     * 专利号
     */
    private String patentNo;
    /**
     * 费用类型
     */
    private String costType;
    /**
     * 缴费期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date limitDate;
    /**
     * 缴费金额
     */
    private BigDecimal amount;
    /**
     * 是否缴费
     */
    private Boolean isPay;
    /**
     * 缴费日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payDateTime;
    /**
     * 是否提醒
     */
    private Boolean isRemind;
    /**
     * 提醒日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date remindDateTime;
    /**
     * 备注
     */
    private String remark;

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private String receiptNo;
    private String status;
    private String payer;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public RsPatentCostEntity() {
    }

    public RsPatentCostEntity(String patentNo, String costType, Date limitDate, BigDecimal amount, Boolean isPay, Date payDateTime, String receiptNo, String status, String payer) {
        this.patentNo = patentNo;
        this.costType = costType;
        this.limitDate = limitDate;
        this.amount = amount;
        this.isPay = isPay;
        this.payDateTime = payDateTime;
        this.receiptNo = receiptNo;
        this.status = status;
        this.payer = payer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Date getPayDateTime() {
        return payDateTime;
    }

    public void setPayDateTime(Date payDateTime) {
        this.payDateTime = payDateTime;
    }

    public Boolean getRemind() {
        return isRemind;
    }

    public void setRemind(Boolean remind) {
        isRemind = remind;
    }

    public Date getRemindDateTime() {
        return remindDateTime;
    }

    public void setRemindDateTime(Date remindDateTime) {
        this.remindDateTime = remindDateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

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

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }
}
