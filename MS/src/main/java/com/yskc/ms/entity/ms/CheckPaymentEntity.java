package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-06 13:33
 **/
@TableName("checkPayment")
public class CheckPaymentEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer customerId;
    private Integer deptId;
    private Integer ownerId;
    private Integer techId;
    private Integer year;
    private Integer finaManagerId;
    private Integer finaDirectorId;
    private Integer ownerMangerId;
    private Date checkDate;
    private Integer rdCnt;
    private Integer checkInstId;
    private String remark;
    private BigDecimal unitPrice;
    private BigDecimal totalAmount;
    private String checkUsername;
    private String checkPassword;
    private String payType;
    private String paymentFile;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

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

    public Integer getRdCnt() {
        return rdCnt;
    }

    public void setRdCnt(Integer rdCnt) {
        this.rdCnt = rdCnt;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPaymentFile() {
        return paymentFile;
    }

    public void setPaymentFile(String paymentFile) {
        this.paymentFile = paymentFile;
    }
}
