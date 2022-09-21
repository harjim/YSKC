package com.yskc.rs.models.tech;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/3/19 9:18
 * @Description:
 */
public class InvestmentModel implements Serializable {

    private Integer id;

    private String type;//支出类别

    private String typeName;

    private String ename;//设备名称

    private String emodal;//设备型号

    private Date entryDate;//入账时间

    private Integer beianId;

    private BigDecimal countQuantity;//总数量

    private BigDecimal countAmount;//不含税金额合计

    private BigDecimal countBank;//银行金额合计

    private List<NameplateModel> nameplateModels;//铭牌

    private Integer order;//排序

    private BigDecimal usagePower;
    private BigDecimal loadFactor;
    private BigDecimal runRate;
    private BigDecimal workHour;

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

    public List<NameplateModel> getNameplateModels() {
        return nameplateModels;
    }

    public void setNameplateModels(List<NameplateModel> nameplateModels) {
        this.nameplateModels = nameplateModels;
    }

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public BigDecimal getCountQuantity() {
        return countQuantity;
    }

    public void setCountQuantity(BigDecimal countQuantity) {
        this.countQuantity = countQuantity;
    }

    public BigDecimal getCountAmount() {
        return countAmount;
    }

    public void setCountAmount(BigDecimal countAmount) {
        this.countAmount = countAmount;
    }

    public BigDecimal getCountBank() {
        return countBank;
    }

    public void setCountBank(BigDecimal countBank) {
        this.countBank = countBank;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
