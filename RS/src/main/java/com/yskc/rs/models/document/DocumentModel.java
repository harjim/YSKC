package com.yskc.rs.models.document;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.DocumentEntity;

public class DocumentModel {

	private static final long serialVersionUID = 1L;

	/**
* 
*/
	@TableId
	private Integer id;
	/**
* 
*/
	private Integer projectId;
	/**
* 
*/
	private String docNumber;
	/**
* 
*/
	private String docName;
	/**
* 
*/
	private String content;
	/**
* 
*/
	private Integer stageId;
	/**
* 
*/
	private Integer processId;
	/**
* 
*/
	private Integer templateId;
	/**
* 
*/
	private Integer companyId;
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
	
	private List<DocumentEntity> docData;
	
	private String pname;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date beginDate;
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date endDate;
	private Integer rdDeptId;
	private String deptName;
	private Integer estimateExpense;
	private String masterENumber;
	private String ename;
	
	

	public String getMasterENumber() {
		return masterENumber;
	}

	public void setMasterENumber(String masterENumber) {
		this.masterENumber = masterENumber;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getRdDeptId() {
		return rdDeptId;
	}

	public void setRdDeptId(Integer rdDeptId) {
		this.rdDeptId = rdDeptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Integer getEstimateExpense() {
		return estimateExpense;
	}

	public void setEstimateExpense(Integer estimateExpense) {
		this.estimateExpense = estimateExpense;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(String docNumber) {
		this.docNumber = docNumber;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getStageId() {
		return stageId;
	}

	public void setStageId(Integer stageId) {
		this.stageId = stageId;
	}

	public Integer getProcessId() {
		return processId;
	}

	public void setProcessId(Integer processId) {
		this.processId = processId;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLastUpdatorId() {
		return lastUpdatorId;
	}

	public void setLastUpdatorId(Integer lastUpdatorId) {
		this.lastUpdatorId = lastUpdatorId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public List<DocumentEntity> getDocData() {
		return docData;
	}

	public void setDocData(List<DocumentEntity> docData) {
		this.docData = docData;
	}
	
}
