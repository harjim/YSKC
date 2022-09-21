package com.yskc.rs.models.PatentPlan;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/7/6 11:13
 * description:专利立项查询返回model
 */
public class PatentPlanModel implements Serializable {

    private Integer id;

    private String pname;//项目名称

    private String RD;//项目编号

    private  String patentName;//申请名称

    private String disclosureParperPath;

    private String docName;//交底书名称

    private String description;//描述

    private Integer status;//状态-》停用

    private Integer rdIndex;//项目编号

    private Integer beginYear;//项目开始年

    private Integer projectId;

    private String rdTitle;

    private Integer auditStatus;//审核状态

    private String inventor;//发明人

    private String filepath;//申请资料

    private String inventorInfo;//发明人信息

    private Integer year;

    private String finalizedName;

    public String getFinalizedName() {
        return finalizedName;
    }

    public void setFinalizedName(String finalizedName) {
        this.finalizedName = finalizedName;
    }

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

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getDisclosureParperPath() {
        return disclosureParperPath;
    }

    public void setDisclosureParperPath(String disclosureParperPath) {
        this.disclosureParperPath = disclosureParperPath;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getRD() {
        return RD;
    }

    public void setRD(String RD) {
        this.RD = RD;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
