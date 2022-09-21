package com.yskc.ms.models.project;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/8/1 9:26
 * description:项目技术人员列表
 */
public class ProjectMembersModel implements Serializable {

    private Integer id;

    private Integer memberId;

    private Integer mType;

    private String realName;

    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
