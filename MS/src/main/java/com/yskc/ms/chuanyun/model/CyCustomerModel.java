package com.yskc.ms.chuanyun.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CyCustomerModel {
    @JsonProperty(value = "ObjectId")
    private String objectid ;
    @JsonProperty(value = "Name")
    private String name;
    @JsonProperty(value = "CreatedBy")
    private String createdBy;
    @JsonProperty(value = "CreatedTime")
    private String createdTime;
    @JsonProperty(value = "ModifiedBy")
    private String modifiedBy;
    @JsonProperty(value = "ModifiedTime")
    private String modifiedTime;
    @JsonProperty(value = "WorkflowInstanceId")
    private String workflowInstanceId;
    @JsonProperty(value = "Status")
    private Integer status;
    @JsonProperty(value = "ClientName")
    private String clientName;
    @JsonProperty(value = "StrategicLevel")
    private String strategicLevel;
    @JsonProperty(value = "ClientLevel")
    private String clientLevel;
    @JsonProperty(value = "SalesRegion")
    private String salesRegion;
    @JsonProperty(value = "ContactName")
    private String contactName;
    @JsonProperty(value = "Mobile")
    private String mobile;
    @JsonProperty(value = "F0000003")
    private String f0000003;
    @JsonProperty(value = "F0000027")
    private String f0000027;
    @JsonProperty(value = "Addr")
    private String addr;
    @JsonProperty(value = "SalesOpportunitie")
    private String salesOpportunitie;
    @JsonProperty(value = "F0000022")
    private String f0000022;

    @JsonProperty(value = "OwnerId")
    private String ownerId;
    @JsonProperty(value = "OwnerDeptId")
    private String ownerDeptId;
    @JsonProperty(value = "Origin")
    private String origin;

    @JsonProperty(value = "TaxID")
    private String taxID;
    @JsonProperty(value = "ClientFPName")
    private String clientFPName;
    @JsonProperty(value = "Bank")
    private String bank;
    @JsonProperty(value = "AccountNumber")
    private String accountNumber;
    @JsonProperty(value = "Address")
    private String address;

    @JsonProperty(value = "F0000038")
    private List<String> tempF0000038;
    private String f0000038;
    @JsonProperty(value = "SalesAssistant")
    private List<String> tempSalesAssistant;
    private String salesAssistant;
    @JsonProperty(value = "F0000039")
    private String f0000039;
    @JsonProperty(value = "F0000029")
    private String f0000029;
    @JsonProperty(value = "F0000033")
    private String f0000033;
    @JsonProperty(value = "F0000031")
    private String f0000031;
    @JsonProperty(value = "F0000034")
    private String f0000034;
    @JsonProperty(value = "F0000036")
    private String f0000036;
    @JsonProperty(value = "F0000037")
    private String f0000037;

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getWorkflowInstanceId() {
        return workflowInstanceId;
    }

    public void setWorkflowInstanceId(String workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getStrategicLevel() {
        return strategicLevel;
    }

    public void setStrategicLevel(String strategicLevel) {
        this.strategicLevel = strategicLevel;
    }

    public String getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(String clientLevel) {
        this.clientLevel = clientLevel;
    }

    public String getSalesRegion() {
        return salesRegion;
    }

    public void setSalesRegion(String salesRegion) {
        this.salesRegion = salesRegion;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getF0000003() {
        return f0000003;
    }

    public void setF0000003(String f0000003) {
        this.f0000003 = f0000003;
    }

    public String getF0000027() {
        return f0000027;
    }

    public void setF0000027(String f0000027) {
        this.f0000027 = f0000027;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getSalesOpportunitie() {
        return salesOpportunitie;
    }

    public void setSalesOpportunitie(String salesOpportunitie) {
        this.salesOpportunitie = salesOpportunitie;
    }

    public String getF0000022() {
        return f0000022;
    }

    public void setF0000022(String f0000022) {
        this.f0000022 = f0000022;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerDeptId() {
        return ownerDeptId;
    }

    public void setOwnerDeptId(String ownerDeptId) {
        this.ownerDeptId = ownerDeptId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getTaxID() {
        return taxID;
    }

    public void setTaxID(String taxID) {
        this.taxID = taxID;
    }

    public String getClientFPName() {
        return clientFPName;
    }

    public void setClientFPName(String clientFPName) {
        this.clientFPName = clientFPName;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getTempF0000038() {
        return tempF0000038;
    }

    public void setTempF0000038(List<String> tempF0000038) {
        this.tempF0000038 = tempF0000038;
    }

    public String getF0000038() {
        return f0000038;
    }

    public void setF0000038(String f0000038) {
        this.f0000038 = f0000038;
    }

    public List<String> getTempSalesAssistant() {
        return tempSalesAssistant;
    }

    public void setTempSalesAssistant(List<String> tempSalesAssistant) {
        this.tempSalesAssistant = tempSalesAssistant;
    }

    public String getSalesAssistant() {
        return salesAssistant;
    }

    public void setSalesAssistant(String salesAssistant) {
        this.salesAssistant = salesAssistant;
    }

    public String getF0000039() {
        return f0000039;
    }

    public void setF0000039(String f0000039) {
        this.f0000039 = f0000039;
    }

    public String getF0000029() {
        return f0000029;
    }

    public void setF0000029(String f0000029) {
        this.f0000029 = f0000029;
    }

    public String getF0000033() {
        return f0000033;
    }

    public void setF0000033(String f0000033) {
        this.f0000033 = f0000033;
    }

    public String getF0000031() {
        return f0000031;
    }

    public void setF0000031(String f0000031) {
        this.f0000031 = f0000031;
    }

    public String getF0000034() {
        return f0000034;
    }

    public void setF0000034(String f0000034) {
        this.f0000034 = f0000034;
    }

    public String getF0000036() {
        return f0000036;
    }

    public void setF0000036(String f0000036) {
        this.f0000036 = f0000036;
    }

    public String getF0000037() {
        return f0000037;
    }

    public void setF0000037(String f0000037) {
        this.f0000037 = f0000037;
    }
}
