package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-06 10:34
 * @Description: 考勤excel
 */
public class CustomerAttendanceExcel implements Serializable {

    @Excel(name = "工号", order = 0, fieldName = "enumber")
    private String enumber;

    @Excel(name = "姓名", order = 1, fieldName = "ename")
    private String ename;

    @Excel(name = "部门", order = 2, fieldName = "deptName")
    private String deptName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "上班日期", order = 3, fieldName = "workDate")
    private Date workDate;

    @JsonFormat(pattern = "HH:mm", timezone = "GTM+8")
    @Excel(name = "上班时间1", order = 4, fieldName = "onTime1")
    private Date onTime1;

    @JsonFormat(pattern = "HH:mm", timezone = "GTM+8")
    @Excel(name = "下班时间1", order = 5, fieldName = "offTime1")
    private Date offTime1;
    @JsonFormat(pattern = "HH:mm", timezone = "GTM+8")
    @Excel(name = "上班时间2", order = 4, fieldName = "onTime2")
    private Date onTime2;

    @JsonFormat(pattern = "HH:mm", timezone = "GTM+8")
    @Excel(name = "下班时间2", order = 5, fieldName = "offTime2")
    private Date offTime2;
    @JsonFormat(pattern = "HH:mm", timezone = "GTM+8")
    @Excel(name = "上班时间3", order = 4, fieldName = "onTime3")
    private Date onTime3;
    @JsonFormat(pattern = "HH:mm", timezone = "GTM+8")
    @Excel(name = "下班时间3", order = 5, fieldName = "offTime3")
    private Date offTime3;
    @Excel(name = "工时", order = 6, fieldName = "workHour")
    private BigDecimal workHour;

    @Excel(name = "备注", order = 6, fieldName = "remark")
    private String remark;

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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getOnTime1() {
        return onTime1;
    }

    public void setOnTime1(Date onTime1) {
        this.onTime1 = onTime1;
    }

    public Date getOffTime1() {
        return offTime1;
    }

    public void setOffTime1(Date offTime1) {
        this.offTime1 = offTime1;
    }

    public Date getOnTime2() {
        return onTime2;
    }

    public void setOnTime2(Date onTime2) {
        this.onTime2 = onTime2;
    }

    public Date getOffTime2() {
        return offTime2;
    }

    public void setOffTime2(Date offTime2) {
        this.offTime2 = offTime2;
    }

    public Date getOnTime3() {
        return onTime3;
    }

    public void setOnTime3(Date onTime3) {
        this.onTime3 = onTime3;
    }

    public Date getOffTime3() {
        return offTime3;
    }

    public void setOffTime3(Date offTime3) {
        this.offTime3 = offTime3;
    }

    public BigDecimal getWorkHour() {
        return workHour;
    }

    public void setWorkHour(BigDecimal workHour) {
        this.workHour = workHour;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
