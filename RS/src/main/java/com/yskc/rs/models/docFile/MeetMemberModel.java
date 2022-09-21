package com.yskc.rs.models.docFile;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/8/26 17:11
 * @Description:
 */
public class MeetMemberModel implements Serializable {

    private Integer id;

    private Integer projectId;

    private String members;

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

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
