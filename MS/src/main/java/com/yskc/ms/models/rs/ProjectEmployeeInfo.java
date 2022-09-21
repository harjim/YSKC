package com.yskc.ms.models.rs;

/**
 * 项目人员
 *
 * @author huronghua
 */
public class ProjectEmployeeInfo {
    private String ename;
    private String enumber;
    private String position;
    private Integer etype;
    private Integer rdDeptId;
    private String deptName;
    private String specialities;
    private String rdDeptName;//研发部门
    private String autographUrl;//签名图片

    public String getAutographUrl() {
        return autographUrl;
    }

    public void setAutographUrl(String autographUrl) {
        this.autographUrl = autographUrl;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}
