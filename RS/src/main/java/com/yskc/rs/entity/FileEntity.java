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
 * @date 2019-09-29 13:52:34
 */
@TableName("p_file")
public class FileEntity implements Serializable {
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
		private Integer projectId;
		/**
	* 
	*/
		private Integer companyId;
		/**
	* 
	*/
		private String fileName;
		/**
	* 
	*/
		private String filePath;
		/**
	* 
	*/
		private Integer type;

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
	    public void setProjectId(Integer projectId){
    	this.projectId=projectId;
	}
	public Integer getProjectId(){
		return projectId;
	}
	    public void setCompanyId(Integer companyId){
    	this.companyId=companyId;
	}
	public Integer getCompanyId(){
		return companyId;
	}
	    public void setFileName(String fileName){
    	this.fileName=fileName;
	}
	public String getFileName(){
		return fileName;
	}
	    public void setFilePath(String filePath){
    	this.filePath=filePath;
	}
	public String getFilePath(){
		return filePath;
	}
	    public void setType(Integer type){
    	this.type=type;
	}
	public Integer getType(){
		return type;
	}
	
}
