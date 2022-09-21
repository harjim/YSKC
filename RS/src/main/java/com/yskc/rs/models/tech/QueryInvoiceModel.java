package com.yskc.rs.models.tech;

import com.yskc.rs.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 11:37
 * @Description:
 */
public class QueryInvoiceModel extends PageParams{

    private String invoiceNo;

    private String ename;

    private Integer investmentId;

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
