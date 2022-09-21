package com.yskc.rs.models.projectrdequipment;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdemployee
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 15:58
 * @Description: 批量操作项目设备折旧费用
 */
public class BatchProjectRdEquipmentModel implements Serializable {

    private Integer projectId;

    private Date month;

    private List<ProjectRdEquipmentModel> list;

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

    public List<ProjectRdEquipmentModel> getList() {
        return list;
    }

    public void setList(List<ProjectRdEquipmentModel> list) {
        this.list = list;
    }
}
