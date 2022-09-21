package com.yskc.ms.models.rdfunds;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTime: 2022/4/27 15:25
 * @Description:设计归集费用model
 * @author: hsx
 */
public class DesignFeesModel implements Serializable {

    private String dname;

    private Date designDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal dFee;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdAmount;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Date getDesignDate() {
        return designDate;
    }

    public void setDesignDate(Date designDate) {
        this.designDate = designDate;
    }

    public BigDecimal getdFee() {
        return dFee;
    }

    public void setdFee(BigDecimal dFee) {
        this.dFee = dFee;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }
}
