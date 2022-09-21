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
 * @date 2019-11-20 10:28:10
 */
@TableName("sys_docOperator")
public class DocOperatorEntity implements Serializable {
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
		private Integer listId;
		/**
	* 
	*/
		private String operators;
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
	    public void setListId(Integer listId){
    	this.listId=listId;
	}
	public Integer getListId(){
		return listId;
	}
	    public void setOperators(String operators){
    	this.operators=operators;
	}
	public String getOperators(){
		return operators;
	}
	
}
