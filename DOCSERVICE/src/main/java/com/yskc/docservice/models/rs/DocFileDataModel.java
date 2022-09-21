package com.yskc.docservice.models.rs;


import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.docFile
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-26 10:16
 * @Description: 项目文档数据mdoel
 */
public class DocFileDataModel implements Serializable {

    private Integer id;

    private String data;

    private String defaultTitle;

    private Integer docFileTemplateId;

    private String pname;

    private String templateName;

    private Integer beginYear;

    private Integer rdIndex;

    private Integer wordLength;

    private Integer pDocFileId;

    private String docFileName;

    private Integer docFileId;//模板id

    private Integer projectId;

    private Date month;

    private Date docDate;//文档日期

    private Map<String,Object> commonMap;

    private String formula; //开展形式

    private String tecIndustry;

    private Boolean finished;

    public Map<String, Object> getCommonMap() {
        return commonMap;
    }

    public void setCommonMap(Map<String, Object> commonMap) {
        this.commonMap = commonMap;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    private Map<String, EmployeeSelectModel> employeeMap;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Map<String, EmployeeSelectModel> getEmployeeMap() {
        return employeeMap;
    }

    public void setEmployeeMap(Map<String, EmployeeSelectModel> employeeMap) {
        this.employeeMap = employeeMap;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
    }

    public Integer getWordLength() {
        return wordLength;
    }

    public void setWordLength(Integer wordLength) {
        this.wordLength = wordLength;
    }

    public Integer getBeginYear() {
        return beginYear;
    }

    public void setBeginYear(Integer beginYear) {
        this.beginYear = beginYear;
    }

    public Integer getRdIndex() {
        return rdIndex;
    }

    public void setRdIndex(Integer rdIndex) {
        this.rdIndex = rdIndex;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getDocFileTemplateId() {
        return docFileTemplateId;
    }

    public void setDocFileTemplateId(Integer docFileTemplateId) {
        this.docFileTemplateId = docFileTemplateId;
    }

    public String getDefaultTitle() {
        return defaultTitle;
    }

    public void setDefaultTitle(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getTecIndustry() {
        return tecIndustry;
    }

    public void setTecIndustry(String tecIndustry) {
        this.tecIndustry = tecIndustry;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
