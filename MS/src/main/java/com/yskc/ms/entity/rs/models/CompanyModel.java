package com.yskc.ms.entity.rs.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Size;

/**
 * 公司返回实体
 * @author Administrator
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyModel  implements Serializable  {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	private Integer customerId;
	/**
	 * 
	 */
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
	private String[] industryCodeArr;
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
	private String domain;
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
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date registerTime;
	/**
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
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
	private Date createTime;
	/**
	* 
	*/
	private Integer creatorId;
	/**
	* 
	*/
	private Date lastUpdateTime;
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
	@Size(max=200,message="备注不能超过200个字")
	private String remark;
	
	//主账户
	private String userName;
	
	//密码
	private String password;
	
	//氚云objectId
	private String objectid;

	private List<String> roleNames;
	private String roles;
	private Integer owerId;
	private Integer deptId;

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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCapitalUnit() {
		return capitalUnit;
	}

	public void setCapitalUnit(String capitalUnit) {
		this.capitalUnit = capitalUnit;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public List<String> getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(List<String> roleNames) {
		this.roleNames = roleNames;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getObjectid() {
		return objectid;
	}
	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
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
	
	public String[] getAddressCodeArr() {
		return addressCodeArr;
	}
	public void setAddressCodeArr(String[] addressCodeArr) {
		this.addressCodeArr = addressCodeArr;
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
	public String[] getIndustryCodeArr() {
		return industryCodeArr;
	}
	public void setIndustryCodeArr(String[] industryCodeArr) {
		this.industryCodeArr = industryCodeArr;
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
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
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
}
