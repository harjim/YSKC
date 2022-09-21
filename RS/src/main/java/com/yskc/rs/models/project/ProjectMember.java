package com.yskc.rs.models.project;

import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 项目成员
 * @author huronghua
 */
public class ProjectMember {
    private Integer projectId;
    private String enumber;
    private String ename;
    private Date month;
    private String remainAttData;
    private Integer attendanceDataId;
    private BigDecimal pay;
    private BigDecimal endowment;
    private BigDecimal medical;
    private BigDecimal unemployment;
    private BigDecimal injury;
    private BigDecimal maternity;
    private BigDecimal house;
    private BigDecimal endowmentOfCom;
    private BigDecimal medicalOfCom;
    private BigDecimal unemploymentOfCom;
    private BigDecimal injuryOfCom;
    private BigDecimal maternityOfCom;
    private BigDecimal houseOfCom;
    private String attData;
    private Integer totalHours;
    private Integer remainHours;
    private Integer companyId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public Integer getTotalHours() {
        return  getHours(attData);
    }

    public void setTotalHours(Integer totalHours) {
        this.totalHours = totalHours;
    }

    public Integer getRemainHours() {
        return getHours(remainAttData);
    }

    public void setRemainHours(Integer remainHours) {
        this.remainHours = remainHours;
    }
    private Integer getHours(String data){
        Integer value = 0;
        if (!StringUtils.isEmpty(data)) {
            List<String> attList = Arrays.asList(data.split(","));
            for (String item : attList) {
                value += Integer.valueOf(item);
            }
        }
        return value;
    }
    public String getAttData() {
        return attData;
    }

    public void setAttData(String attData) {
        this.attData = attData;
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

    public Integer getAttendanceDataId() {
        return attendanceDataId;
    }

    public void setAttendanceDataId(Integer attendanceDataId) {
        this.attendanceDataId = attendanceDataId;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getRemainAttData() {
        return remainAttData;
    }

    public void setRemainAttData(String remainAttData) {
        this.remainAttData = remainAttData;
    }
}
