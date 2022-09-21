package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/15 10:13
 * description:
 */
public class VoucherExcel implements Serializable {
    @Excel(name = "凭证号", order = 0, fieldName = "voucherNo")
    private String voucherNo;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "凭证日期", order = 1, fieldName = "voucherDate")
    private Date voucherDate;
    @Excel(name = "金额", order = 2, fieldName = "amount")
    private BigDecimal amount;
    @Excel(name = "摘要", order = 3, fieldName = "summary")
    private String summary;
    @Excel(name = "费用类型", order = 4, fieldName = "rdType")
    private String rdType;
    @Excel(name = "项目rd列表", order = 5, fieldName = "projectRds")
    private String projectRds;

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRdType() {
        return rdType;
    }

    public void setRdType(String rdType) {
        this.rdType = rdType;
    }

    public String getProjectRds() {
        return projectRds;
    }

    public void setProjectRds(String projectRds) {
        this.projectRds = projectRds;
    }
}
