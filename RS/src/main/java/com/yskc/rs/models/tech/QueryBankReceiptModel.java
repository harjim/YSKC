package com.yskc.rs.models.tech;

import com.yskc.rs.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/31 17:29
 * @Description:银行水单查询
 */
public class QueryBankReceiptModel extends PageParams {

    private Integer investmentId;

    private String voucherNo;//转账记账凭证字号

    private String payee;//收款单位

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }
}
