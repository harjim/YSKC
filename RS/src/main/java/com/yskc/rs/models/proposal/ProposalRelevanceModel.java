package com.yskc.rs.models.proposal;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/4/19 14:47
 * @Description:
 * @author: hsx
 */
public class ProposalRelevanceModel implements Serializable {

    private Integer projectId;

    private List<Integer> ids;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
