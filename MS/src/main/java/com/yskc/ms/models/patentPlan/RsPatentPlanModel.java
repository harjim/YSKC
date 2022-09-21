package com.yskc.ms.models.patentPlan;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/7 8:51
 * description:
 */
public class RsPatentPlanModel implements Serializable {

    private Integer id;

    private String patentName;//申请名称

    private String docName;//交底书名称

    private String path;//交底书路径

    private String description;//描述

    private String pname;//项目名称

    private String rdTitle;

    private Integer status;//状态

    private String masterName;//负责人

    private String patentNo;//专利号

    private Integer masterId;

    private String companyName;//客户名称

    private Integer projectId;

    private Integer companyId;

    private String rsPatentName;//专利名称

    private String mainType;//专利类型

    private Date applyDateTime;//申请时间

    private String inventor;//发明人

    private String filepath;//申请资料

    private String inventorInfo;

    private String opinion;//最新建议

    private Integer year;

    private String finalizedName;

    private String deptName;//所属部门

    private String owner;//业务员

    private String creator;//工程师

    private Integer msCreatorId;

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFinalizedName() {
        return finalizedName;
    }

    public void setFinalizedName(String finalizedName) {
        this.finalizedName = finalizedName;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
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

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getRsPatentName() {
        return rsPatentName;
    }

    public void setRsPatentName(String rsPatentName) {
        this.rsPatentName = rsPatentName;
    }

    public String getMainType() {
        return mainType;
    }

    public void setMainType(String mainType) {
        this.mainType = mainType;
    }

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }
}
