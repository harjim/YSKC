package com.yskc.rs.models.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-16 09:46
 * @Description: 项目列表model
 */
public class ProjectListModel implements Serializable {
    /**
     *
     */
    @TableId
    private Integer id;

    private Integer companyId;
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

    private String beginAndEndDate;
    private Integer estimateExpense;

    private Integer ryear;

    private String masterENumber;

    private String ename;

    private String tecIndustry;

    private Integer rdDeptId;

    private String rdDeptName;

    private String rdNumber;
    private Integer projectSource;
    private String projectSourceModel;
    private Integer formula;
    private String formulaModel;
    private String result;
    private Integer targets;
    private String targetsModel;
    private Integer beginYear;
    private Integer endYear;

    private Boolean trialProd;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tBeginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tEndDate;

    private Integer deptId;
    private String deptName;

    private String workshop;//车间

    private String productLine;//产线

    private String processSection;//工艺段
    //是否试制
    private String haveTrialProd;

    private List<ProjectListModel> children;//子项目列表

    private Integer parentId;

    private Boolean hasChild;

    private Integer seq;

    private String rdTitle;

    private String involvedProduct;//涉及产品

    private Integer status;//审核状态

    private String suggestion;//审核意见

    private String fullname;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getProjectSourceModel() {
        return projectSourceModel;
    }

    public void setProjectSourceModel(String projectSourceModel) {
        this.projectSourceModel = projectSourceModel;
    }

    public String getFormulaModel() {
        return formulaModel;
    }

    public void setFormulaModel(String formulaModel) {
        this.formulaModel = formulaModel;
    }

    public String getTargetsModel() {
        return targetsModel;
    }

    public void setTargetsModel(String targetsModel) {
        this.targetsModel = targetsModel;
    }

    private String proName;


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getInvolvedProduct() {
        return involvedProduct;
    }

    public void setInvolvedProduct(String involvedProduct) {
        this.involvedProduct = involvedProduct;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<ProjectListModel> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectListModel> children) {
        this.children = children;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public String getHaveTrialProd() {
        return haveTrialProd;
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

    public void setHaveTrialProd(String haveTrialProd) {
        this.haveTrialProd = haveTrialProd;
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

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
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

    public Integer getFormula() {
        return formula;
    }

    public void setFormula(Integer formula) {
        this.formula = formula;
    }

    public Integer getProjectSource() {
        return projectSource;
    }

    public void setProjectSource(Integer projectSource) {
        this.projectSource = projectSource;
    }

    public String getRdNumber() {
        return rdNumber;
    }

    public void setRdNumber(String rdNumber) {
        this.rdNumber = rdNumber;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public String getBeginAndEndDate() {
        return beginAndEndDate;
    }

    public void setBeginAndEndDate(String beginAndEndDate) {
        this.beginAndEndDate = beginAndEndDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }


    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
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


    public Integer getEstimateExpense() {
        return estimateExpense;
    }

    public void setEstimateExpense(Integer estimateExpense) {
        this.estimateExpense = estimateExpense;
    }


    public Integer getRyear() {
        return ryear;
    }

    public void setRyear(Integer ryear) {
        this.ryear = ryear;
    }

    public String getMasterENumber() {
        return masterENumber;
    }

    public void setMasterENumber(String masterENumber) {
        this.masterENumber = masterENumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getTecIndustry() {
        return tecIndustry;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }
}
