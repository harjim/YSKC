package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-26 10:45:09
 */
@TableName("p_patent")
public class PatentEntity implements Serializable {
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
		private Integer lastUpdatorId;
		/**
	* 
	*/
		private Date createTime;
		/**
	* 
	*/
		private Date lastUpdateTime;
		/**
	* 
	*/
		private Integer msCreatorId;
		/**
	* 
	*/
		private Integer msLastUpdatorId;
		/**
	* 
	*/
		private Integer companyId;
		/**
	* 
	*/
		private Integer projectId;
		/**
	* 
	*/
		private String patentNo;
		/**
	* 
	*/
		private String patentName;
		/**
	* 
	*/
		private Date applyDate;
		/**
	* 
	*/
		private Date authorizeDate;
		/**
	* 
	*/
		private String ownership;
		/**
	* 
	*/
		private Date searchDate;
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
	    public void setLastUpdatorId(Integer lastUpdatorId){
    	this.lastUpdatorId=lastUpdatorId;
	}
	public Integer getLastUpdatorId(){
		return lastUpdatorId;
	}
	    public void setCreateTime(Date createTime){
    	this.createTime=createTime;
	}
	public Date getCreateTime(){
		return createTime;
	}
	    public void setLastUpdateTime(Date lastUpdateTime){
    	this.lastUpdateTime=lastUpdateTime;
	}
	public Date getLastUpdateTime(){
		return lastUpdateTime;
	}
	    public void setMsCreatorId(Integer msCreatorId){
    	this.msCreatorId=msCreatorId;
	}
	public Integer getMsCreatorId(){
		return msCreatorId;
	}
	    public void setMsLastUpdatorId(Integer msLastUpdatorId){
    	this.msLastUpdatorId=msLastUpdatorId;
	}
	public Integer getMsLastUpdatorId(){
		return msLastUpdatorId;
	}
	    public void setCompanyId(Integer companyId){
    	this.companyId=companyId;
	}
	public Integer getCompanyId(){
		return companyId;
	}
	    public void setProjectId(Integer projectId){
    	this.projectId=projectId;
	}
	public Integer getProjectId(){
		return projectId;
	}
	    public void setPatentNo(String patentNo){
    	this.patentNo=patentNo;
	}
	public String getPatentNo(){
		return patentNo;
	}
	    public void setPatentName(String patentName){
    	this.patentName=patentName;
	}
	public String getPatentName(){
		return patentName;
	}
	    public void setApplyDate(Date applyDate){
    	this.applyDate=applyDate;
	}
	public Date getApplyDate(){
		return applyDate;
	}
	    public void setAuthorizeDate(Date authorizeDate){
    	this.authorizeDate=authorizeDate;
	}
	public Date getAuthorizeDate(){
		return authorizeDate;
	}
	    public void setOwnership(String ownership){
    	this.ownership=ownership;
	}
	public String getOwnership(){
		return ownership;
	}
	    public void setSearchDate(Date searchDate){
    	this.searchDate=searchDate;
	}
	public Date getSearchDate(){
		return searchDate;
	}
	
}
