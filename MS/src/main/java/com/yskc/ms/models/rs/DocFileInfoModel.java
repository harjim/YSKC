package com.yskc.ms.models.rs;

import java.io.Serializable;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/3/11 9:36
 * @Description:过程文件信息model
 */
public class DocFileInfoModel implements Serializable {

    private Integer id;//文件id

    private Integer projectId;

    private Integer docFileId;//生成文档id

    private String docFileName;

    private String templateName;

    private Integer pdocFileId;//文档内容id


    private Map<String, Object> dataMap;//决算所用数据

    private Map<String, Object> commonMap;//共用数据map

    private Integer hasSubmit;// 0进行中 1通过 2驳回 3激活 4提交 5未提交

    private Integer owner;//0技术1财务

    private Boolean hasPermission;//当前用户是否有权限审核

    private Integer status;

    public Boolean getHasPermission() {
        return hasPermission;
    }

    public void setHasPermission(Boolean hasPermission) {
        this.hasPermission = hasPermission;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    private Map<String, EmployeeSelectModel> footerMap;//文档footer

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

    public Map<String, EmployeeSelectModel> getFooterMap() {
        return footerMap;
    }

    public void setFooterMap(Map<String, EmployeeSelectModel> footerMap) {
        this.footerMap = footerMap;
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
}
