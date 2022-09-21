package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author hck
 * @since 2021-03-18
 */
@TableName("t_contract_detail")
public class ContractDetail extends BaseEntity {
    @TableId
    private Integer id;
    private Integer companyId;
    private Integer contractId;
    private String ename;
    private String emodal;

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

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
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
