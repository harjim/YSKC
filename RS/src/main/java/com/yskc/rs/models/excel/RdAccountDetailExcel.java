package com.yskc.rs.models.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-30 15:17
 * @Description: 研发费用数据excel
 */
public class RdAccountDetailExcel implements Serializable {

    @Excel(name = "年")
    private Integer cyear;

    @Excel(name = "月")
    private Integer cmonth;

    @Excel(name = "日")
    private Integer cday;

    @Excel(name = "凭证号")
    private String accNumber;

    @Excel(name = "摘要")
    private String summary;

    @Excel(name = "借方")
    private BigDecimal debit;

    @Excel(name = "贷方")
    private BigDecimal credit;

    @Excel(name = "方向")
    private String direction;

    @Excel(name = "余额")
    private BigDecimal balance;

    public Integer getCyear() {
        return cyear;
    }

    public void setCyear(Integer cyear) {
        this.cyear = cyear;
    }

    public Integer getCmonth() {
        return cmonth;
    }

    public void setCmonth(Integer cmonth) {
        this.cmonth = cmonth;
    }

    public Integer getCday() {
        return cday;
    }

    public void setCday(Integer cday) {
        this.cday = cday;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
