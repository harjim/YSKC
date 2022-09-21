package com.yskc.ms.models.rs;

import com.yskc.ms.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/13 9:18
 * @Description:
 */
public class QueryReportsModel extends PageParams {

    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
