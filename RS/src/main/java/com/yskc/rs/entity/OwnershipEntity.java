package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-26 10:39:36
 */
@TableName("c_ownership")
public class OwnershipEntity implements Serializable {
	private static final long serialVersionUID = 1L;

		/**
	* 
	*/
		@TableId
		private Integer id;
		/**
	* 
	*/
		private Integer creatorId;
		/**
	* 
	*/
		private Date createTime;
		/**
	* 
	*/
		private Integer lastUpdatorId;
		/**
	* 
	*/
		private Date lastUpdateTime;
		/**
	* 
	*/
		private Integer companyId;
		/**
	* 
	*/
		private String shareholder;
		/**
	* 
	*/
		private BigDecimal capitalContribution;
		/**
	* 
	*/
		private String contributionType;
		/**
	* 
	*/
		private BigDecimal proportion;
		    public void setId(Integer id){
    	this.id=id;
	}
	public Integer getId(){
		return id;
	}
	    public void setCreatorId(Integer creatorId){
    	this.creatorId=creatorId;
	}
	public Integer getCreatorId(){
		return creatorId;
	}
	    public void setCreateTime(Date createTime){
    	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	    public void setLastUpdatorId(Integer lastUpdatorId){
    	this.lastUpdatorId=lastUpdatorId;
	}
	public Integer getLastUpdatorId(){
		return lastUpdatorId;
	}
	    public void setLastUpdateTime(Date lastUpdateTime){
    	this.lastUpdateTime=lastUpdateTime;
	}
	public Date getLastUpdateTime(){
		return lastUpdateTime;
	}
	    public void setCompanyId(Integer companyId){
    	this.companyId=companyId;
	}
	public Integer getCompanyId(){
		return companyId;
	}
	    public void setShareholder(String shareholder){
    	this.shareholder=shareholder;
	}
	public String getShareholder(){
		return shareholder;
	}
	    public void setCapitalContribution(BigDecimal capitalContribution){
    	this.capitalContribution=capitalContribution;
	}
	public BigDecimal getCapitalContribution(){
		return capitalContribution;
	}
	    public void setContributionType(String contributionType){
    	this.contributionType=contributionType;
	}
	public String getContributionType(){
		return contributionType;
	}
	    public void setProportion(BigDecimal proportion){
    	this.proportion=proportion;
	}
	public BigDecimal getProportion(){
		return proportion;
	}
	
}
