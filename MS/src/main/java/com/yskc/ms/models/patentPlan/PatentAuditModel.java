package com.yskc.ms.models.patentPlan;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/6/8 16:23
 * @Description:专利审核列表
 */
public class PatentAuditModel implements Serializable {
    private Integer id;

    private String rdTitle;

    private String pname;

    private Integer projectId;

    private Integer auditStatus;//审核状态

    private String patentName;//申请专利名称

    private String disclosureParperPath;//交底书路径

    private String description;//描述

    private String filepath;//专利资料

    private String inventor;//发明人

    private String inventorInfo;//发明人信息（附件）

    private Integer instanceId;

    private Integer curNodeId;

    private Integer moduleId;

    private Boolean hasPermission;//是否有审核权限

    private String companyName;//客户名称

    private Integer status;//rs p_audit_patent 状态

    private Integer patentPlanId;

    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getInventorInfo() {
        return inventorInfo;
    }

    public void setInventorInfo(String inventorInfo) {
        this.inventorInfo = inventorInfo;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(Integer curNodeId) {
        this.curNodeId = curNodeId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public String getDisclosureParperPath() {
        return disclosureParperPath;
    }

    public void setDisclosureParperPath(String disclosureParperPath) {
        this.disclosureParperPath = disclosureParperPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }
}
