package com.yskc.ms.models.projectAudit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: ms
 * @description: 据项目月份返回材料数据
 * @author: cyj
 * @create: 2022-04-27 11:46
 **/
public class RdFeeMaterialModel extends PageParams  implements Serializable {
    private String mcode;

    private String mname;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date acqDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal unitPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal quantity;
    private String unit;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal used;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdAmount;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
