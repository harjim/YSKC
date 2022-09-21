package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 奖金导入模板
 *
 * @author huronghua
 */
public class BonusExcel implements Serializable {
    private int id;
    @Excel(name = "员工编号")
    private String enumber;
    @Excel(name = "姓名")
    private String ename;
    @Excel(name = "金额")
    private BigDecimal bonus;
    @Excel(name = "开始日期", dateFormat = "yyyy-MM-dd")
    private Date startTime;
    @Excel(name = "结束日期", dateFormat = "yyyy-MM-dd")
    private Date endTime;
    @Excel(name = "备注")
    private String remark;
    @Excel(name = "科目")
    private String fullAccountName;
    private String deptName;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date month;
    private int type;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public BigDecimal getBonus() {
        return bonus;
    }

    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
