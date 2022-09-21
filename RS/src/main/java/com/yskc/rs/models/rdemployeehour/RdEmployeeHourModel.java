package com.yskc.rs.models.rdemployeehour;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdemployeehour
 * @Author: zhangdingfu
 * @CreateTime: 2022-03-08 09:04
 * @Description: 研发人员投入工时
 */
public class RdEmployeeHourModel {

    private Integer id;
    private String enumber;
    private String ename;
    private Integer etype;
    private Integer eduLevel;
    private String fullName;
    private String position;
    private String rds;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date entryDate;
    private BigDecimal rdHour;
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRds() {
        return rds;
    }

    public void setRds(String rds) {
        this.rds = rds;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }
}
