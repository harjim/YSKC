package com.yskc.ms.models.tech;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/3/27 9:06
 * @Description:备案信息
 */
public class BeianInfoModel implements Serializable {

    private Integer id;

    private Integer beianId;

    private Integer year;

    private Integer companyId;

    private Integer customerId;

    private String pname;

    private BigDecimal equipment;
    private BigDecimal initWorkCapital;
    private BigDecimal construction;

    private BigDecimal completeRate;
    private BigDecimal equipmentCost;
    private BigDecimal initWorkCapitalCost;
    private BigDecimal constructionCost;
    private String beianNo;
    private Date beianDate;
    private Integer projectId;
    private String companyName;
    private String productName;

    private BigDecimal totalBudget;//备案总金额

    private BigDecimal totalAmount;//投资总金额

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBeianId() {
        return beianId;
    }

    public void setBeianId(Integer beianId) {
        this.beianId = beianId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public BigDecimal getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(BigDecimal completeRate) {
        this.completeRate = completeRate;
    }

    public BigDecimal getEquipmentCost() {
        return equipmentCost;
    }

    public void setEquipmentCost(BigDecimal equipmentCost) {
        this.equipmentCost = equipmentCost;
    }

    public BigDecimal getInitWorkCapitalCost() {
        return initWorkCapitalCost;
    }

    public void setInitWorkCapitalCost(BigDecimal initWorkCapitalCost) {
        this.initWorkCapitalCost = initWorkCapitalCost;
    }

    public BigDecimal getConstructionCost() {
        return constructionCost;
    }

    public void setConstructionCost(BigDecimal constructionCost) {
        this.constructionCost = constructionCost;
    }

    public String getBeianNo() {
        return beianNo;
    }

    public void setBeianNo(String beianNo) {
        this.beianNo = beianNo;
    }

    public Date getBeianDate() {
        return beianDate;
    }

    public void setBeianDate(Date beianDate) {
        this.beianDate = beianDate;
    }


    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
