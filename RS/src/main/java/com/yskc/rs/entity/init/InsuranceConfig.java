package com.yskc.rs.entity.init;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 14:56:07
 */
@TableName("insuranceConfig")
public class InsuranceConfig implements Serializable {
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
     * 养老
     */
    private BigDecimal endowment;
    /**
     * 医疗
     */
    private BigDecimal medical;
    /**
     * 失业
     */
    private BigDecimal unemployment;
    /**
     * 工伤
     */
    private BigDecimal injury;
    /**
     * 生育
     */
    private BigDecimal maternity;
    /**
     * 公积金
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

    private Integer creatorId;

    private Date createTime;

    private Integer updatorId;

    private Date lastUpdateTime;

    public BigDecimal getEndowmentOfCom() {
        return endowmentOfCom;
    }

    public void setEndowmentOfCom(BigDecimal endowmentOfCom) {
        this.endowmentOfCom = endowmentOfCom;
    }

    public BigDecimal getMedicalOfCom() {
        return medicalOfCom;
    }

    public void setMedicalOfCom(BigDecimal medicalOfCom) {
        this.medicalOfCom = medicalOfCom;
    }

    public BigDecimal getUnemploymentOfCom() {
        return unemploymentOfCom;
    }

    public void setUnemploymentOfCom(BigDecimal unemploymentOfCom) {
        this.unemploymentOfCom = unemploymentOfCom;
    }

    public BigDecimal getInjuryOfCom() {
        return injuryOfCom;
    }

    public void setInjuryOfCom(BigDecimal injuryOfCom) {
        this.injuryOfCom = injuryOfCom;
    }

    public BigDecimal getMaternityOfCom() {
        return maternityOfCom;
    }

    public void setMaternityOfCom(BigDecimal maternityOfCom) {
        this.maternityOfCom = maternityOfCom;
    }

    public BigDecimal getHouseOfCom() {
        return houseOfCom;
    }

    public void setHouseOfCom(BigDecimal houseOfCom) {
        this.houseOfCom = houseOfCom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getEndowment() {
        return endowment;
    }

    public void setEndowment(BigDecimal endowment) {
        this.endowment = endowment;
    }

    public BigDecimal getMedical() {
        return medical;
    }

    public void setMedical(BigDecimal medical) {
        this.medical = medical;
    }

    public BigDecimal getUnemployment() {
        return unemployment;
    }

    public void setUnemployment(BigDecimal unemployment) {
        this.unemployment = unemployment;
    }

    public BigDecimal getInjury() {
        return injury;
    }

    public void setInjury(BigDecimal injury) {
        this.injury = injury;
    }

    public BigDecimal getMaternity() {
        return maternity;
    }

    public void setMaternity(BigDecimal maternity) {
        this.maternity = maternity;
    }

    public BigDecimal getHouse() {
        return house;
    }

    public void setHouse(BigDecimal house) {
        this.house = house;
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

    public Integer getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(Integer updatorId) {
        this.updatorId = updatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
