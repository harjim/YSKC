package com.yskc.rs.models.docFile;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/12/10 15:02
 * description:
 */
public class ReceptionModel implements Serializable {

    private String stageKey;

    private String docFileName;

    private String fileData;

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
}
