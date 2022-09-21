package com.yskc.rs.models.reviewCommittee;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/9/1 17:24
 * description:评审人员
 */
public class ReviewModel implements Serializable {

    private String ename;//姓名

    private String enumber;//工号

    private Integer id;

    private Integer companyId;

    private Integer year;

    private String deptName;//部门

    private String position;//

    private Date edate;//入职日期

    private String remark;//备注

    private Date leaveDate;//离职日期

    private String idNumber;//身份证号

    private String title;//职称

    private String specialities;//专业

    private Integer eduLevel;//学历

    private String rdDeptPath;//研发部门

    private Integer seq;    //排序

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    private List<Integer> reviewIds;

    public List<Integer> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Integer> reviewIds) {
        this.reviewIds = reviewIds;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRdDeptPath() {
        return rdDeptPath;
    }

    public void setRdDeptPath(String rdDeptPath) {
        this.rdDeptPath = rdDeptPath;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
