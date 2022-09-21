package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity
 * @Author: wangxing
 * @CreateTime: 2019-09-24 08:48
 * @Description: ProjectQuotaEntity
 */
@TableName("t_projectQuota")
public class TechProjectQuotaEntity implements Serializable {
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
    private Integer projectId;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private BigDecimal investment;
    /**
     *
     */
    private BigDecimal transform;
    /**
     *
     */
    private BigDecimal water;
    /**
     *
     */
    private BigDecimal investmentOfPlan;
    /**
     *
     */
    private BigDecimal transformOfPlan;
    /**
     *
     */
    private BigDecimal waterOfPlan;
    /**
     *
     */
    private Integer robotsCount;
    /**
     *
     */
    private Integer robotsOfPlan;
    /**
     *
     */
    private Integer robotsOfAbroad;
    /**
     *
     */
    private Integer robotsOfDomestic;
    /**
     *
     */
    private Integer robotsOfGd;

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

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setInvestment(BigDecimal investment) {
        this.investment = investment;
    }

    public BigDecimal getInvestment() {
        return investment;
    }

    public void setTransform(BigDecimal transform) {
        this.transform = transform;
    }

    public BigDecimal getTransform() {
        return transform;
    }

    public void setWater(BigDecimal water) {
        this.water = water;
    }

    public BigDecimal getWater() {
        return water;
    }

    public void setInvestmentOfPlan(BigDecimal investmentOfPlan) {
        this.investmentOfPlan = investmentOfPlan;
    }

    public BigDecimal getInvestmentOfPlan() {
        return investmentOfPlan;
    }

    public void setTransformOfPlan(BigDecimal transformOfPlan) {
        this.transformOfPlan = transformOfPlan;
    }

    public BigDecimal getTransformOfPlan() {
        return transformOfPlan;
    }

    public void setWaterOfPlan(BigDecimal waterOfPlan) {
        this.waterOfPlan = waterOfPlan;
    }

    public BigDecimal getWaterOfPlan() {
        return waterOfPlan;
    }

    public void setRobotsCount(Integer robotsCount) {
        this.robotsCount = robotsCount;
    }

    public Integer getRobotsCount() {
        return robotsCount;
    }

    public void setRobotsOfPlan(Integer robotsOfPlan) {
        this.robotsOfPlan = robotsOfPlan;
    }

    public Integer getRobotsOfPlan() {
        return robotsOfPlan;
    }

    public void setRobotsOfAbroad(Integer robotsOfAbroad) {
        this.robotsOfAbroad = robotsOfAbroad;
    }

    public Integer getRobotsOfAbroad() {
        return robotsOfAbroad;
    }

    public void setRobotsOfDomestic(Integer robotsOfDomestic) {
        this.robotsOfDomestic = robotsOfDomestic;
    }

    public Integer getRobotsOfDomestic() {
        return robotsOfDomestic;
    }

    public void setRobotsOfGd(Integer robotsOfGd) {
        this.robotsOfGd = robotsOfGd;
    }

    public Integer getRobotsOfGd() {
        return robotsOfGd;
    }

}
