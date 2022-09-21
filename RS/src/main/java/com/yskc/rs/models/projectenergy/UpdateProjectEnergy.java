package com.yskc.rs.models.projectenergy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectenergy
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-28 19:32
 * @Description: 更新项目能源损耗
 */
public class UpdateProjectEnergy implements Serializable {

    private Integer projectId;

    private Date month;

    private Integer etype;

    private Integer type;

    private BigDecimal rdHour;

    private List<ProjectEnergyModel> modelList;

    public static UpdateProjectEnergy build(List<ProjectEnergyModel> modelList, Date month, Integer projectId, Integer etype, Integer type) {
        UpdateProjectEnergy update = new UpdateProjectEnergy();
        update.modelList = modelList;
        update.month = month;
        update.projectId = projectId;
        update.etype = etype;
        update.type = type;
        return update;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public List<ProjectEnergyModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<ProjectEnergyModel> modelList) {
        this.modelList = modelList;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }
}
