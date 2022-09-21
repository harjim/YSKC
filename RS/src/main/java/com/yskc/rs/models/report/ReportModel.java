package com.yskc.rs.models.report;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yskc.rs.models.project.ProjectListModel;

/**
 * 申报实体
 * 
 * @author Administrator
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportModel implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* 
	*/
	private Integer id;
	/**
	* 
	*/
	private Integer ryear;
	/**
	* 
	*/
	private String rname;
	/**
	* 
	*/
	private Integer companyId;
	/**
	* 
	*/
	private String remark;
	/**
	* 
	*/
	private Integer creatorId;
	/**
	* 
	*/
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	private Date createTime;

	/**
	 * RD集合
	 * 
	 * @param id
	 */
	private List<ProjectListModel> projectData;
	
	private Integer projectNum;
	
	private Integer estimateExpenseNum;

	
	public Integer getProjectNum() {
		return projectNum;
	}

	public void setProjectNum(Integer projectNum) {
		this.projectNum = projectNum;
	}

	public Integer getEstimateExpenseNum() {
		return estimateExpenseNum;
	}

	public void setEstimateExpenseNum(Integer estimateExpenseNum) {
		this.estimateExpenseNum = estimateExpenseNum;
	}

	public List<ProjectListModel> getProjectData() {
		return projectData;
	}

	public void setProjectData(List<ProjectListModel> projectData) {
		this.projectData = projectData;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setRyear(Integer ryear) {
		this.ryear = ryear;
	}

	public Integer getRyear() {
		return ryear;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRname() {
		return rname;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

}
