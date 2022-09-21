package com.yskc.ms.models.projectApproval;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by hck
 * on 2020/6/16 15:34
 * description:立项列表
 */
public class ProjectApprovalModel {

    private Integer id;

    private String pname;//项目名称


    private String companyName;//公司名称

    private String rdTitle;//项目编号

    private Integer year;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    private Date createTime;//创建时间

    private Integer memberCnt;

    private Integer equipmentCnt;


    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(Integer memberCnt) {
        this.memberCnt = memberCnt;
    }

    public Integer getEquipmentCnt() {
        return equipmentCnt;
    }

    public void setEquipmentCnt(Integer equipmentCnt) {
        this.equipmentCnt = equipmentCnt;
    }
}
