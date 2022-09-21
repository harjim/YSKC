package com.yskc.docservice.models.rs.projectrdemployee;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdemployee
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 15:58
 * @Description: 批量操作项目人员费用
 */
public class BatchProjectRdEmployeeModel implements Serializable {

    private Integer projectId;

    private Date month;

    private List<ProjectRdEmployeeModel> list;

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

    public List<ProjectRdEmployeeModel> getList() {
        return list;
    }

    public void setList(List<ProjectRdEmployeeModel> list) {
        this.list = list;
    }
}
