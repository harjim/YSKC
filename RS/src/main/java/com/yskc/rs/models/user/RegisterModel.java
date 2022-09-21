package com.yskc.rs.models.user;

public class RegisterModel {
	
	
	private String userName;
    private String password;
    private String linkMan;
    private String linkTel;
    private String companyName;
	private String companyAddress;
	private String[] industryCode;
	
	
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLinkTel() {
		return linkTel;
	}
	public void setLinkTel(String linkTel) {
		this.linkTel = linkTel;
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
	public String[] getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String[] industryCode) {
		this.industryCode = industryCode;
	}

	
	
	
}
