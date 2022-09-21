package com.yskc.ms.entity.rs.models.role;

import java.io.Serializable;
import java.util.List;

public class CompanyRoleModel implements Serializable {
    private Integer companyId;
    private Integer[] roleIds;
    private List<Integer> pageRoleIds;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }

    public List<Integer> getPageRoleIds() {
        return pageRoleIds;
    }

    public void setPageRoleIds(List<Integer> pageRoleIds) {
        this.pageRoleIds = pageRoleIds;
    }
}
