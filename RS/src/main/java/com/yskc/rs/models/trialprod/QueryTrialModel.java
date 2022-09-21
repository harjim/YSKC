package com.yskc.rs.models.trialprod;

import com.yskc.rs.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/12 13:34
 * description:查询试制
 */
public class QueryTrialModel extends PageParams {

    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
