package com.yskc.ms.models;

import com.yskc.ms.models.params.PageParams;

/**
 * @DateTime: 2021/9/15 14:28
 * @Description:
 * @author: hsx
 */
public class QueryStageModel extends PageParams {

    private Integer projectId;

    private Integer companyId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
