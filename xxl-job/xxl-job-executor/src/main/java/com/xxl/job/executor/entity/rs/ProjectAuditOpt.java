package com.xxl.job.executor.entity.rs;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @program: xxl-job
 * @description: projectAuditOpt
 * @author: cyj
 * @create: 2022-05-17 16:50
 **/
@TableName("p_audit_opt")
public class ProjectAuditOpt extends BaseEntity{

    private Integer companyId;
    private Integer projectId;
    private Integer year;
    private String ysTech;
    private String ysFina;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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

    public String getYsTech() {
        return ysTech;
    }

    public void setYsTech(String ysTech) {
        this.ysTech = ysTech;
    }

    public String getYsFina() {
        return ysFina;
    }

    public void setYsFina(String ysFina) {
        this.ysFina = ysFina;
    }

    public ProjectAuditOpt() {
    }

    public ProjectAuditOpt(Integer companyId, Integer year) {
        this.companyId = companyId;
        this.year = year;
    }

    public void setYsTechOrYsFina(Integer mType, String realName) {
        if (mType==1){
            this.ysTech = realName;
        }else if (mType==2){
            this.ysFina = realName;
        }
    }

}
