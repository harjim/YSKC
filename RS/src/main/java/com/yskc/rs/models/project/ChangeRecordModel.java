package com.yskc.rs.models.project;

import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/rs/models/project
 * @Author: hm
 * @CreateTime: 2022/8/26
 * @Description: 项目变更操作model
 */
public class ChangeRecordModel {
    // 变更记录id
    private Integer id;
    // 变更类型
    private Integer changeType;
    // 变更内容
    private String content;
    // 变更时间
    private Date changeTime;
    // 备注
    private String remark;

    // 项目id
    private Integer projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
}
