package com.yskc.ms.models.params;

public class DocFileStageParams {

    private Integer[] docFileIds;

    private String stageKey;

    public Integer[] getDocFileIds() {
        return docFileIds;
    }

    public void setDocFileIds(Integer[] docFileIds) {
        this.docFileIds = docFileIds;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }
}
