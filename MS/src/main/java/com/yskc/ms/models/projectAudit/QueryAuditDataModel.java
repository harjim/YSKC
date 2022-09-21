package com.yskc.ms.models.projectAudit;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/11/26 10:07
 * description:审核数据查询（研发人员及设备）
 */
public class QueryAuditDataModel extends PageParams {

    private Integer companyId;

    private Integer year;

    private String companyName;

    private String patentName;

    private String patentNo;

    private String inventor;

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

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

    public QueryAuditDataModel() {
    }

    public QueryAuditDataModel(Integer companyId, Integer year) {
        this.companyId = companyId;
        this.year = year;
    }
}
