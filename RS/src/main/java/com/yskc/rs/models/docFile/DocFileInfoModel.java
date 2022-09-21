package com.yskc.rs.models.docFile;

import com.yskc.rs.models.employee.EmployeeAutographModel;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/3/11 9:36
 * @Description:过程文件信息model
 */
public class DocFileInfoModel implements Serializable {

    private Integer id;//docFile  ->id

    private Integer projectId;

    private Integer docFileId;//文档id

    private String data;

    private String docFileName;

    private String templateName;

    private String docName;

    private Integer pdocFileId;//文档内容id


    private Map<String,Object> dataMap;//决算所用数据

    private Map<String,Object> commonMap;//共用数据map

    private Integer hasSubmit;// 0进行中 1通过 2驳回 3激活 4提交 5未提交

    private Integer owner;//0技术1财务

    private Integer status;

    private Date docDate;

    private String stageKey;

    private Integer templateId;//默认模板id

    private Boolean deleted;

    private Boolean needEdit;//需要编辑

    private Boolean finished;
    private Map<String, EmployeeAutographModel> footerMap;//文档footer

    public Boolean getNeedEdit() {
        return needEdit;
    }

    public void setNeedEdit(Boolean needEdit) {
        this.needEdit = needEdit;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getHasSubmit() {
        return hasSubmit;
    }

    public void setHasSubmit(Integer hasSubmit) {
        this.hasSubmit = hasSubmit;
    }

    public Map<String, Object> getCommonMap() {
        return commonMap;
    }

    public void setCommonMap(Map<String, Object> commonMap) {
        this.commonMap = commonMap;
    }


    public Integer getPdocFileId() {
        return pdocFileId;
    }

    public void setPdocFileId(Integer pdocFileId) {
        this.pdocFileId = pdocFileId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Map<String, EmployeeAutographModel> getFooterMap() {
        return footerMap;
    }

    public void setFooterMap(Map<String, EmployeeAutographModel> footerMap) {
        this.footerMap = footerMap;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
