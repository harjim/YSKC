package com.yskc.rs.models.init.member;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.init.member
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-04 09:05
 * @Description: 查询项目人员model
 */
public class
QueryInitMemberModel extends PageParams implements Serializable {

    private Integer projectId;

    private String ename;

    private Integer etype;

    private String title;

    private String enumber;

    private String position;

    private Integer year;
    private String rdDeptPath;
    private String specialities;
    private String role;
    private String deptName;
    private List<Integer> existIds;//要排除的项目成员id

    private String remark;

    private Integer pDocFileId;

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
    }

    public List<Integer> getExistIds() {
        return existIds;
    }

    public void setExistIds(List<Integer> existIds) {
        this.existIds = existIds;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
