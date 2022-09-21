package com.yskc.rs.models.docFile;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 8:19
 * @Description: 调整文件阶段model
 */
public class ChangeDocStageModel implements Serializable {

    private List<Integer> pdocFileIds;
    /**
     * 调整后的阶段
     */
    private String stage;

    private Integer projectId;

    /**
     * 是否在目标文档之后
     */
    private Boolean after;

    /**
     * 目标文档
     */
    private Integer targetPDocFileId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getPdocFileIds() {
        return pdocFileIds;
    }

    public void setPdocFileIds(List<Integer> pdocFileIds) {
        this.pdocFileIds = pdocFileIds;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Boolean getAfter() {
        return after;
    }

    public void setAfter(Boolean after) {
        this.after = after;
    }

    public Integer getTargetPDocFileId() {
        return targetPDocFileId;
    }

    public void setTargetPDocFileId(Integer targetPDocFileId) {
        this.targetPDocFileId = targetPDocFileId;
    }
}
