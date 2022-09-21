package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.models.company.OwnershipModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/12/4 14:09
 * description:股权架构
 */
@TableName("c_ownership")
public class OwnershipEntity {
    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private String shareholder;
    private BigDecimal capitalContribution;
    private String contributionType;
    private BigDecimal proportion;

    public static OwnershipEntity buildInsert(OwnershipModel item, Date now, Integer userId) {
        OwnershipEntity entity = build(item);
        entity.loadCreate(now, userId);
        return entity;
    }


    public static OwnershipEntity buildUpdate(OwnershipModel item, Date now, Integer userId) {
        OwnershipEntity entity = build(item);
        entity.id = item.getId();
        entity.loadUpdate(now, userId);
        return entity;
    }

    private static OwnershipEntity build(OwnershipModel item) {
        OwnershipEntity entity = new OwnershipEntity();
        entity.companyId = item.getCompanyId();
        entity.shareholder = item.getShareholder();
        entity.capitalContribution = item.getCapitalContribution();
        entity.contributionType = item.getContributionType();
        entity.proportion = item.getProportion();
        return entity;
    }

    private void loadCreate(Date now, Integer creatorUserId) {
        this.createTime = now;
        this.creatorId = -1;
        this.msCreatorId = creatorUserId;
        loadUpdate(now, creatorUserId);
    }

    private void loadUpdate(Date now, Integer updateUserId) {
        this.lastUpdateTime = now;
        this.lastUpdatorId = -1;
        this.msLastUpdatorId = updateUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getShareholder() {
        return shareholder;
    }

    public void setShareholder(String shareholder) {
        this.shareholder = shareholder;
    }

    public BigDecimal getCapitalContribution() {
        return capitalContribution;
    }

    public void setCapitalContribution(BigDecimal capitalContribution) {
        this.capitalContribution = capitalContribution;
    }

    public String getContributionType() {
        return contributionType;
    }

    public void setContributionType(String contributionType) {
        this.contributionType = contributionType;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }
}
