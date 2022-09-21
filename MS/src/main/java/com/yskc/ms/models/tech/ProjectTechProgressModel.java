package com.yskc.ms.models.tech;

import com.yskc.ms.models.project.ProjectMembersModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/29 15:03
 * description:
 */
public class ProjectTechProgressModel implements Serializable {

    private Integer id;//项目id

    private Integer deptId;

    private String deptName;//所属部门

    private String companyAddress;//地址

    private String companyName;//客户名称



    private String productName;//项目名称


    private Integer status;//状态

    private String stageKey;//阶段


    private BigDecimal budget;//预算

    private Date deadline;//截至时间

    private Date receivedDate;//接收时间

    private String process;//进度详情

    private String feedback;//问题反馈

    private String labelKey;//标签

    private Date completedDate;//完成时间

    private Date lastUpdateTime;//最后更新时间

    private Integer productId;

    private String realName;

    private  Integer owerId;


    private List<ProjectTechLogModel> logList;//日志列表

    private Boolean hasStage;

    private List<ProjectMembersModel> techs;//技术人员列表

    private Date projectCreateTime;//项目接收时间

    private Integer year;//项目年份

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getProjectCreateTime() {
        return projectCreateTime;
    }

    public void setProjectCreateTime(Date projectCreateTime) {
        this.projectCreateTime = projectCreateTime;
    }

    public List<ProjectMembersModel> getTechs() {
        return techs;
    }

    public void setTechs(List<ProjectMembersModel> techs) {
        this.techs = techs;
    }

    public Boolean getHasStage() {
        return hasStage;
    }

    public void setHasStage(Boolean hasStage) {
        this.hasStage = hasStage;
    }

    public List<ProjectTechLogModel> getLogList() {
        return logList;
    }

    public void setLogList(List<ProjectTechLogModel> logList) {
        this.logList = logList;
    }

    public Integer getOwerId() {
        return owerId;
    }

    public void setOwerId(Integer owerId) {
        this.owerId = owerId;
    }

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
