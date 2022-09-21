package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/22 15:11
 * @Description:
 */
public class AddInvestmentModel implements Serializable {

    private Integer id;

    private String type;

    private String ename;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date entryDate;

    private Integer beianId;

    private Integer order;

    private String emodal;//设备型号
    private BigDecimal usagePower;
    private BigDecimal loadFactor;
    private BigDecimal runRate;
    private BigDecimal workHour;

    private List<Integer> invoiceIds;//发票详情id

    private List<Integer> contractIds;//合同详情id

    private List<Integer> bankReceiptIds;

    private List<NameplateModel> nameplateModels;

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
    }

    public List<Integer> getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(List<Integer> invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public List<Integer> getContractIds() {
        return contractIds;
    }

    public void setContractIds(List<Integer> contractIds) {
        this.contractIds = contractIds;
    }

    public List<Integer> getBankReceiptIds() {
        return bankReceiptIds;
    }

    public void setBankReceiptIds(List<Integer> bankReceiptIds) {
        this.bankReceiptIds = bankReceiptIds;
    }

    public List<NameplateModel> getNameplateModels() {
        return nameplateModels;
    }

    public void setNameplateModels(List<NameplateModel> nameplateModels) {
        this.nameplateModels = nameplateModels;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
