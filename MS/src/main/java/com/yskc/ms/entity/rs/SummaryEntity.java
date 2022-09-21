package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: summary实体
 * @Author: zhangdingfu
 * @date: 2019-07-19
 */
@TableName("p_summary")
public class SummaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private Date month;
    /**
     *
     */
    private Integer rdType;
    /**
     *
     */
    private String accountNumber;
    /**
     *
     */
    private BigDecimal rdFunds;
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
    private Integer updatorId;
    /**
     *
     */
    private Date lastUpdateTime;

    private Integer lastUpdatorId;


    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public static SummaryEntity build(Date now, Date month, Integer projectId, int type, BigDecimal insurance, Integer msUserId) {
        SummaryEntity summaryEntity = new SummaryEntity();
        summaryEntity.setLastUpdateTime(now);
        summaryEntity.setUpdatorId(-1);
        summaryEntity.setCreateTime(now);
        summaryEntity.setLastUpdatorId(-1);
        summaryEntity.setCreatorId(-1);
        summaryEntity.setMonth(month);
        summaryEntity.setAccountNumber("");
        summaryEntity.setProjectId(projectId);
        summaryEntity.setRdType(type);
        summaryEntity.setRdFunds(insurance);
        summaryEntity.setMsCreatorId(msUserId);
        summaryEntity.setMsLastUpdatorId(msUserId);
        return summaryEntity;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
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

    public void setUpdatorId(Integer updatorId) {
        this.updatorId = updatorId;
    }

    public Integer getUpdatorId() {
        return updatorId;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }


}
