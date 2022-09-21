package com.yskc.rs.models.projectinspection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/22 8:29
 * description:
 */
public class SetInspectionAmountModel implements Serializable {

    private Integer projectId;

    private List<ProjectInspectionModel> models;

    private Date projectMonth;

    private BigDecimal setAmount;

    public BigDecimal getSetAmount() {
        return setAmount;
    }

    public void setSetAmount(BigDecimal setAmount) {
        this.setAmount = setAmount;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<ProjectInspectionModel> getModels() {
        return models;
    }

    public void setModels(List<ProjectInspectionModel> models) {
        this.models = models;
    }

    public Date getProjectMonth() {
        return projectMonth;
    }

    public void setProjectMonth(Date projectMonth) {
        this.projectMonth = projectMonth;
    }
}
