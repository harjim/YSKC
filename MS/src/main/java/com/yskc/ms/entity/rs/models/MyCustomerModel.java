package com.yskc.ms.entity.rs.models;

import com.yskc.ms.models.project.ProjectInfoModel;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-16 09:19
 * @Description: 我的客户model
 */
public class MyCustomerModel implements Serializable {

    private Integer id;
    private Integer companyId;
    private String companyName;
    private String addressCode;
    private String linkMan;
    private String linkTel;
    private Boolean hasRole = false;
    private String deptName;
    private String companyLevel;
    private String tecUsers;
    private String finUsers;

    private List<ProjectInfoModel> projectList;

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public Boolean getHasRole() {
        return hasRole;
    }

    public void setHasRole(Boolean hasRole) {
        this.hasRole = hasRole;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getTecUsers() {
        return tecUsers;
    }

    public void setTecUsers(String tecUsers) {
        this.tecUsers = tecUsers;
    }

    public String getFinUsers() {
        return finUsers;
    }

    public void setFinUsers(String finUsers) {
        this.finUsers = finUsers;
    }

    public List<ProjectInfoModel> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<ProjectInfoModel> projectList) {
        this.projectList = projectList;
    }

    public String getCompanyLevel() {
        return companyLevel;
    }

    public void setCompanyLevel(String companyLevel) {
        this.companyLevel = companyLevel;
    }
}
