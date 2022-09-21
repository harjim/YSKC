package com.yskc.docservice.entity.rs.tech;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 *
 * </p>
 *
 * @author hck
 * @since 2021-03-18
 */
@TableName("t_equipment")
public class TechEquipment extends BaseEntity {

    @TableId
    private Integer id;
    private Integer beianId;
    private Integer companyId;
    private String ename;
    private String emodal;
    // private String producePlace;
    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal amount;
    private BigDecimal usagePower;
    private BigDecimal loadFactor;
    private BigDecimal workHour;
    private BigDecimal powerUsed;
    private BigDecimal runRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    // public String getProducePlace() {
    // return producePlace;
    // }

    // public void setProducePlace(String producePlace) {
    // this.producePlace = producePlace;
    // }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUsagePower() {
        return usagePower;
    }

    public void setUsagePower(BigDecimal usagePower) {
        this.usagePower = usagePower;
    }

    public BigDecimal getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(BigDecimal loadFactor) {
        this.loadFactor = loadFactor;
    }

    public BigDecimal getRunRate() {
        return runRate;
    }

    public void setRunRate(BigDecimal runRate) {
        this.runRate = runRate;
    }

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public BigDecimal getPowerUsed() {
        return powerUsed;
    }

    public void setPowerUsed() {
        this.powerUsed = this.usagePower.multiply(this.loadFactor).multiply(this.runRate).multiply(this.workHour);
    }
}
