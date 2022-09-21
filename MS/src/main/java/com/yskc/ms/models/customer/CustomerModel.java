package com.yskc.ms.models.customer;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CustomerModel {

    private Integer id;
    /**
     *
     */
    private int companyId;
    private String companyName;
    /**
     *
     */
    private String companyAddress;
    /**
     *
     */
    private String addressCode;
    private String[] addressCodeArr;
    private String addressStr;
    /**
     *
     */
    private String taxpayerId;
    /**
     *
     */
    private String creditCode;
    /**
     *
     */
    private String invoiceTitle;
    /**
     *
     */
    private String industryCode;
    private String[] industryCodeArr;
    /**
     *
     */
    private String mainIndustry;
    /**
     *
     */
    private String linkMan;
    private String linkTel;

    private Integer companyType;

    private Integer groupId;

    private String companyLevel;

    private String groupFullPath;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }
/**
 *
 */
//    private String domain="";
    /**
     *
     */
    private String email;
    /**
     *
     */
    private String owner;
    /**
     *
     */
    private BigDecimal capital;
    private String capitalUnit;
    /**
     *
     */
    private Integer members;
    /**
     *
     */
    private Integer depts;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date registerTime;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date firstDevFee;
    /**
     *
     */
    private String accountSystem;
    /**
     *
     */
    private String taxAuthorities;
    /**
     *
     */
    private String realTaxAuthorities;
    /**
     *
     */
    private Boolean hasDevAccount;
    /**
     *
     */
    private Boolean highTec;
    /**
     *
     */
    private String highTecIndustry;
    /**
     *
     */
    private String businessLicense;
    /**
     *
     */
    private String logo;
    /**
     *
     */
    private Date creatorTime;
    /**
     *
     */
    private Integer creatorId;

    private String creatorName;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer lastFollowId;

    private String lastFollowName;
    /**
     *
     */
    private Date lastFollowTime;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Integer from;
    /**
     *
     */
    private Integer status;

    private String remark;

    //氚云objectId
    private String objectid;

    private String ownerUser;

    private String deptName;

    private Integer owerId;
    private String owerName;

    private Integer customerId;

    private Integer deptId;
    private String fullPath;

    public Integer getLastFollowId() {
        return lastFollowId;
    }

    public void setLastFollowId(Integer lastFollowId) {
        this.lastFollowId = lastFollowId;
    }

    public String getLastFollowName() {
        return lastFollowName;
    }

    public void setLastFollowName(String lastFollowName) {
        this.lastFollowName = lastFollowName;
    }

    public Date getLastFollowTime() {
        return lastFollowTime;
    }

    public void setLastFollowTime(Date lastFollowTime) {
        this.lastFollowTime = lastFollowTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public String getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(String ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String[] getIndustryCodeArr() {
        return industryCodeArr;
    }

    public void setIndustryCodeArr(String[] industryCodeArr) {
        this.industryCodeArr = industryCodeArr;
    }

    public String getObjectid() {
        return objectid;
    }

    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }

    public String[] getAddressCodeArr() {
        return addressCodeArr;
    }

    public void setAddressCodeArr(String[] addressCodeArr) {
        this.addressCodeArr = addressCodeArr;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressStr() {
        return addressStr;
    }

    public void setAddressStr(String addressStr) {
        this.addressStr = addressStr;
    }

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getMainIndustry() {
        return mainIndustry;
    }

    public void setMainIndustry(String mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public String getCapitalUnit() {
        return capitalUnit;
    }

    public void setCapitalUnit(String capitalUnit) {
        this.capitalUnit = capitalUnit;
    }

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public Integer getDepts() {
        return depts;
    }

    public void setDepts(Integer depts) {
        this.depts = depts;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getFirstDevFee() {
        return firstDevFee;
    }

    public void setFirstDevFee(Date firstDevFee) {
        this.firstDevFee = firstDevFee;
    }

    public String getAccountSystem() {
        return accountSystem;
    }

    public void setAccountSystem(String accountSystem) {
        this.accountSystem = accountSystem;
    }

    public String getTaxAuthorities() {
        return taxAuthorities;
    }

    public void setTaxAuthorities(String taxAuthorities) {
        this.taxAuthorities = taxAuthorities;
    }

    public String getRealTaxAuthorities() {
        return realTaxAuthorities;
    }

    public void setRealTaxAuthorities(String realTaxAuthorities) {
        this.realTaxAuthorities = realTaxAuthorities;
    }

    public Boolean getHasDevAccount() {
        return hasDevAccount;
    }

    public void setHasDevAccount(Boolean hasDevAccount) {
        this.hasDevAccount = hasDevAccount;
    }

    public Boolean getHighTec() {
        return highTec;
    }

    public void setHighTec(Boolean highTec) {
        this.highTec = highTec;
    }

    public String getHighTecIndustry() {
        return highTecIndustry;
    }

    public void setHighTecIndustry(String highTecIndustry) {
        this.highTecIndustry = highTecIndustry;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }


    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }


    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }


    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(Date creatorTime) {
        this.creatorTime = creatorTime;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    public String getGroupFullPath() {
        return groupFullPath;
    }

    public void setGroupFullPath(String groupFullPath) {
        this.groupFullPath = groupFullPath;
    }
}
