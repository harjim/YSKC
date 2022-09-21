package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

public class ProjectExcel implements Serializable {
    private int id;
    @Excel(name = "RD", order = 0, fieldName = "rdStr")
    private String rdStr;
    /// 项目名称
    @Excel(name = "项目名称", order = 1, fieldName = "pname")
    private String pname;
    /// 部门
    @Excel(name = "部门", order = 2, fieldName = "deptName")
    private String deptName;
    /// 车间
    @Excel(name = "车间", order = 3, fieldName = "workshop")
    private String workshop;
    /// 产线
    @Excel(name = "产线", order = 4, fieldName = "productLine")
    private String productLine;
    /// 工艺段
    @Excel(name = "工艺段", order = 5, fieldName = "processSection")
    private String processSection;
    /// 研发部门
    @Excel(name = "研发部门", order = 6, fieldName = "rdDeptName")
    private String rdDeptName;
    /// 负责人
    @Excel(name = "负责人", order = 7, fieldName = "ename")
    private String ename;
    /// 预算
    @Excel(name = "预算(万元)", order = 8, fieldName = "estimateExpense")
    private Integer estimateExpense;
    /// 开始日期
    @Excel(name = "项目开始日期", order = 9, fieldName = "beginDate", dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    /// 结束日期
    @Excel(name = "项目结束日期", order = 10, fieldName = "endDate", dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    @Excel(name = "是否试制", order = 11, fieldName = "trialProd")
    private String trialProd;
    @Excel(name = "开始试制日期", order = 12, fieldName = "tBeginDate", dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tBeginDate;
    @Excel(name = "结束试制日期", order = 13, fieldName = "tEndDate", dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tEndDate;
    @Excel(name = "内部编号", order = 7, fieldName = "rdNumber")
    private String rdNumber;

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public String getTrialProd() {
        return trialProd;
    }

    public void setTrialProd(String trialProd) {
        this.trialProd = trialProd;
    }

    public Date gettBeginDate() {
        return tBeginDate;
    }

    public void settBeginDate(Date tBeginDate) {
        this.tBeginDate = tBeginDate;
    }

    public Date gettEndDate() {
        return tEndDate;
    }

    public void settEndDate(Date tEndDate) {
        this.tEndDate = tEndDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getRdStr() {
        return rdStr;
    }

    public void setRdStr(String rdStr) {
        this.rdStr = rdStr;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getEstimateExpense() {
        return estimateExpense;
    }

    public void setEstimateExpense(Integer estimateExpense) {
        this.estimateExpense = estimateExpense;
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
}
