package com.yskc.rs.models.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;

public class DeptExcel implements Serializable {
    private int id;
    @Excel(name = "部门", order = 0, fieldName = "deptName")
    private String deptName;
    @Excel(name = "备注", order = 1, fieldName = "remark")
    private String remark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
