package com.yskc.rs.models.project;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/6/18 14:32
 * @Description:多级研发管理查询model
 */
public class QueryAttachmentModel extends PageParams {

    private Date month;

    private String deptName;

    private String workshop;

    private String productLine;

    private String processSection;

    private String pname;

    private Integer projectId;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
