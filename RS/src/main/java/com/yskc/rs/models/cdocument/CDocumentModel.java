package com.yskc.rs.models.cdocument;

import java.io.Serializable;

/**
 * @DateTime: 2022/1/14 14:19
 * @Description:风险核算报告AND核算方法Model
 * @author: hsx
 */
public class CDocumentModel implements Serializable {

    private Integer id;

    private Integer companyId;

    private Integer year;

    private Integer type;

    private String data;

    private Integer accountType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }
}
