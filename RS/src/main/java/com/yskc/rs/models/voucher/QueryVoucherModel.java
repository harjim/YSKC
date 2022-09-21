package com.yskc.rs.models.voucher;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * Created by hck
 * on 2020/7/14 15:27
 * description:
 */
public class QueryVoucherModel extends PageParams {

    private String voucherNo;//凭证号
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date voucherMonth;//凭证月份

    private Date beginDate;

    private Date endDate;

    private String summary;//摘要

    private Integer rdType;//费用类型

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Date getVoucherMonth() {
        return voucherMonth;
    }

    public void setVoucherMonth(Date voucherMonth) {
        this.voucherMonth = voucherMonth;
    }
}
