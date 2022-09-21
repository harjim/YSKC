package com.yskc.rs.models.docFile;

import java.io.Serializable;

/**
 * Created by hck
 * on 2021/2/19 9:52
 * description:过程文件页脚设置model
 */

public class DocFileFooterModel implements Serializable {

    private Integer id;
    private Integer projectId;

    private String rdTitle;

    private String pname;

    private String toCompileEnumber;//编制

    private String auditEnumber;//审核

    private String approvalEnumber;//批准

    private String toCompile;

    private String auditor;

    private String approval;

    private Integer year;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getToCompile() {
        return toCompile;
    }

    public void setToCompile(String toCompile) {
        this.toCompile = toCompile;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getToCompileEnumber() {
        return toCompileEnumber;
    }

    public void setToCompileEnumber(String toCompileEnumber) {
        this.toCompileEnumber = toCompileEnumber;
    }

    public String getAuditEnumber() {
        return auditEnumber;
    }

    public void setAuditEnumber(String auditorEnumber) {
        this.auditEnumber = auditorEnumber;
    }

    public String getApprovalEnumber() {
        return approvalEnumber;
    }

    public void setApprovalEnumber(String approvalEnumber) {
        this.approvalEnumber = approvalEnumber;
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
}
