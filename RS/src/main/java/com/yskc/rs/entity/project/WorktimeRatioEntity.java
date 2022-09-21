package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-12 11:19:48
 */
@TableName("p_worktimeRatio")
public class WorktimeRatioEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer companyId;
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
    private BigDecimal research;
    /**
     *
     */
    private BigDecimal technical;
    /**
     *
     */
    private BigDecimal auxiliary;
    /**
     *
     */
    private BigDecimal laboratory;
    /**
     *
     */
    private BigDecimal production;
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

    private Integer msCreatorId;
    private Integer msLastUpdatorId;

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

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
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

    public void setResearch(BigDecimal research) {
        this.research = research;
    }

    public BigDecimal getResearch() {
        return research;
    }

    public void setTechnical(BigDecimal technical) {
        this.technical = technical;
    }

    public BigDecimal getTechnical() {
        return technical;
    }

    public void setAuxiliary(BigDecimal auxiliary) {
        this.auxiliary = auxiliary;
    }

    public BigDecimal getAuxiliary() {
        return auxiliary;
    }

    public void setLaboratory(BigDecimal laboratory) {
        this.laboratory = laboratory;
    }

    public BigDecimal getLaboratory() {
        return laboratory;
    }

    public void setProduction(BigDecimal production) {
        this.production = production;
    }

    public BigDecimal getProduction() {
        return production;
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
}
