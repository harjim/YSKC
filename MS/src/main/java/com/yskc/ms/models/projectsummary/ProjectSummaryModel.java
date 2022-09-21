package com.yskc.ms.models.projectsummary;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/11/10 14:06
 * description:高新数据model
 */
public class ProjectSummaryModel implements Serializable {

    private Integer projectId;

    private Integer year;

    private String productName;

    private String companyName;

    private Integer customerId;

    private Integer companyId;

    private String deptName;//所属部门

    private String owerName;//业务员

    private String techRealName;//技术人员

    private String financeRealName;//财务人员
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间



    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    private List<SummaryDataModel> summaryData;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getOwerName() {
        return owerName;
    }

    public void setOwerName(String owerName) {
        this.owerName = owerName;
    }

    public String getTechRealName() {
        return techRealName;
    }

    public void setTechRealName(String techRealName) {
        this.techRealName = techRealName;
    }

    public String getFinanceRealName() {
        return financeRealName;
    }

    public void setFinanceRealName(String financeRealName) {
        this.financeRealName = financeRealName;
    }

    public List<SummaryDataModel> getSummaryData() {
        return summaryData;
    }

    public void setSummaryData(List<SummaryDataModel> summaryData) {
        this.summaryData = summaryData;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
