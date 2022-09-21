package com.yskc.rs.models.docFile;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/4/21 10:10
 * @Description:
 */
public class SubmitFileModel implements Serializable {

    private String docFileName;

    private Integer id;

    private Boolean needEdit;

    private Integer docFileDataId;

    private String stage;

    private String stageName;

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getNeedEdit() {
        return needEdit;
    }

    public void setNeedEdit(Boolean needEdit) {
        this.needEdit = needEdit;
    }

    public Integer getDocFileDataId() {
        return docFileDataId;
    }

    public void setDocFileDataId(Integer docFileDataId) {
        this.docFileDataId = docFileDataId;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }
}
