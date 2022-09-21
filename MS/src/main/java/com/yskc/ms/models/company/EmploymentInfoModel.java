package com.yskc.ms.models.company;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/12/7 9:42
 * description:从业人员信息model
 */
public class EmploymentInfoModel implements Serializable {
    private Integer id;
    private Integer year;
    private String corporationName;//法人
    private Integer corporationDegree;//法人学历
    private String corporationPhone;//法人联系电话
    private String corporationIdNum;//法人身份证号码
    private String linkName;//联系人
    private Integer linkDegree;//联系人学历
    private String linkPhone;//联系人电话
    private String linkIdNum;//联系人身份证号码
    private Integer employees;//入职人数
    private Integer females;//女职工人数
    private Integer returnees;//留学归国人员数
    private Integer insurances;//社保人员数
    private Integer foreignExperts;//外籍专家数
    private Integer freshGraduates;//应届毕业生
    private Integer administrations;//行政人员数
    private Integer marketings;//市场营销人员数
    private Integer rdDesigners;//研发设计人数
    private Integer processes;//加工制造人数
    private Integer otherWorkers;//其他工作人数
    private Integer doctors;//博士人数
    private Integer masters;//硕士人数
    private Integer undergraduates;//本科人数
    private Integer juniorColleges;//专科人数
    private Integer otherDegrees;//其他学历人数
    private Integer highTitles;//高级职称人数
    private Integer middleTitles;//中级职称人数
    private Integer primaryTitles;//初级职称人数
    private Integer otherTitles;//其他职称人数
    private Integer companyId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
