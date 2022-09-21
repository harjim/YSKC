package com.yskc.rs.models.summary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SimpleSummaryModel implements Serializable {
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    private Date month;
    private Integer rdType;

    private String rdTitle;

    private BigDecimal rdFunds;

    /**
     *
     */
    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    /**
     *
     */
    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }
}
