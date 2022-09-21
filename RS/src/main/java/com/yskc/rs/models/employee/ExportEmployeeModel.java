package com.yskc.rs.models.employee;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/9/12 10:06
 * description:导出项目成员名单
 */
public class ExportEmployeeModel implements Serializable {

    private String enumber;

    private String ename;

    private String gender;

    private String deptName;

    private String RD;

    private String position;

    private Integer etype;

    private Date edate;

    private Boolean master;

    private String employeeType;

    private String intoDate;

    public String getIntoDate() {
        return intoDate;
    }

    public void setIntoDate(String intoDate) {
        this.intoDate = intoDate;
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

    public String getGender() {
        if(gender.equals("1")){
            gender="女";
        }else if(gender.equals("2")){
            gender="男";
        }else {
            gender=null;
        }
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRD() {
        return RD;
    }

    public void setRD(String RD) {
        this.RD = RD;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }
}
