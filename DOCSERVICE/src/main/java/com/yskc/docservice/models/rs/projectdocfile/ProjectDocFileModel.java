package com.yskc.docservice.models.rs.projectdocfile;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ProjectDocFileModel {

    private Integer id;
    private Integer projectId;
    private String stage;
    private Integer docFileId;
    private String docFileName;
    private String docName;
    private String templateName;
    private List<DocFileModel> docList;

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    private Integer seq;
    private Integer status;//提交审核状态 // 0进行中 1通过 2驳回 3激活 4提交 5未提交
    private String suggestion;

    private Integer owner;//0技术1财务
    private Boolean needEdit;//是否需要编辑 true 是 false 否
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dataMonth;//取数据需要月份

    private List<Integer> pDocFileIds;

    private Boolean deleteLogo;//批量删除标识 true 可删除 false 不可删除

    private Boolean finished;

    public Boolean getDeleteLogo() {
        return deleteLogo;
    }

    public void setDeleteLogo(Boolean deleteLogo) {
        this.deleteLogo = deleteLogo;
    }

    public List<Integer> getpDocFileIds() {
        return pDocFileIds;
    }

    public void setpDocFileIds(List<Integer> pDocFileIds) {
        this.pDocFileIds = pDocFileIds;
    }

    public Boolean getNeedEdit() {
        return needEdit;
    }

    public void setNeedEdit(Boolean needEdit) {
        this.needEdit = needEdit;
    }

    public Date getDataMonth() {
        return dataMonth;
    }

    public void setDataMonth(Date dataMonth) {
        this.dataMonth = dataMonth;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<DocFileModel> getDocList() {
        return docList;
    }

    public void setDocList(List<DocFileModel> docList) {
        this.docList = docList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
