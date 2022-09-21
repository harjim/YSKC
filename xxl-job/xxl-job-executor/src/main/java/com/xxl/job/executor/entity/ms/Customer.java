package com.xxl.job.executor.entity.ms;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
/**
 * 客户实体
 */
@TableName("customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    private String companyName;
    /**
     *
     */
    private String companyAddress;
    /**
     *
     */
    private String addressCode;
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
    /**
     *
     */
    private String mainIndustry;
    /**
     *
     */
    private String linkMan;
    private String linkTel;
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
    private Integer capital;
    private String capitalUnit;
    /**
     *
     */
//    private Integer members;
    /**
     *
     */
//    private Integer depts;
    /**
     *
     */
    private LocalDateTime registerTime;
    /**
     *
     */
    private LocalDateTime firstDevFee;
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

    private LocalDateTime creatorTime;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private LocalDateTime lastUpdateTime;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     * 0:用户注册，1：氚云，2：新增
     */
    private Integer from;
    /**
     * 0: 未签, 10:邀约 20:拜访,30:已签
     */
    private Integer status;

    private String remark;

    private Integer deptId;

    private Integer owerId;

    private String companyLevel;

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getCapitalUnit() {
        return capitalUnit;
    }

    public void setCapitalUnit(String capitalUnit) {
        this.capitalUnit = capitalUnit;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public LocalDateTime getCreatorTime() {
        return creatorTime;
    }

    public void setCreatorTime(LocalDateTime creatorTime) {
        this.creatorTime = creatorTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
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

    public String getTaxpayerId() {
        return taxpayerId;
    }

    public void setTaxpayerId(String taxpayerId) {
        this.taxpayerId = taxpayerId == null ? "" : taxpayerId;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? "" : creditCode;
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
        this.email = email == null ? "" : email;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? "" : owner;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime == null ? LocalDateTime.now() : registerTime;
    }

    public LocalDateTime getFirstDevFee() {
        return firstDevFee;
    }

    public void setFirstDevFee(LocalDateTime firstDevFee) {
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

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    /**
     * 0: 未签, 10:邀约 20:拜访,30:已签
     */
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

