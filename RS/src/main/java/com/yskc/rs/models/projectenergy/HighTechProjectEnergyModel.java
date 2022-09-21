package com.yskc.rs.models.projectenergy;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @DateTime: 2022/5/23 17:03
 * @Description:
 * @author: hsx
 */
public class HighTechProjectEnergyModel implements Serializable {

    private Integer id;

    /**
     * 单价
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal unitPrice;

    /**
     * 数量
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal quantity;

    /**
     * 金额
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;

    /**
     * 总工时
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalHour;

    /**
     * 研发工时
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdHour;

    /**
     * 研发金额
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdAmount;

    /**
     * 研发用量
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdQuantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public BigDecimal getRdQuantity() {
        return rdQuantity;
    }

    public void setRdQuantity(BigDecimal rdQuantity) {
        this.rdQuantity = rdQuantity;
    }
}
