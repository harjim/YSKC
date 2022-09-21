package com.yskc.ms.models.projectAudit;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @program: ms
 * @description: 财务审批数据查询
 * @author: cyj
 * @create: 2022-04-22 14:47
 **/
public class QueryFinaAuditModel extends PageParams  implements Serializable {
    private String companyName;
    private Integer deptId;
    private Integer year;
    private Integer[] tIds;
    private Integer[] fIds;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer[] gettIds() {
        return tIds;
    }

    public void settIds(Integer[] tIds) {
        this.tIds = tIds;
    }

    public Integer[] getfIds() {
        return fIds;
    }

    public void setfIds(Integer[] fIds) {
        this.fIds = fIds;
    }
}
