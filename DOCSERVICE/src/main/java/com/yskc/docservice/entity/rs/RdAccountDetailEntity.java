package com.yskc.docservice.entity.rs;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-30 10:49:31
 */
@TableName("rdAccountDetail")
public class RdAccountDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Date rdDate;
    /**
     *
     */
    private String accNumber;
    /**
     *
     */
    private String summary;
    /**
     *
     */
    private BigDecimal debit;
    /**
     *
     */
    private BigDecimal credit;
    /**
     *
     */
    private String direction;
    /**
     *
     */
    private BigDecimal balance;
    /**
     *
     */
    private Integer accType;
    /**
     *
     */
    private Integer companyId;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setRdDate(Date rdDate) {
        this.rdDate = rdDate;
    }

    public Date getRdDate() {
        return rdDate;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setAccType(Integer accType) {
        this.accType = accType;
    }

    public Integer getAccType() {
        return accType;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

}
