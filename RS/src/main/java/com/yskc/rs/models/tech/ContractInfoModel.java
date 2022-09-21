package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 17:28
 * @Description:合同信息model
 */
public class ContractInfoModel implements Serializable {

    private Integer id;

    private String ename;

    private String emodal;

    private Integer seq;
    private Integer quantity;
    private BigDecimal amount;
    private Boolean partPurchase;
    private Boolean secondHand;
    private Boolean traderPurchase;

    private String contractNo;

    private String signTarget;

    private String filePath;

    private Integer investmentId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date contractDate;

    private Integer contractId;

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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

    public Date getContractDate() {
        return contractDate;
    }

    public void setContractDate(Date contractDate) {
        this.contractDate = contractDate;
    }

    public Integer getInvestmentId() {
        return investmentId;
    }

    public void setInvestmentId(Integer investmentId) {
        this.investmentId = investmentId;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getSignTarget() {
        return signTarget;
    }

    public void setSignTarget(String signTarget) {
        this.signTarget = signTarget;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
