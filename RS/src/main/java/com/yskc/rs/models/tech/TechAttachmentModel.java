package com.yskc.rs.models.tech;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/14 14:12
 * description:
 */
public class TechAttachmentModel implements Serializable {

    private Integer id;

    private Integer projectId;

    private String filePath;

    private Integer settingId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getSettingId() {
        return settingId;
    }

    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }
}
