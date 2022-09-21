package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: rs
 * @description:
 * @author: wjy
 * @create: 2022-09-21 09:05
 **/
public class BeianTaxModel implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal equipment;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal initWorkCapital;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal construction;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal completion;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalAmountTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amountTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal equipmentTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal initWorkCapitalTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal constructionTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal completionTax;

    public BeianTaxModel() {
    }

    public BeianTaxModel(BigDecimal totalAmount, BigDecimal amount,
                         BigDecimal equipment, BigDecimal initWorkCapital, BigDecimal construction,
                         BigDecimal completion, BigDecimal totalAmountTax, BigDecimal amountTax,
                         BigDecimal equipmentTax, BigDecimal initWorkCapitalTax,
                         BigDecimal constructionTax, BigDecimal completionTax) {
        this.totalAmount = totalAmount;
        this.amount = amount;
        this.equipment = equipment;
        this.initWorkCapital = initWorkCapital;
        this.construction = construction;
        this.completion = completion;
        this.totalAmountTax = totalAmountTax;
        this.amountTax = amountTax;
        this.equipmentTax = equipmentTax;
        this.initWorkCapitalTax = initWorkCapitalTax;
        this.constructionTax = constructionTax;
        this.completionTax = completionTax;
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

    public BigDecimal getCompletion() {
        return completion;
    }

    public void setCompletion(BigDecimal completion) {
        this.completion = completion;
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

    public BigDecimal getCompletionTax() {
        return completionTax;
    }

    public void setCompletionTax(BigDecimal completionTax) {
        this.completionTax = completionTax;
    }
}
