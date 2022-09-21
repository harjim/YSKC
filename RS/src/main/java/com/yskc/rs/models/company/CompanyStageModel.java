package com.yskc.rs.models.company;

import com.yskc.rs.entity.company.CompanyStageEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @program: rs
 * @description: 公司stagelist
 * @author: cyj
 * @create: 2022-05-11 16:36
 **/
public class CompanyStageModel implements Serializable {
    private Integer projectId;
    private List<CompanyStageEntity> list;
    private Boolean changeCStage;

    public Boolean getChangeCStage() {
        return changeCStage;
    }

    public void setChangeCStage(Boolean changeCStage) {
        this.changeCStage = changeCStage;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<CompanyStageEntity> getList() {
        return list;
    }

    public void setList(List<CompanyStageEntity> list) {
        this.list = list;
    }
}
