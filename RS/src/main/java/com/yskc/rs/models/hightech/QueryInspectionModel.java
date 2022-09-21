package com.yskc.rs.models.hightech;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * @program: rs
 * @description: 查询高新进度其他费用
 * @author: cyj
 * @create: 2022-05-21 10:56
 **/
public class QueryInspectionModel  extends PageParams {
    private Integer projectId;
    private Integer companyId;
    private Date month;
    private String[] types;

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
