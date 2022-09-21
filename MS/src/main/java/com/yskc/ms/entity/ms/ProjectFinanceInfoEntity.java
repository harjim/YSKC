package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/10/31 10:25
 * description:项目财务信息
 */
@TableName("project_finance_info")
public class ProjectFinanceInfoEntity {
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer projectId;
    private Date dockingTime;//启动时间
    private BigDecimal estimateRdAmount;//预计研发归集总额

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getDockingTime() {
        return dockingTime;
    }

    public void setDockingTime(Date dockingTime) {
        this.dockingTime = dockingTime;
    }

    public BigDecimal getEstimateRdAmount() {
        return estimateRdAmount;
    }

    public void setEstimateRdAmount(BigDecimal estimateRdAmount) {
        this.estimateRdAmount = estimateRdAmount;
    }
}
