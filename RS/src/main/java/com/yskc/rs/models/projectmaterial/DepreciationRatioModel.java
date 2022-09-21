package com.yskc.rs.models.projectmaterial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectmaterial
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-02 15:52
 * @Description: 物料损耗率model
 */
public class DepreciationRatioModel {

    private BigDecimal depreciationRatio;

    private List<Integer> ids;
    private Date month;
    private Integer projectId;
    private Integer rdType;

    public BigDecimal getDepreciationRatio() {
        return depreciationRatio;
    }

    public void setDepreciationRatio(BigDecimal depreciationRatio) {
        this.depreciationRatio = depreciationRatio;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }
}
