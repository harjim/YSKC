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
 * @date 2019-09-25 17:16:17
 */
@TableName("c_annualReport")
public class AnnualReportEntity implements Serializable {
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
		private Integer year;
		/**
	* 
	*/
		private Integer type;
		/**
	* 
	*/
		private String reportName;
		/**
	* 
	*/
		private String filePath;
		private Integer month;

	private Integer msCreatorId;
	private Integer msLastUpdatorId;

	public Integer getMsCreatorId() {
		return msCreatorId;
	}

	public void setMsCreatorId(Integer msCreatorId) {
		this.msCreatorId = msCreatorId;
	}

	public Integer getMsLastUpdatorId() {
		return msLastUpdatorId;
	}

	public void setMsLastUpdatorId(Integer msLastUpdatorId) {
		this.msLastUpdatorId = msLastUpdatorId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

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
	    public void setYear(Integer year){
    	this.year=year;
	}
	public Integer getYear(){
		return year;
	}
	    public void setType(Integer type){
    	this.type=type;
	}
	public Integer getType(){
		return type;
	}
	    public void setReportName(String reportName){
    	this.reportName=reportName;
	}
	public String getReportName(){
		return reportName;
	}
	    public void setFilePath(String filePath){
    	this.filePath=filePath;
	}
	public String getFilePath(){
		return filePath;
	}
	
}
