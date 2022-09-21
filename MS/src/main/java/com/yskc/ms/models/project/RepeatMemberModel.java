package com.yskc.ms.models.project;

/**
 * @program: ms
 * @description: 查询项目配置重复人员Model
 * @author: cyj
 * @create: 2022-01-14 09:22
 **/
public class RepeatMemberModel {
    private Integer projectId;
    private Integer mType;
    private String realName;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
}
