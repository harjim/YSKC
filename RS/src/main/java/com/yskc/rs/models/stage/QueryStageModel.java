package com.yskc.rs.models.stage;

import com.yskc.rs.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/11 17:35
 * description:
 */
public class QueryStageModel extends PageParams {

    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
