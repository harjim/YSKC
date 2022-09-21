package com.yskc.rs.models.projectattendance;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectattendance
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-10 16:03
 * @Description: 批量操作数据
 */
public class BatchAttendanceUsedModel implements Serializable {
    private List<BatchProjectHourAttendance> list;
    private Date month;
    private Integer projectId;

    public List<BatchProjectHourAttendance> getList() {
        return list;
    }

    public void setList(List<BatchProjectHourAttendance> list) {
        this.list = list;
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
}
