package com.yskc.ms.models.projectRegister;

import com.yskc.ms.models.project.ProjectMembersModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/6 14:07
 * description:项目备案管理数据
 */
public class ProjectRegisterDataModel implements Serializable {

    private Integer id;//项目id

    private Integer deptId;

    private String deptName;//所属部门

    private String companyAddress;//地址

    private String companyName;//客户名称

    private BigDecimal amount;//备案金额

    private String realName;//业务员

   private  Integer owerId;

    private List<ProjectMembersModel> techs;//技术人员列表

    private String stageKey;//项目技改阶段

    private  Integer registerNum;//备案数

    private Integer year;

    private String productName;//项目类型

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private List<ProjectRegisterModel> projectRegisters;//项目备案列表

    public List<ProjectRegisterModel> getProjectRegisters() {
        return projectRegisters;
    }

    public void setProjectRegisters(List<ProjectRegisterModel> projectRegisters) {
        this.projectRegisters = projectRegisters;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public List<ProjectMembersModel> getTechs() {
        return techs;
    }

    public void setTechs(List<ProjectMembersModel> techs) {
        this.techs = techs;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }
}
