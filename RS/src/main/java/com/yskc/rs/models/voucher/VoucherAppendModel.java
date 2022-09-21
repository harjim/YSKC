package com.yskc.rs.models.voucher;

import com.yskc.rs.config.Constant;

import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.voucher
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-16 18:37
 * @Description: voucher append 类
 */
public class VoucherAppendModel {

    private StringBuilder voucherNo;
    private BigDecimal amount;

    public VoucherAppendModel() {
    }

    public VoucherAppendModel(String voucherNo, BigDecimal amount) {
        this.voucherNo = new StringBuilder(voucherNo);
        this.amount = amount;
    }

    public StringBuilder getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(StringBuilder voucherNo) {
        this.voucherNo = voucherNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public void addVoucherNoAndAmount(String voucherNo, BigDecimal amount) {
        this.voucherNo.append(Constant.PATH_SEPARATOR).append(voucherNo);
        this.amount = this.amount.add(amount);
    }
}
