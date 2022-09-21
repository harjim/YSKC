package com.yskc.ms.models;

import com.yskc.ms.models.params.PageParams;

/**
 * @DateTime: 2021/9/6 16:27
 * @Description:
 */
public class QueryDocumentModel extends PageParams {

    private Integer companyId;

    private Integer year;

    private Integer fileType;

    private String fileName;

    private String pname;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
