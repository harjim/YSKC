package com.yskc.rs.models.reviewCommittee;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/9/1 17:31
 * description:
 */
public class QueryReviewModel extends PageParams implements Serializable {

    private String ename;

    private String enumber;

    private Integer year;

    private String position;//职位

    private String title;//职称

    private String specialities;//专业

    private Integer eduLevel;//学历

    private String rdDeptPath;//研发部门

    private Integer etype;//人员类型

    private String deptName;

    private Date docDate;

    private Integer pDocFileId;

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Integer getpDocFileId() {
        return pDocFileId;
    }

    public void setpDocFileId(Integer pDocFileId) {
        this.pDocFileId = pDocFileId;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
