package com.yskc.rs.models.project;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/5/26 9:40
 * description:
 */
public class MaterialModel {

    private Integer id;
    /**
     * 公司名称
     */
    private String companyName;

    private Integer projectId;

    private Integer year;

    private String month;

    private Integer rdType;

    private String rdTypeName;
    /**
     * 领用日期
     */
    private Date acqDate;
    /**
     * 材料编码
     */
    private String mcode;
    /**
     * 材料名称
     */
    private String mname;
    /**
     * 使用数量
     */
    private BigDecimal used;

    /**
     *单位
     */
    private String unit;
    /**
     * 部门
     */
    private Integer deptId ;
    /**
     * 出库单号
     */
    private String billNo;

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getRdTypeName() {
        return rdTypeName;
    }

    public void setRdTypeName(String rdTypeName) {
        this.rdTypeName = rdTypeName;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }


    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
