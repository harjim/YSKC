package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

public class PatentExcel implements Serializable {
    @Excel(name = "专利号", order = 0, fieldName = "patentNo")
    private String patentNo;
    @Excel(name = "专利名称", order = 1, fieldName = "patentName")
    private String patentName;
    @Excel(name = "申请日期", order = 2, fieldName = "applyDate", dateFormat = "yyyy-MM-dd")
    private Date applyDate;
    @Excel(name = "授权日期", order = 3, fieldName = "authorizeDate", dateFormat = "yyyy-MM-dd")
    private Date authorizeDate;
    @Excel(name = "专利权属", order = 4, fieldName = "ownership")
    private String ownership;
    @Excel(name = "缴费收据报支查找年月", order = 5, fieldName = "searchDate", dateFormat = "yyyy-MM-dd")
    private Date searchDate;

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getAuthorizeDate() {
        return authorizeDate;
    }

    public void setAuthorizeDate(Date authorizeDate) {
        this.authorizeDate = authorizeDate;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public Date getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(Date searchDate) {
        this.searchDate = searchDate;
    }
}
