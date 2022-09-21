package com.yskc.ms.models.company;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/12/7 17:29
 * description:公司基本信息
 */
public class CompanyInfoModel implements Serializable {

    private Integer companyId;

    private Integer customerId;

    private String companyName;//公司名称

    private String invoiceTitle;//发票抬头

    private String companyAddress;//单位地址*

    private String industryCode;//所属行业*

    private String mainIndustry;//主行业

    private String linkMan;//联系人

    private String linkTel;//联系电话

    private Integer deptId;//所属部门id*

    private Integer owerId;//业务员id*

    private String taxpayerId;//纳税人识别号

    private String creditCode;//统一社会码

    private String owner;//法人

    private Integer capital;//注册资金
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date registerTime;//注册时间

    private String email;//邮箱
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date firstDevFee;//首次发生研发费时间

    private String accountSystem;//会计制度

    private String taxAuthorities;//所属税务机关

    private String realTaxAuthorities;//办理税务机关

    private Boolean hasDevAccount;//是否有研发专用帐

    private Boolean highTec;//是否高新

    private String highTecIndustry;//高新领域

    private String remark;//备注

    private String deptName;//部门名称

    private String ownerName;//业务员名称

    private String addressCode;//所在地代码

    private String capitalUnit;//资金单位
    private String companyLevel;//客户级别
    private Integer status;//状态

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }

    public String getCapitalUnit() {
        return capitalUnit;
    }

    public void setCapitalUnit(String capitalUnit) {
        this.capitalUnit = capitalUnit;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
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

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getCapital() {
        return capital;
    }

    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
