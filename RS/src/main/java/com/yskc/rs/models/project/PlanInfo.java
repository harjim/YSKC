package com.yskc.rs.models.project;

import com.yskc.rs.models.dept.DeptTree;

import java.util.List;

public class PlanInfo {
    /**
     * 项目数
     */
    private Integer cnt;
    /**
     * 研发费
     */
    private Integer rdFee;
    /**
     * 企业总人数
     */
    private Integer employeeAmount;
    /**
     * 营收预测
     */
    private Integer revenueFcst;

    private String deptIds;

    private List<DeptTree> deptTree;

    public List<DeptTree> getDeptTree() {
        return deptTree;
    }

    public void setDeptTree(List<DeptTree> deptTree) {
        this.deptTree = deptTree;
    }

    public String getDeptIds() {
        return deptIds;
    }

    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public Integer getRdFee() {
        return rdFee;
    }

    public void setRdFee(Integer rdFee) {
        this.rdFee = rdFee;
    }

    public Integer getEmployeeAmount() {
        return employeeAmount;
    }

    public void setEmployeeAmount(Integer employeeAmount) {
        this.employeeAmount = employeeAmount;
    }

    public Integer getRevenueFcst() {
        return revenueFcst;
    }

    public void setRevenueFcst(Integer revenueFcst) {
        this.revenueFcst = revenueFcst;
    }
}
