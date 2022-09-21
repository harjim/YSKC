package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: rs
 * @description:
 * @author: wjy
 * @create: 2022-09-21 10:00
 **/
@TableName("t_beian_summary")
public class BeianSummaryEntity extends BaseEntity implements Serializable {
    @TableId
    private Integer id;
    private Integer companyId;
    private Integer beianId;
    
    private BigDecimal totalAmount;
    
    private BigDecimal amount;
    
    private BigDecimal equipment;
    
    private BigDecimal initWorkCapital;
    
    private BigDecimal construction;

    
    private BigDecimal totalAmountTax;
    
    private BigDecimal amountTax;
    
    private BigDecimal equipmentTax;
    
    private BigDecimal initWorkCapitalTax;
    
    private BigDecimal constructionTax;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getEquipment() {
        return equipment;
    }

    public void setEquipment(BigDecimal equipment) {
        this.equipment = equipment;
    }

    public BigDecimal getInitWorkCapital() {
        return initWorkCapital;
    }

    public void setInitWorkCapital(BigDecimal initWorkCapital) {
        this.initWorkCapital = initWorkCapital;
    }

    public BigDecimal getConstruction() {
        return construction;
    }

    public void setConstruction(BigDecimal construction) {
        this.construction = construction;
    }

    public BigDecimal getTotalAmountTax() {
        return totalAmountTax;
    }

    public void setTotalAmountTax(BigDecimal totalAmountTax) {
        this.totalAmountTax = totalAmountTax;
    }

    public BigDecimal getAmountTax() {
        return amountTax;
    }

    public void setAmountTax(BigDecimal amountTax) {
        this.amountTax = amountTax;
    }

    public BigDecimal getEquipmentTax() {
        return equipmentTax;
    }

    public void setEquipmentTax(BigDecimal equipmentTax) {
        this.equipmentTax = equipmentTax;
    }

    public BigDecimal getInitWorkCapitalTax() {
        return initWorkCapitalTax;
    }

    public void setInitWorkCapitalTax(BigDecimal initWorkCapitalTax) {
        this.initWorkCapitalTax = initWorkCapitalTax;
    }

    public BigDecimal getConstructionTax() {
        return constructionTax;
    }

    public void setConstructionTax(BigDecimal constructionTax) {
        this.constructionTax = constructionTax;
    }
}
