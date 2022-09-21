package com.yskc.ms.models.rs;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/11/3 10:42
 * description:
 */
public class TechProjectResultModel implements Serializable {

    private String reportType;

    private Integer pyear;

    private String pname;

    private Integer productId;

    private Integer id;

    private String addressCode;

    private Integer sourceProjectId;

    private Integer reportYear;//申报年

    public Integer getReportYear() {
        return reportYear;
    }

    public void setReportYear(Integer reportYear) {
        this.reportYear = reportYear;
    }

    public Integer getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(Integer sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Integer getPyear() {
        return pyear;
    }

    public void setPyear(Integer pyear) {
        this.pyear = pyear;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }
}
