package com.yskc.ms.models.rs;

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

    private Integer year;

    private Boolean importFooterName = true;
    private Boolean budgetDetail = true;
    private List<Integer> pDocFileIds;


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

    public DataModel() {
    }

    public Boolean getImportFooterName() {
        return importFooterName;
    }

    public void setImportFooterName(Boolean importFooterName) {
        this.importFooterName = importFooterName;
    }


    public Boolean getBudgetDetail() {
        return budgetDetail == null ? true : budgetDetail;
    }

    public void setBudgetDetail(Boolean budgetDetail) {
        this.budgetDetail = budgetDetail;
    }

    public DataModel(Integer id, String data, Integer pDocFileId, Integer projectId, String docFileName,
                     Boolean importFooterName, Boolean budgetDetail) {
        this.id = id;
        this.data = data;
        this.pDocFileId = pDocFileId;
        this.projectId = projectId;
        this.docFileName = docFileName;
        this.importFooterName = importFooterName;
        this.budgetDetail = budgetDetail;
    }

    public List<Integer> getpDocFileIds() {
        return pDocFileIds;
    }

    public void setpDocFileIds(List<Integer> pDocFileIds) {
        this.pDocFileIds = pDocFileIds;
    }
}
