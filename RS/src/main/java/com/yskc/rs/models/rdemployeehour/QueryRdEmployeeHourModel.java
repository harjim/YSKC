package com.yskc.rs.models.rdemployeehour;

import com.yskc.rs.models.params.PageParams;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdemployeehour
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-08 09:06
 * @Description: 查询研发投入工时
 */
public class QueryRdEmployeeHourModel extends PageParams {

    private Integer year;
    private String rdDeptPath;
    private String enumber;
    private String ename;
    private String position;
    private Integer[] etypes;
    private Integer eduLevel;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer[] getEtypes() {
        return etypes;
    }

    public void setEtypes(Integer[] etypes) {
        this.etypes = etypes;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }
}
