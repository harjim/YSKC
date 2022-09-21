package com.yskc.rs.models.workSheet;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.workSheet
 * @Author: zhangdingfu
 * @CreateTime: 2019-11-21 18:05
 * @Description: 研发凭证
 */
public class VoucherModel implements Serializable {

    private String yearStr;

    private String monthStr;

    private String dayStr;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal expenseAmount;

    private String voucherNum;

    private String rdNum;

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public String getDayStr() {
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
    }

    public BigDecimal getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(BigDecimal expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getVoucherNum() {
        return voucherNum;
    }

    public void setVoucherNum(String voucherNum) {
        this.voucherNum = voucherNum;
    }

    public VoucherModel() {
    }

    public VoucherModel(String yearStr, String monthStr, String dayStr, BigDecimal expenseAmount, String voucherNum, String rdNum) {
        this.yearStr = yearStr;
        this.monthStr = monthStr;
        this.dayStr = dayStr;
        this.expenseAmount = expenseAmount;
        this.voucherNum = voucherNum;
        this.rdNum = rdNum;
    }

    public String getRdNum() {
        return rdNum;
    }

    public void setRdNum(String rdNum) {
        this.rdNum = rdNum;
    }
}
