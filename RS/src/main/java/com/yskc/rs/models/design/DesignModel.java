package com.yskc.rs.models.design;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.entity.DesignEntity;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 设计费用
 */
public class DesignModel implements Serializable {
    private Integer id;
    private String dname;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date designDate;
    private BigDecimal dFeeName;

    private Integer companyId;
    @Size(max = 200, message = "备注不能超过200个字")
    private String remark;

    private String deptName;

    private BigDecimal designCount;

    private Integer accountTitleId;
    private String fullAccountName;

    private BigDecimal remainDFee;

    public BigDecimal getRemainDFee() {
        return remainDFee;
    }

    public void setRemainDFee(BigDecimal remainDFee) {
        this.remainDFee = remainDFee;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

    public BigDecimal getDesignCount() {
        return designCount;
    }

    public void setDesignCount(BigDecimal designCount) {
        this.designCount = designCount;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    private List<DesignEntity> designEntities;

    public List<DesignEntity> getDesignEntities() {
        return designEntities;
    }

    public void setDesignEntities(List<DesignEntity> designEntities) {
        this.designEntities = designEntities;
    }

    public BigDecimal getdFee() {
        return dFee;
    }

    public void setdFee(BigDecimal dFee) {
        this.dFee = dFee;
    }

    private BigDecimal dFee;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDname() {
        return dname;
    }

    public void setDesignDate(Date designDate) {
        this.designDate = designDate;
    }

    public Date getDesignDate() {
        return designDate;
    }

    public BigDecimal getdFeeName() {
        return dFeeName;
    }

    public void setdFeeName(BigDecimal dFeeName) {
        this.dFeeName = dFeeName;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

}
