package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * Created by hck
 * on 2020/12/4 13:40
 * description:从业人员情况
 */
@TableName("c_employment_info")
public class EmploymentInfoEntity {

    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer year;
    private String corporationName;
    private Integer corporationDegree;
    private String corporationPhone;
    private String corporationIdNum;
    private String linkName;
    private Integer linkDegree;
    private String linkPhone;
    private String linkIdNum;
    private Integer employees;
    private Integer females;
    private Integer returnees;
    private Integer insurances;
    private Integer foreignExperts;
    private Integer freshGraduates;
    private Integer administrations;
    private Integer marketings;
    private Integer rdDesigners;
    private Integer processes;
    private Integer otherWorkers;
    private Integer doctors;
    private Integer masters;
    private Integer undergraduates;
    private Integer juniorColleges;
    private Integer otherDegrees;
    private Integer highTitles;
    private Integer middleTitles;
    private Integer primaryTitles;
    private Integer otherTitles;
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public Integer getCorporationDegree() {
        return corporationDegree;
    }

    public void setCorporationDegree(Integer corporationDegree) {
        this.corporationDegree = corporationDegree;
    }

    public String getCorporationPhone() {
        return corporationPhone;
    }

    public void setCorporationPhone(String corporationPhone) {
        this.corporationPhone = corporationPhone;
    }

    public String getCorporationIdNum() {
        return corporationIdNum;
    }

    public void setCorporationIdNum(String corporationIdNum) {
        this.corporationIdNum = corporationIdNum;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public Integer getLinkDegree() {
        return linkDegree;
    }

    public void setLinkDegree(Integer linkDegree) {
        this.linkDegree = linkDegree;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public String getLinkIdNum() {
        return linkIdNum;
    }

    public void setLinkIdNum(String linkIdNum) {
        this.linkIdNum = linkIdNum;
    }

    public Integer getEmployees() {
        return employees;
    }

    public void setEmployees(Integer employees) {
        this.employees = employees;
    }

    public Integer getFemales() {
        return females;
    }

    public void setFemales(Integer females) {
        this.females = females;
    }

    public Integer getReturnees() {
        return returnees;
    }

    public void setReturnees(Integer returnees) {
        this.returnees = returnees;
    }

    public Integer getInsurances() {
        return insurances;
    }

    public void setInsurances(Integer insurances) {
        this.insurances = insurances;
    }

    public Integer getForeignExperts() {
        return foreignExperts;
    }

    public void setForeignExperts(Integer foreignExperts) {
        this.foreignExperts = foreignExperts;
    }

    public Integer getFreshGraduates() {
        return freshGraduates;
    }

    public void setFreshGraduates(Integer freshGraduates) {
        this.freshGraduates = freshGraduates;
    }

    public Integer getAdministrations() {
        return administrations;
    }

    public void setAdministrations(Integer administrations) {
        this.administrations = administrations;
    }

    public Integer getMarketings() {
        return marketings;
    }

    public void setMarketings(Integer marketings) {
        this.marketings = marketings;
    }

    public Integer getRdDesigners() {
        return rdDesigners;
    }

    public void setRdDesigners(Integer rdDesigners) {
        this.rdDesigners = rdDesigners;
    }

    public Integer getProcesses() {
        return processes;
    }

    public void setProcesses(Integer processes) {
        this.processes = processes;
    }

    public Integer getOtherWorkers() {
        return otherWorkers;
    }

    public void setOtherWorkers(Integer otherWorkers) {
        this.otherWorkers = otherWorkers;
    }

    public Integer getDoctors() {
        return doctors;
    }

    public void setDoctors(Integer doctors) {
        this.doctors = doctors;
    }

    public Integer getMasters() {
        return masters;
    }

    public void setMasters(Integer masters) {
        this.masters = masters;
    }

    public Integer getUndergraduates() {
        return undergraduates;
    }

    public void setUndergraduates(Integer undergraduates) {
        this.undergraduates = undergraduates;
    }

    public Integer getJuniorColleges() {
        return juniorColleges;
    }

    public void setJuniorColleges(Integer juniorColleges) {
        this.juniorColleges = juniorColleges;
    }

    public Integer getOtherDegrees() {
        return otherDegrees;
    }

    public void setOtherDegrees(Integer otherDegrees) {
        this.otherDegrees = otherDegrees;
    }

    public Integer getHighTitles() {
        return highTitles;
    }

    public void setHighTitles(Integer highTitles) {
        this.highTitles = highTitles;
    }

    public Integer getMiddleTitles() {
        return middleTitles;
    }

    public void setMiddleTitles(Integer middleTitles) {
        this.middleTitles = middleTitles;
    }

    public Integer getPrimaryTitles() {
        return primaryTitles;
    }

    public void setPrimaryTitles(Integer primaryTitles) {
        this.primaryTitles = primaryTitles;
    }

    public Integer getOtherTitles() {
        return otherTitles;
    }

    public void setOtherTitles(Integer otherTitles) {
        this.otherTitles = otherTitles;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
