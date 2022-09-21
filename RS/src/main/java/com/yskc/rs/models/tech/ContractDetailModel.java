package com.yskc.rs.models.tech;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: hck
 * @DateTime: 2021/3/20 10:14
 * @Description:
 */
public class ContractDetailModel implements Serializable {

    private Integer id;

    private String ename;

    private String emodal;

    private Integer contractId;

    private Integer investmentId;
    private Integer quantity;
    private BigDecimal amount;
    private Boolean partPurchase;
    private Boolean secondHand;
    private Boolean traderPurchase;


    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getPartPurchase() {
        return partPurchase;
    }

    public void setPartPurchase(Boolean partPurchase) {
        this.partPurchase = partPurchase;
    }

    public Boolean getSecondHand() {
        return secondHand;
    }

    public void setSecondHand(Boolean secondHand) {
        this.secondHand = secondHand;
    }

    public Boolean getTraderPurchase() {
        return traderPurchase;
    }

    public void setTraderPurchase(Boolean traderPurchase) {
        this.traderPurchase = traderPurchase;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
