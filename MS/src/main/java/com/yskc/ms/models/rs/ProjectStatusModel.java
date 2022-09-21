package com.yskc.ms.models.rs;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-16 09:15
 * @Description: rs项目状态model
 */
public class ProjectStatusModel {
    private List<ProjectBaseStatusModel> audits;
    private Integer status;
    /**
     * 供预算审核用 // TODO: 21/03/25 zdf
     */
    private Integer projectId;

    private Integer companyId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<ProjectBaseStatusModel> getAudits() {
        return audits;
    }

    public void setAudits(List<ProjectBaseStatusModel> audits) {
        this.audits = audits;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
