package com.yskc.ms.models.rs;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目列表
 */
public class RsProjectListModel implements Serializable {
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
    private Integer formula;

    private String result;
    private Integer targets;

    private Integer beginYear;
    private Integer endYear;

    private Boolean trialProd;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tBeginDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tEndDate;

    private String deptName;

    private String workshop;//车间

    private String productLine;//产线

    private String processSection;//工艺段
    //是否试制
    private String haveTrialProd;


    private Integer parentId;

    private Boolean hasChild;

    private Integer seq;

    private String rdTitle;

    private Integer fileNumber;//总文件数

    private Integer currentFileNum;//当前年过程文件数

    private Integer auditDocNum;//可审核文档数

    private Integer submitNum;//提交数

    private Integer passNum;//通过数

    private Integer totalDocNum;//过程文档总数

    public Integer getTotalDocNum() {
        return totalDocNum;
    }

    public void setTotalDocNum(Integer totalDocNum) {
        this.totalDocNum = totalDocNum;
    }

    public Integer getSubmitNum() {
        return submitNum;
    }

    public void setSubmitNum(Integer submitNum) {
        this.submitNum = submitNum;
    }

    public Integer getPassNum() {
        return passNum;
    }

    public void setPassNum(Integer passNum) {
        this.passNum = passNum;
    }

    public Integer getAuditDocNum() {
        return auditDocNum;
    }

    public void setAuditDocNum(Integer auditDocNum) {
        this.auditDocNum = auditDocNum;
    }

    public RsProjectListModel() {
    }

    public Integer getCurrentFileNum() {
        return currentFileNum;
    }

    public void setCurrentFileNum(Integer currentFileNum) {
        this.currentFileNum = currentFileNum;
    }

    public Integer getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(Integer fileNumber) {
        this.fileNumber = fileNumber;
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

}
