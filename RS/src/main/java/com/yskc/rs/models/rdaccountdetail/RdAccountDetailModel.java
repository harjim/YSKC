package com.yskc.rs.models.rdaccountdetail;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdaccountdetail
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-30 11:22
 * @Description: 费用明细model
 */
public class RdAccountDetailModel implements Serializable {


    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rdDate;

    private String accNumber;

    private String summary;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal debit;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal credit;

    private String direction;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal balance;

    private Integer accType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRdDate() {
        return rdDate;
    }

    public void setRdDate(Date rdDate) {
        this.rdDate = rdDate;
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

    public Integer getAccType() {
        return accType;
    }

    public void setAccType(Integer accType) {
        this.accType = accType;
    }
}
