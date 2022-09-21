package com.yskc.rs.models.projectequipment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 18:31
 * @Description: 批量保存设备研发记录
 */
public class BatchEquipmentModel implements Serializable {

    private Integer projectId;

    private Date month;
    private List<ProjectEquipmentModel> list;

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

    public List<ProjectEquipmentModel> getList() {
        return list;
    }

    public void setList(List<ProjectEquipmentModel> list) {
        this.list = list;
    }
}
