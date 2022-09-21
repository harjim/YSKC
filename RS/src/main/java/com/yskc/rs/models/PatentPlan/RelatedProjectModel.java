package com.yskc.rs.models.PatentPlan;

import com.yskc.rs.models.Patent.PatentDetailModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/9 16:56
 * description:关联项目model
 */
public class RelatedProjectModel implements Serializable {

    private Integer projectId;

    private List<String> patentNos;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }


    public List<String> getPatentNos() {
        return patentNos;
    }

    public void setPatentNos(List<String> patentNos) {
        this.patentNos = patentNos;
    }
}
