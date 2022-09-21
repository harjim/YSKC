package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 14:56:07
 */
@TableName("sys_insuranceConfig")
public class InsuranceConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer companyId;
	/**
	 *
	 */
	private Integer deptId;
	/**
	 *
	 */
	private String enumber;
	/**
	 *
	 */
	@JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
	private Date startMonth;
	/**
	 *
	 */
	@JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
	private Date endMonth;
	/**
	 *
	 */
	private BigDecimal endowment;
	/**
	 *
	 */
	private BigDecimal medical;
	/**
	 *
	 */
	private BigDecimal unemployment;
	/**
	 *
	 */
	private BigDecimal injury;
	/**
	 *
	 */
	private BigDecimal maternity;
	/**
	 *
	 */
	private BigDecimal house;
	/**
	 *
	 */
	private BigDecimal endowmentOfCom;
	/**
	 *
	 */
	private BigDecimal medicalOfCom;
	/**
	 *
	 */
	private BigDecimal unemploymentOfCom;
	/**
	 *
	 */
	private BigDecimal injuryOfCom;
	/**
	 *
	 */
	private BigDecimal maternityOfCom;
	/**
	 *
	 */
	private BigDecimal houseOfCom;

	private Integer lastUpdatorId;

	private Date lastUpdateTime;

	private Integer msCreatorId;
	private Integer msLastUpdatorId;
	/**
	 *
	 */
	private Integer creatorId;
	/**
	 *
	 */
	private Date createTime;


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

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setEnumber(String enumber) {
		this.enumber = enumber;
	}

	public String getEnumber() {
		return enumber;
	}

	public void setStartMonth(Date startMonth) {
		this.startMonth = startMonth;
	}

	public Date getStartMonth() {
		return startMonth;
	}

	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
	}

	public Date getEndMonth() {
		return endMonth;
	}

	public void setEndowment(BigDecimal endowment) {
		this.endowment = endowment;
	}

	public BigDecimal getEndowment() {
		return endowment;
	}

	public void setMedical(BigDecimal medical) {
		this.medical = medical;
	}

	public BigDecimal getMedical() {
		return medical;
	}

	public void setUnemployment(BigDecimal unemployment) {
		this.unemployment = unemployment;
	}

	public BigDecimal getUnemployment() {
		return unemployment;
	}

	public void setInjury(BigDecimal injury) {
		this.injury = injury;
	}

	public BigDecimal getInjury() {
		return injury;
	}

	public void setMaternity(BigDecimal maternity) {
		this.maternity = maternity;
	}

	public BigDecimal getMaternity() {
		return maternity;
	}

	public void setHouse(BigDecimal house) {
		this.house = house;
	}

	public BigDecimal getHouse() {
		return house;
	}

	public void setEndowmentOfCom(BigDecimal endowmentOfCom) {
		this.endowmentOfCom = endowmentOfCom;
	}

	public BigDecimal getEndowmentOfCom() {
		return endowmentOfCom;
	}

	public void setMedicalOfCom(BigDecimal medicalOfCom) {
		this.medicalOfCom = medicalOfCom;
	}

	public BigDecimal getMedicalOfCom() {
		return medicalOfCom;
	}

	public void setUnemploymentOfCom(BigDecimal unemploymentOfCom) {
		this.unemploymentOfCom = unemploymentOfCom;
	}

	public BigDecimal getUnemploymentOfCom() {
		return unemploymentOfCom;
	}

	public void setInjuryOfCom(BigDecimal injuryOfCom) {
		this.injuryOfCom = injuryOfCom;
	}

	public BigDecimal getInjuryOfCom() {
		return injuryOfCom;
	}

	public void setMaternityOfCom(BigDecimal maternityOfCom) {
		this.maternityOfCom = maternityOfCom;
	}

	public BigDecimal getMaternityOfCom() {
		return maternityOfCom;
	}

	public void setHouseOfCom(BigDecimal houseOfCom) {
		this.houseOfCom = houseOfCom;
	}

	public BigDecimal getHouseOfCom() {
		return houseOfCom;
	}

}
