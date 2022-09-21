package com.yskc.ms.models.projectApproval;

import com.yskc.ms.models.params.PageParams;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.projectApproval
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-25 09:29
 * @Description: 查询项目成员
 */
public class QueryProjectMemberModel extends PageParams {

    private Integer projectId;
    private Integer year;
    private String ename;
    private String enumber;
    private String deptName;
    private String specialities;
    private String role;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
