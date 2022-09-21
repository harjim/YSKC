package com.yskc.rs.entity.tech;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author zdf-hck123
 * @since 2021-03-18
 */
@TableName("t_beian")
public class BeianEntity extends BaseEntity {
    @TableId
    private Integer id;
    private Integer productId;
    private Integer companyId;
    private Integer year;
    private String pname;
    private String constructionPlace;
    private Integer economyType;
    private String content;
    private Date beginDate;
    private Date endDate;
    private String beianNo;
    private Date beianDate;
    private String filePath;
    private String scanFilePath;

    private BigDecimal completeRate;
    private Boolean change;

    private Integer sourceProjectId;
    private String remark;
    private String applicant;

    private String changedDates;
    private String projectNo;
    private String accountName;
    private String accountPassword;
    private BigDecimal powerUsed;
    private BigDecimal energyUsed;

    private Integer equipmentCnt;//备案资产项数(sum(t_equipment))
    private Integer equipmentQuantity;//备案资产数量(sum(t_equipment.quantity))

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getConstructionPlace() {
        return constructionPlace;
    }

    public void setConstructionPlace(String constructionPlace) {
        this.constructionPlace = constructionPlace;
    }

    public Integer getEconomyType() {
        return economyType;
    }

    public void setEconomyType(Integer economyType) {
        this.economyType = economyType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getBeianNo() {
        return beianNo;
    }

    public void setBeianNo(String beianNo) {
        this.beianNo = beianNo;
    }

    public Date getBeianDate() {
        return beianDate;
    }

    public void setBeianDate(Date beianDate) {
        this.beianDate = beianDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getScanFilePath() {
        return scanFilePath;
    }

    public void setScanFilePath(String scanFilePath) {
        this.scanFilePath = scanFilePath;
    }

    public BigDecimal getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(BigDecimal completeRate) {
        this.completeRate = completeRate;
    }

    public Boolean getChange() {
        return change;
    }

    public void setChange(Boolean change) {
        this.change = change;
    }

    public Integer getSourceProjectId() {
        return sourceProjectId;
    }

    public void setSourceProjectId(Integer sourceProjectId) {
        this.sourceProjectId = sourceProjectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getChangedDates() {
        return changedDates;
    }

    public void setChangedDates(String changedDates) {
        this.changedDates = changedDates;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public BigDecimal getPowerUsed() {
        return powerUsed;
    }

    public void setPowerUsed(BigDecimal powerUsed) {
        this.powerUsed = powerUsed;
    }

    public BigDecimal getEnergyUsed() {
        return energyUsed;
    }

    public void setEnergyUsed(BigDecimal energyUsed) {
        this.energyUsed = energyUsed;
    }

    public Integer getEquipmentCnt() {
        return equipmentCnt;
    }

    public void setEquipmentCnt(Integer equipmentCnt) {
        this.equipmentCnt = equipmentCnt;
    }

    public Integer getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(Integer equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }
}
