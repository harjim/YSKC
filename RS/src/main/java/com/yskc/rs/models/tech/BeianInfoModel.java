package com.yskc.rs.models.tech;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.tech.BeianChangedEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/3/18 15:30
 * @Description:
 */
public class BeianInfoModel implements Serializable {

    private Integer id;
    private String productName;
    private Integer productId;
    private Integer year;
    private String pname;
    private String constructionPlace;//建设地点（多个）json格式
    private Integer economyType;
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date beginDate;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endDate;
    private String beianNo;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date beianDate;
    private String filePath;
    private String scanFilePath;

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
    private BigDecimal totalAmountTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amountTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal equipmentTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal initWorkCapitalTax;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal constructionTax;

    private Boolean change;

    private Integer sourceProjectId;
    private String remark;
    private String applicant;

    private BeianTaxModel invoiceModel;
    private BeianTaxModel paymentModel;
    private String changedDates;
    private String projectNo;
    private String accountName;
    private String accountPassword;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal powerUsed;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal energyUsed;
    private Integer equipmentCnt;//备案资产项数(sum(t_equipment))
    private Integer equipmentQuantity;//备案资产数量(sum(t_equipment.quantity))

    private List<BeianChangedModel> changedList;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getConstructionPlace() {
        return constructionPlace;
    }

    public void setConstructionPlace(String constructionPlace) {
        this.constructionPlace = constructionPlace;
    }

    public Integer getEconomyType() {
        return economyType;
    }

    public void setEconomyType(Integer economyType) {
        this.economyType = economyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getScanFilePath() {
        return scanFilePath;
    }

    public void setScanFilePath(String scanFilePath) {
        this.scanFilePath = scanFilePath;
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

    public Boolean getChange() {
        return change;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }

    public Integer getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(Integer sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public BeianTaxModel getInvoiceModel() {
        return invoiceModel;
    }

    public void setInvoiceModel(BeianTaxModel invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public BeianTaxModel getPaymentModel() {
        return paymentModel;
    }

    public void setPaymentModel(BeianTaxModel paymentModel) {
        this.paymentModel = paymentModel;
    }

    public String getChangedDates() {
        return changedDates;
    }

    public void setChangedDates(String changedDates) {
        this.changedDates = changedDates;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public BigDecimal getPowerUsed() {
        return powerUsed;
    }

    public void setPowerUsed(BigDecimal powerUsed) {
        this.powerUsed = powerUsed;
    }

    public BigDecimal getEnergyUsed() {
        return energyUsed;
    }

    public void setEnergyUsed(BigDecimal energyUsed) {
        this.energyUsed = energyUsed;
    }

    public Integer getEquipmentCnt() {
        return equipmentCnt;
    }

    public void setEquipmentCnt(Integer equipmentCnt) {
        this.equipmentCnt = equipmentCnt;
    }

    public Integer getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(Integer equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public List<BeianChangedModel> getChangedList() {
        return changedList;
    }

    public void setChangedList(List<BeianChangedModel> changedList) {
        this.changedList = changedList;
    }
}
