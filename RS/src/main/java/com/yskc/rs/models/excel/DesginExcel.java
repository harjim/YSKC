package com.yskc.rs.models.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计费用
 */
public class DesginExcel implements Serializable {
    private int id;
    @Excel(name = "设计名称", order = 0, fieldName = "dname")
    private String dname;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "发生日期", order = 3, fieldName = "designDate", dateFormat = "yyyy-MM-dd")
    private Date designDate;
    @Excel(name = "设计费用", order = 2, fieldName = "dFee")
    private BigDecimal dFee;
    @Excel(name = "备注", order = 6, fieldName = "remark")
    private String remark;
    @Excel(name = "部门", order = 3, fieldName = "deptName")
    private String deptName;
    @Excel(name = "科目全名", order = 5, fieldName = "fullAccountName")
    private String fullAccountName;

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public Date getDesignDate() {
        return designDate;
    }

    public void setDesignDate(Date designDate) {
        this.designDate = designDate;
    }

    public BigDecimal getdFee() {
        return dFee;
    }

    public void setdFee(BigDecimal dFee) {
        this.dFee = dFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
