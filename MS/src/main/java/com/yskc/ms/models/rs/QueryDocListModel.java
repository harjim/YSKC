package com.yskc.ms.models.rs;

import com.yskc.ms.models.params.PageParams;

/**
 * @Author: hck
 * @DateTime: 2021/3/2 8:31
 * @Description:
 */
public class QueryDocListModel extends PageParams {

    private Integer companyId;

    private Integer year;

    private Integer projectId;

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
