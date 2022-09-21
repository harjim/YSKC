package com.yskc.rs.models.project;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Description: 项目model
 * @Param:
 * @return:
 * @Author: zhangdingfu
 * @date: 2019-07-15
 */
public class ProjectModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String pname;
    /**
     *
     */
    private Integer rdIndex;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    /**
     * 起止日期
     */
    private List<String> beginAndEndDate;
    /**
     *
     */
    private Integer companyId;
    private Integer estimateExpense;
    /**
     *
     */
    private String masterENumber;

    private String masterEName;

    /**
     *
     */
    private String tecIndustry;
    private String rdDeptName;

    private String rdNumber;

    private Integer year;
    private Integer rdDeptId;

    /**
     * 项目来源
     * /// 1.本企业自选项目；
     */
    private Integer projectSource;

    /**
     * /// 项目开展形式
     */
    private Integer formula;

    /**
     * /// 项目当年成果形式
     */
    private String result;
    /**
     * /// 项目技术经济目标
     */
    public Integer targets;
    private BigDecimal govCost;
    private Boolean trialProd;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tBeginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tEndDate;
    private Integer deptId;
    private String deptName;
    private String workshop;
    private String productLine;
    private String processSection;

    private Boolean changeDatePerm;//修改项目周期标识 true 可修改 flase 不可以

    private String involvedProduct;//涉及产品

    private Integer currentYear;//当前年

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Integer currentYear) {
        this.currentYear = currentYear;
    }

    public String getInvolvedProduct() {
        return involvedProduct;
    }

    public void setInvolvedProduct(String involvedProduct) {
        this.involvedProduct = involvedProduct;
    }

    public Boolean getChangeDatePerm() {
        return changeDatePerm;
    }

    public void setChangeDatePerm(Boolean changeDatePerm) {
        this.changeDatePerm = changeDatePerm;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
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

    public Boolean getTrialProd() {
        return trialProd;
    }

    public void setTrialProd(Boolean trialProd) {
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

    public BigDecimal getGovCost() {
        return govCost;
    }

    public void setGovCost(BigDecimal govCost) {
        this.govCost = govCost;
    }

    public Integer getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(Integer projectSource) {
        this.projectSource = projectSource;
    }

    public Integer getFormula() {
        return formula;
    }

    public void setFormula(Integer formula) {
        this.formula = formula;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getTargets() {
        return targets;
    }

    public void setTargets(Integer targets) {
        this.targets = targets;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public String getMasterEName() {
        return masterEName;
    }

    public void setMasterEName(String masterEName) {
        this.masterEName = masterEName;
    }

    public List<String> getBeginAndEndDate() {
        return beginAndEndDate;
    }

    public void setBeginAndEndDate(List<String> beginAndEndDate) {
        this.beginAndEndDate = beginAndEndDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPname() {
        return pname;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setEstimateExpense(Integer estimateExpense) {
        this.estimateExpense = estimateExpense;
    }

    public Integer getEstimateExpense() {
        return estimateExpense;
    }

    public void setMasterENumber(String masterENumber) {
        this.masterENumber = masterENumber;
    }

    public String getMasterENumber() {
        return masterENumber;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }

    public String getTecIndustry() {
        return tecIndustry;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
