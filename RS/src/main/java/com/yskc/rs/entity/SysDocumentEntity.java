package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-09-16 11:19:57
 */
@TableName("sys_document")
public class SysDocumentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
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
	private Integer downloadTimes;
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
	private Integer companyId;
	/**
	 *
	 */
	private Integer fileType;


	private Integer lastUpdatorId;

	private Date lastUpdateTime;

	private Integer msCreatorId;
	private Integer msLastUpdatorId;

	/**
	 * 上传4表合并后新增的字段
	 * 2019/10/29
	 */
	private Integer month;
	private Integer projectId;
	/**
	 * 0:基本信息  1:创新项目  2:产业投资项目  3:子项目
	 */
	private Integer projectType;
	private Integer year;
	private String remark;
	private Integer listId;
	private String patentNo;

	/**
	 * 新增文档年月字段
	 * 2019/12/2
	 * @return
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date docMonth;
	/**
	 * 生产日期
	 */
	private Date docDate;
	/**
	 * 工艺线
	 */
	private Integer workShopId;

	private String workshop;

	private String stageKey;

	private Integer buildType;

	public String getStageKey() {
		return stageKey;
	}

	public void setStageKey(String stageKey) {
		this.stageKey = stageKey;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Integer getWorkShopId() {
		return workShopId;
	}

	public void setWorkShopId(Integer workShopId) {
		this.workShopId = workShopId;
	}

	public Date getDocMonth() {
		return docMonth;
	}

	public void setDocMonth(Date docMonth) {
		this.docMonth = docMonth;
	}

	public String getPatentNo() {
		return patentNo;
	}

	public void setPatentNo(String patentNo) {
		this.patentNo = patentNo;
	}

	public Integer getListId() {
		return listId;
	}

	public void setListId(Integer listId) {
		this.listId = listId;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public Integer getProjectType() {
		return projectType;
	}

	public void setProjectType(Integer projectType) {
		this.projectType = projectType;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setDownloadTimes(Integer downloadTimes) {
		this.downloadTimes = downloadTimes;
	}

	public Integer getDownloadTimes() {
		return downloadTimes;
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

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Integer getFileType() {
		return fileType;
	}

	public String getWorkshop() {
		return workshop;
	}

	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}

	public Integer getBuildType() {
		return buildType;
	}

	public void setBuildType(Integer buildType) {
		this.buildType = buildType;
	}
}
