package com.yskc.ms.models.projectAudit;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description: 据项目月份获取各项数据 查询model
 * @author: cyj
 * @create: 2022-04-27 10:26
 **/
public class QueryRdFeeModel  extends PageParams implements Serializable {
        private Integer companyId;
        private Date month;
        private Integer projectId;
        private List<Integer> rdTypes;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public List<Integer> getRdTypes() {
        return rdTypes;
    }

    public void setRdTypes(List<Integer> rdTypes) {
        this.rdTypes = rdTypes;
    }
}
