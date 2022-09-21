package com.yskc.rs.models.hightechprogress;

import java.io.Serializable;

/**
 * @DateTime: 2022/5/20 11:29
 * @Description:
 * @author: hsx
 */
public class HighTechFileModel implements Serializable {

    private Integer id;

    private Integer projectId;

    private String docName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
