package com.yskc.rs.models.material;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yskc.rs.entity.MaterialEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据归集物料返回实体
 * 
 * @author Administrator
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectMaterialModel implements Serializable {
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
	private Integer materialDataId;
	
	private List<MaterialEntity> materialList;
	/**
	* 
	*/
	private BigDecimal used;
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
	
	private Date acqMonth;
	
	private BigDecimal countTotal;
	private BigDecimal countMaterial;
	private BigDecimal countTrial;
	
	private Integer rdType;

	private List<AppMaterialModel> modelList;

	private List<Integer> ids;

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	public List<AppMaterialModel> getModelList() {
		return modelList;
	}

	public void setModelList(List<AppMaterialModel> modelList) {
		this.modelList = modelList;
	}

	public Integer getRdType() {
		return rdType;
	}

	public void setRdType(Integer rdType) {
		this.rdType = rdType;
	}

	public BigDecimal getCountTotal() {
		return countTotal;
	}

	public void setCountTotal(BigDecimal countTotal) {
		this.countTotal = countTotal;
	}

	public BigDecimal getCountMaterial() {
		return countMaterial;
	}

	public void setCountMaterial(BigDecimal countMaterial) {
		this.countMaterial = countMaterial;
	}

	public BigDecimal getCountTrial() {
		return countTrial;
	}

	public void setCountTrial(BigDecimal countTrial) {
		this.countTrial = countTrial;
	}

	public Date getAcqMonth() {
		return acqMonth;
	}

	public void setAcqMonth(Date acqMonth) {
		this.acqMonth = acqMonth;
	}

	public List<MaterialEntity> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialEntity> materialList) {
		this.materialList = materialList;
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

	public void setMaterialDataId(Integer materialDataId) {
		this.materialDataId = materialDataId;
	}

	public Integer getMaterialDataId() {
		return materialDataId;
	}

	public void setUsed(BigDecimal used) {
		this.used = used;
	}

	public BigDecimal getUsed() {
		return used;
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

}
