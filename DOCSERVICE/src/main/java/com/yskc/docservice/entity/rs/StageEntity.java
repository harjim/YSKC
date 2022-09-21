package com.yskc.docservice.entity.rs;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-08 11:28:54
 */
@TableName("p_stage")
public class StageEntity implements Serializable {
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
	@Size(max=50,message="阶段名称不能超过50个字")
	private String stageName;
	/**
	 *
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date beginDate;
	/**
	 *
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;
	/**
	 *
	 */
	@Size(max=200,message="备注不能超过200个字")
	private String remark;
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

	private Integer msCreatorId;
	private Integer msLastUpdatorId;
	@Size(max=200,message="工作内容不能超过200个字")
	private String workDesc;
	private String stageKey;

	public String getStageKey() {
		return stageKey;
	}

	public void setStageKey(String stageKey) {
		this.stageKey = stageKey;
	}

	public String getWorkDesc() {
		return workDesc;
	}

	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}

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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getStageName() {
		return stageName;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
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

	public void setLastUpdatorId(Integer lastUpdatorId) {
		this.lastUpdatorId = lastUpdatorId;
	}

	public Integer getLastUpdatorId() {
		return lastUpdatorId;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

}
