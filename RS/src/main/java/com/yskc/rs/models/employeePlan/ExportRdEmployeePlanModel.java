package com.yskc.rs.models.employeePlan;

import com.yskc.rs.enums.EmployeeTypeEnum;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employeePlan
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-27 14:46
 * @Description: 导出计划工时
 */
public class ExportRdEmployeePlanModel {

    private Integer projectId;

    private String enumber;

    private Date month;

    private BigDecimal planTime;

    private String ename;

    private String rdTitle;

    private String pname;

    private Integer etype;

    private String typeName;

    private String rdNumber;

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

    public BigDecimal getPlanTime() {
        return planTime;
    }

    public void setPlanTime(BigDecimal planTime) {
        this.planTime = planTime;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getTypeName() {
        return EmployeeTypeEnum.getEmployeeType(etype);
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }
}
