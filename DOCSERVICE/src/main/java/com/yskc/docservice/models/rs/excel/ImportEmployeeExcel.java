package com.yskc.docservice.models.rs.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/5/11 9:39
 * 研发花名册列表
 */
public class ImportEmployeeExcel implements Serializable {
    @Excel(name = "工号", order = 0, fieldName = "enumber")
    private String enumber;
    @Excel(name = "姓名", order = 1, fieldName = "ename")
    private String ename;
    @Excel(name = "人员类型", order = 2, fieldName = "typeValue")
    private String typeValue;
    @Excel(name = "研发部门", order = 3, fieldName = "rdDeptName")
    private String rdDeptName;
    @Excel(name = "职位", order = 4, fieldName = "position")
    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

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

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

}
