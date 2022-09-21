package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 *  氚云客户信息
 * @author huronghua
 */
@TableName(value = "h3_customer")
public class CyCustomer {
    @TableId
    private String objectid;
    private String name;
    private String createdBy;
    private String createdTime;
    private String modifiedBy;
    private String modifiedTime;
    private String workflowInstanceId;
    private Integer status;
    private String clientName;
    private String strategicLevel;
    private String clientLevel;
    private String salesRegion;
    private String contactName;
    private String mobile;
    private String f0000003;
    private String f0000027;
    private String addr;
    private String salesOpportunitie;
    private String f0000022;
    private String ownerId;
    private String ownerDeptId;
    private String origin;
    private String taxID;
    private String clientFPName;
    private String bank;
    private String accountNumber;
    private String address;
    private String f0000038;
    private String salesAssistant;
    private String f0000039;
    private String f0000029;
    private String f0000033;
    private String f0000031;
    private String f0000034;
    private String f0000036;
    private String f0000037;
    private Date synDataDateTime;
    private Integer companyId;
    /// 转换状态0：未转，1：已转 2:转化失败(客户已存在)
	private Integer convertStatus;

	
    public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getConvertStatus() {
		return convertStatus;
	}

	public void setConvertStatus(Integer convertStatus) {
		this.convertStatus = convertStatus;
	}

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



    public String getF0000038() {
        return f0000038;
    }

    public void setF0000038(String f0000038) {
        this.f0000038 = f0000038;
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

    public Date getSynDataDateTime() {
        return synDataDateTime;
    }

    public void setSynDataDateTime(Date synDataDateTime) {
        this.synDataDateTime = synDataDateTime;
    }
}
