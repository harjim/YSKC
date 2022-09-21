package com.yskc.rs.models.projectenergy;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectenergy
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-16 17:27
 * @Description: 批量添加能源
 */
public class EnergyAddListModel implements Serializable {

    private Integer projectId;

    private Date month;

    private Integer etype;

    private Integer type;

    private List<EnergyListModel> energyModels;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<EnergyListModel> getEnergyModels() {
        return energyModels;
    }

    public void setEnergyModels(List<EnergyListModel> energyModels) {
        this.energyModels = energyModels;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
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
}
