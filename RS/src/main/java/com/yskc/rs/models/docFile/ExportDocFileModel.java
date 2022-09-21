package com.yskc.rs.models.docFile;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/9/10 9:14
 * description:导出过程文件model
 */
public class ExportDocFileModel implements Serializable {


    private Integer projectId;

    private Integer currentYear;

    private Integer companyId;

    private List<Integer> pDocFileIds;

    private Boolean importFooterName = true;
    private Boolean catalogue = true;
    private Boolean pageNum = true;
    private Boolean budgetDetail = true;
    private Boolean header = true;
    private Boolean cover = true;

    public Boolean getCover() {
        return cover;
    }

    public void setCover(Boolean cover) {
        this.cover = cover;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getpDocFileIds() {
        return pDocFileIds;
    }

    public void setpDocFileIds(List<Integer> pDocFileIds) {
        this.pDocFileIds = pDocFileIds;
    }

    public Boolean getImportFooterName() {
        return importFooterName;
    }

    public void setImportFooterName(Boolean importFooterName) {
        this.importFooterName = importFooterName;
    }

    public Boolean getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(Boolean catalogue) {
        this.catalogue = catalogue;
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

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
    }
}
