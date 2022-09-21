package com.yskc.rs.models.hightech;

import com.yskc.rs.entity.hightech.ProjectDeliverEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @program: rs
 * @description: 高新进度model
 * @author: wjy
 * @create: 2022-05-19 09:18
 **/
public class HighTechProgressModel  implements Serializable {
    private Integer id;

    private Date beginDate;
    private Date endDate;
    private Integer companyId;

    private String companyName;

    private Integer projectId;

    private String rdTitle;
    private String rdNumber;
    private String pname;

    private String ysTech;

    private String ysFina;

    private String ftyTech;
    private String ftyFina;
    private String areaTech;
    private String areaFina;
    //立项材料
    private ProjectDeliverEntity pApplication;
    //验收材料
    private ProjectDeliverEntity pAccept;

    private Map<Integer, ProjectDeliverEntity> techNodeMap;
    private Map<Integer, ProjectDeliverEntity> finaNodeMap;

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public Map<Integer, ProjectDeliverEntity> getTechNodeMap() {
        return techNodeMap;
    }

    public void setTechNodeMap(Map<Integer, ProjectDeliverEntity> techNodeMap) {
        this.techNodeMap = techNodeMap;
    }

    public Map<Integer, ProjectDeliverEntity> getFinaNodeMap() {
        return finaNodeMap;
    }

    public void setFinaNodeMap(Map<Integer, ProjectDeliverEntity> finaNodeMap) {
        this.finaNodeMap = finaNodeMap;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getYsTech() {
        return ysTech;
    }

    public void setYsTech(String ysTech) {
        this.ysTech = ysTech;
    }

    public String getYsFina() {
        return ysFina;
    }

    public void setYsFina(String ysFina) {
        this.ysFina = ysFina;
    }

    public String getFtyTech() {
        return ftyTech;
    }

    public void setFtyTech(String ftyTech) {
        this.ftyTech = ftyTech;
    }

    public String getFtyFina() {
        return ftyFina;
    }

    public void setFtyFina(String ftyFina) {
        this.ftyFina = ftyFina;
    }

    public String getAreaTech() {
        return areaTech;
    }

    public void setAreaTech(String areaTech) {
        this.areaTech = areaTech;
    }

    public String getAreaFina() {
        return areaFina;
    }

    public void setAreaFina(String areaFina) {
        this.areaFina = areaFina;
    }

    public ProjectDeliverEntity getpApplication() {
        return pApplication;
    }

    public void setpApplication(ProjectDeliverEntity pApplication) {
        this.pApplication = pApplication;
    }

    public ProjectDeliverEntity getpAccept() {
        return pAccept;
    }

    public void setpAccept(ProjectDeliverEntity pAccept) {
        this.pAccept = pAccept;
    }
}
