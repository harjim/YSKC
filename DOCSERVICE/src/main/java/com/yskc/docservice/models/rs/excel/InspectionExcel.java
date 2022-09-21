package com.yskc.docservice.models.rs.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;
import com.yskc.docservice.enums.CostEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.excel
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-16 10:53
 * @Description: 检测修理excel
 */
public class InspectionExcel implements Serializable {

    @Excel(name = "凭证号", order = 0, fieldName = "accNumber")
    private String accNumber;
    @Excel(name = "摘要", order = 1, fieldName = "summary")
    private String summary;
    @Excel(name = "费用", order = 2, fieldName = "expense")
    private BigDecimal expense;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "记账日期", order = 3,  fieldName = "accDate", dateFormat = "yyyy-MM-dd")
    private Date accDate;
    @Excel(name = "费用类型", order = 4, fieldName = "typeName")
    private String typeName;
    @Excel(name = "所属部门", order = 5, fieldName = "deptName")
    private String deptName;
    @Excel(name = "研发部门", order = 6, fieldName = "rdDeptName")
    private String rdDeptName;
    @Excel(name = "备注", order = 9, fieldName = "remark")
    private String remark;
    @Excel(name = "工号", order = 7, fieldName = "ename")
    private String ename;
    @Excel(name = "科目", order = 8, fieldName = "fullAccountName")
    private String fullAccountName;
    private Integer type;

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getAccDate() {
        return accDate;
    }

    public void setAccDate(Date accDate) {
        this.accDate = accDate;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTypeName() {
        return type == null ? typeName : CostEnum.getCostEnum(type).getTitle();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}
