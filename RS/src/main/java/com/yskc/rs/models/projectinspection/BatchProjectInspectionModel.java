package com.yskc.rs.models.projectinspection;

import com.yskc.rs.entity.InspectionEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectinspection
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-10 11:13
 * @Description: 批量操作费用
 */
public class BatchProjectInspectionModel {

    private Integer projectId;

    private Date projectMonth;

    private List<InspectionEntity> inspectionEntities;

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

    public Date getProjectMonth() {
        return projectMonth;
    }

    public void setProjectMonth(Date projectMonth) {
        this.projectMonth = projectMonth;
    }

    public List<InspectionEntity> getInspectionEntities() {
        return inspectionEntities;
    }

    public void setInspectionEntities(List<InspectionEntity> inspectionEntities) {
        this.inspectionEntities = inspectionEntities;
    }

}
