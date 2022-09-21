package com.yskc.rs.models.employee;

import java.io.Serializable;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employee
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-19 14:00
 * @Description: 人员下拉表格
 */
public class EmployeeSelectTableModel implements Serializable {

    private String enumber;

    private String ename;

    private String title;

    private String deptName;

    private String position;

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
