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
 * @date 2019-11-08 10:03:17
 */
@TableName("sys_docList")
public class DocListEntity implements Serializable {
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
		private Integer listType;
		/**
	* 
	*/
		private String docName;
		/**
	* 
	*/
		private String desciption;
		/**
	* 
	*/
		private Boolean optional;
		/**
	* 
	*/
		private Integer seq;
		/**
	* 
	*/
		private Integer companyId;
	private String classify;
	private String subClassify;
	private String rdActivities;
	private String samplePath;

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getSubClassify() {
		return subClassify;
	}

	public void setSubClassify(String subClassify) {
		this.subClassify = subClassify;
	}

	public String getRdActivities() {
		return rdActivities;
	}

	public void setRdActivities(String rdActivities) {
		this.rdActivities = rdActivities;
	}

	public String getSamplePath() {
		return samplePath;
	}

	public void setSamplePath(String samplePath) {
		this.samplePath = samplePath;
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
	    public void setListType(Integer listType){
    	this.listType=listType;
	}
	public Integer getListType(){
		return listType;
	}
	    public void setDocName(String docName){
    	this.docName=docName;
	}
	public String getDocName(){
		return docName;
	}
	    public void setDesciption(String desciption){
    	this.desciption=desciption;
	}
	public String getDesciption(){
		return desciption;
	}
	    public void setOptional(Boolean optional){
    	this.optional=optional;
	}
	public Boolean getOptional(){
		return optional;
	}
	    public void setSeq(Integer seq){
    	this.seq=seq;
	}
	public Integer getSeq(){
		return seq;
	}
	    public void setCompanyId(Integer companyId){
    	this.companyId=companyId;
	}
	public Integer getCompanyId(){
		return companyId;
	}
	
}
