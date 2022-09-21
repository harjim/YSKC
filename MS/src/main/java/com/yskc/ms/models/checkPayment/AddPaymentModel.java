package com.yskc.ms.models.checkPayment;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-06 15:08
 **/
public class AddPaymentModel {
    private Integer id;
    private Integer customerId;
    private Integer deptId;
    private Integer ownerId;
    private Integer techId;
    private Integer year;
    private Integer finaManagerId;
    private Integer finaDirectorId;
    private Integer ownerMangerId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkDate;
    private Integer checkInstId;
    private String remark;
    private BigDecimal unitPrice;
    private String checkUsername;
    private String checkPassword;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    private List<PaymentProjectModel> list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getTechId() {
        return techId;
    }

    public void setTechId(Integer techId) {
        this.techId = techId;
    }

    public Integer getFinaManagerId() {
        return finaManagerId;
    }

    public void setFinaManagerId(Integer finaManagerId) {
        this.finaManagerId = finaManagerId;
    }

    public Integer getFinaDirectorId() {
        return finaDirectorId;
    }

    public void setFinaDirectorId(Integer finaDirectorId) {
        this.finaDirectorId = finaDirectorId;
    }

    public Integer getOwnerMangerId() {
        return ownerMangerId;
    }

    public void setOwnerMangerId(Integer ownerMangerId) {
        this.ownerMangerId = ownerMangerId;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckInstId() {
        return checkInstId;
    }

    public void setCheckInstId(Integer checkInstId) {
        this.checkInstId = checkInstId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getCheckUsername() {
        return checkUsername;
    }

    public void setCheckUsername(String checkUsername) {
        this.checkUsername = checkUsername;
    }

    public String getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(String checkPassword) {
        this.checkPassword = checkPassword;
    }

    public List<PaymentProjectModel> getList() {
        return list;
    }

    public void setList(List<PaymentProjectModel> list) {
        this.list = list;
    }
}
