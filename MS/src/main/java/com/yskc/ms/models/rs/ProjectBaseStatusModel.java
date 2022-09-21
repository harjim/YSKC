package com.yskc.ms.models.rs;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-03-25 15:40
 * @Description: 项目状态model
 */
public class ProjectBaseStatusModel {

    private Integer projectId;
    private List<Date> months;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Date> getMonths() {
        return months;
    }

    public void setMonths(List<Date> months) {
        this.months = months;
    }
}
