package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.enums.CostEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-16 14:44:47
 */
@TableName("p_material")
public class ProjectMaterialEntity implements Serializable {
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
    private Integer materialDataId;
    /**
     *
     */
    private BigDecimal used;
    /**
     *
     */
    private Integer companyId;
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
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer lastUpdatorId;

    private Integer rdType;


    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    private BigDecimal rdAmount;

    private BigDecimal finishAmount;//成品金额

    private BigDecimal finishQuantity;//成品数量

    private BigDecimal finishUnitPrice;//成品单价

    private BigDecimal wasteAmount;//废品金额

    private BigDecimal wasteQuantity;//废品数量

    private BigDecimal wasteUnitPrice;//废品单价

    private BigDecimal totalYield;
    private BigDecimal rdYield;
    private BigDecimal depreciationRatio;

    public BigDecimal getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(BigDecimal finishAmount) {
        this.finishAmount = finishAmount;
    }

    public BigDecimal getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(BigDecimal finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public BigDecimal getFinishUnitPrice() {
        return finishUnitPrice;
    }

    public void setFinishUnitPrice(BigDecimal finishUnitPrice) {
        this.finishUnitPrice = finishUnitPrice;
    }

    public BigDecimal getWasteAmount() {
        return wasteAmount;
    }

    public void setWasteAmount(BigDecimal wasteAmount) {
        this.wasteAmount = wasteAmount;
    }

    public BigDecimal getWasteQuantity() {
        return wasteQuantity;
    }

    public void setWasteQuantity(BigDecimal wasteQuantity) {
        this.wasteQuantity = wasteQuantity;
    }

    public BigDecimal getWasteUnitPrice() {
        return wasteUnitPrice;
    }

    public void setWasteUnitPrice(BigDecimal wasteUnitPrice) {
        this.wasteUnitPrice = wasteUnitPrice;
    }

    public static ProjectMaterialEntity build(ProjectMaterialEntity entity, Integer userId,
                                              Integer msUserId, Date now, BigDecimal remainQuantity,
                                              BigDecimal unitPrice) {
        entity.lastUpdatorId = userId;
        entity.msLastUpdatorId = msUserId;
        entity.lastUpdateTime = now;
        entity.used = entity.used.add(remainQuantity);
        entity.rdAmount = entity.used.multiply(unitPrice);
        return entity;
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


    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public void setMaterialDataId(Integer materialDataId) {
        this.materialDataId = materialDataId;
    }

    public Integer getMaterialDataId() {
        return materialDataId;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
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

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public static ProjectMaterialEntity build(Integer userId, Integer msUserId, Date now, Integer materialId,
                                              Integer projectId, Integer rdType, Integer companyId,
                                              BigDecimal used, BigDecimal rdAmount, BigDecimal price) {
        ProjectMaterialEntity entity = new ProjectMaterialEntity();
        entity.lastUpdatorId = userId;
        entity.creatorId = userId;
        entity.msLastUpdatorId = msUserId;
        entity.msCreatorId = msUserId;
        entity.lastUpdateTime = now;
        entity.createTime = now;
        entity.materialDataId = materialId;
        entity.projectId = projectId;
        entity.rdType = rdType;
        entity.companyId = companyId;
        entity.used = used;
        entity.rdAmount = rdAmount;
        if (rdType == CostEnum.IRON_MATERIAL.getType() || rdType == CostEnum.IRON_TRIAL.getType()) {
            entity.rdAmount = BigDecimal.ZERO;
        }
        if (rdType == CostEnum.PAPER_MATERIAL.getType() || rdType == CostEnum.PAPER_TRIAL.getType()) {
            entity.finishUnitPrice = price;
        } else {
            entity.finishUnitPrice = BigDecimal.ZERO;
        }
        return entity;
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
    }

    public BigDecimal getRdYield() {
        return rdYield;
    }

    public void setRdYield(BigDecimal rdYield) {
        this.rdYield = rdYield;
    }

    public BigDecimal getDepreciationRatio() {
        return depreciationRatio;
    }

    public void setDepreciationRatio(BigDecimal depreciationRatio) {
        this.depreciationRatio = depreciationRatio;
    }
}
