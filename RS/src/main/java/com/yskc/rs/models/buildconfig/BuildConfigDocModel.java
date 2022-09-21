package com.yskc.rs.models.buildconfig;
import java.io.Serializable;

/**
 * @program: rs
 * @description: 机构建设事项文档用模板
 * @author: cyj
 * @create: 2022-01-06 11:03
 **/
public class BuildConfigDocModel implements Serializable {
    private Integer type;
    private String madeDept;
    private String maker;
    private String issueDate;
    private String version;
    private String description;
    private String validDate;
    private String approval;
    private String auditor;
    private String rdDeptMaster;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMadeDept() {
        return madeDept;
    }

    public void setMadeDept(String madeDept) {
        this.madeDept = madeDept;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getApproval() {
        return approval;
    }

    public void setApproval(String approval) {
        this.approval = approval;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getRdDeptMaster() {
        return rdDeptMaster;
    }

    public void setRdDeptMaster(String rdDeptMaster) {
        this.rdDeptMaster = rdDeptMaster;
    }
}
