package com.yskc.rs.models.document;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.document
 * @Author: zhangdingfu
 * @CreateTime: 2019-08-29 16:17
 * @Description: 文档model
 */
public class DataModel implements Serializable {

    private Integer id;

    private String data;

    private String templateName;

    private String docName;

    private Integer pDocFileId;

    private Integer projectId;

    private Integer currentYear;

    private Integer companyId;

    private String docFileName;

    private Integer docFileId;//id+docFileId+projectId+year+templateId 切换模板使用参数

    private Integer year;

    private Integer templateId;

    /**
     * 引入脚部人名(默认为true)
     */
    private Boolean importFooterName = true;

    private Boolean pageNum = true;

    private Boolean budgetDetail = true;

    private Boolean header = true;

    private List<Integer> pDocFileIds;

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public DataModel() {
    }

    public DataModel(Integer pDocFileId, Integer projectId, Integer currentYear, Integer companyId) {
        this.pDocFileId = pDocFileId;
        this.projectId = projectId;
        this.currentYear = currentYear;
        this.companyId = companyId;
    }

    public DataModel(Integer id, String data, Integer pDocFileId, Integer projectId, String docFileName,
                     Boolean importFooterName,Boolean budgetDetail) {
        this.id = id;
        this.data = data;
        this.pDocFileId = pDocFileId;
        this.projectId = projectId;
        this.docFileName = docFileName;
        this.importFooterName = importFooterName;
        this.budgetDetail = budgetDetail;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public Boolean getImportFooterName() {
        return importFooterName;
    }

    public void setImportFooterName(Boolean importFooterName) {
        this.importFooterName = importFooterName;
    }

    public Boolean getPageNum() {
        return pageNum;
    }

    public void setPageNum(Boolean pageNum) {
        this.pageNum = pageNum;
    }

    public Boolean getBudgetDetail() {
        return budgetDetail;
    }

    public void setBudgetDetail(Boolean budgetDetail) {
        this.budgetDetail = budgetDetail;
    }

    public List<Integer> getpDocFileIds() {
        return pDocFileIds;
    }

    public void setpDocFileIds(List<Integer> pDocFileIds) {
        this.pDocFileIds = pDocFileIds;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }
}
