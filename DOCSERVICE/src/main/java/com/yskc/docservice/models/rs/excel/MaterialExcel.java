package com.yskc.docservice.models.rs.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.common.annotation.Excel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MaterialExcel implements Serializable {
    private int id;

    @Excel(name = "物料编码", order = 0, fieldName = "mcode")
    private String mcode;

    @Excel(name = "物料名称", order = 1, fieldName = "mname")
    private String mname;

    @Excel(name = "出库单号", order = 2, fieldName = "billNo")
    private String billNo;

    @Excel(name = "凭证号", order = 3, fieldName = "accNumber")
    private String accNumber;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "领用日期", order = 4, fieldName = "acqDate", dateFormat = "yyyy-MM-dd")
    private Date acqDate;

    @Excel(name = "单价", order = 5, fieldName = "unitPrice")
    private BigDecimal unitPrice;

    @Excel(name = "数量", order = 6, fieldName = "quantity")
    private BigDecimal quantity;
    @Excel(name = "总金额", order = 7, fieldName = "totalAmount")
    private BigDecimal totalAmount;
    @Excel(name = "单位", order = 8, fieldName = "unit")
    private String unit;
    @Excel(name = "规格型号", order = 9, fieldName = "specification")
    private String specification;
    @Excel(name = "所属部门", order = 10, fieldName = "deptName")
    private String deptName;
    @Excel(name = "研发部门", order = 11, fieldName = "rdDeptName")
    private String rdDeptName;
    @Excel(name = "仓库", order = 12, fieldName = "warehouse")
    private String warehouse;
    @Excel(name = "科目", order = 13, fieldName = "warehouse")
    private String fullAccountName;
    @Excel(name = "领料人", order = 14, fieldName = "picker")
    private String picker;
    @Excel(name = "制单人", order = 15, fieldName = "biller")
    private String biller;
    @Excel(name = "审核人", order = 16, fieldName = "auditor")
    private String auditor;
    @Excel(name = "记帐人", order = 17, fieldName = "booker")
    private String booker;
    @Excel(name = "备注", order = 18, fieldName = "remark")
    private String remark;
    @Excel(name = "用途", order = 19, fieldName = "purpose")
    private String purpose;

    private String rdTitle;

    private String rdTypeName;

    private BigDecimal used;

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

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

    public Date getAcqDate() {
        return acqDate;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getRdDeptName() {
        return rdDeptName;
    }

    public void setRdDeptName(String rdDeptName) {
        this.rdDeptName = rdDeptName;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


	public BigDecimal getUsed() {
		return used;
	}

	public void setUsed(BigDecimal used) {
		this.used = used;
	}

    public String getRdTypeName() {
        return rdTypeName;
    }

    public void setRdTypeName(String rdTypeName) {
        this.rdTypeName = rdTypeName;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }
}
