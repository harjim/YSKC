package com.yskc.ms.models.rs;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/4/14 14:22
 * @Description:查新报告项目列表model
 */
public class ReportProjectModel implements Serializable {

    private Integer projectId;

    private String pname;

    private String rdTitle;

    private Integer auditStatus;//审核状态

    private Boolean hasPermission;//当前用户是否有审核权限 true 有 false 没有

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }
}
