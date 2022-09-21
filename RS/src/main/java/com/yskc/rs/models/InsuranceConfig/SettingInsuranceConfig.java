package com.yskc.rs.models.InsuranceConfig;

import java.math.BigDecimal;
import java.util.List;

/**
 * 设置五险一金比例
 * @author huronghua
 */
public class SettingInsuranceConfig {
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
    /**
     * 薪酬ID
     */
    private List<Integer> salaryIds;

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

    public List<Integer> getSalaryIds() {
        return salaryIds;
    }

    public void setSalaryIds(List<Integer> salaryIds) {
        this.salaryIds = salaryIds;
    }
}
