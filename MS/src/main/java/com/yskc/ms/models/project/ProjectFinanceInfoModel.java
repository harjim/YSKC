package com.yskc.ms.models.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/11/2 8:28
 * description:财务服务model
 */
public class ProjectFinanceInfoModel implements Serializable {
    private Integer id;
    private Date dockingTime;//启动时间
    private BigDecimal estimateRdAmount;//预计研发归集总额
    private Integer projectId;

    private Date beginTime;

    private Date endTime;

    private Integer itemType;

    private String result;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
