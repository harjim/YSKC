package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.models.rs.RelatedProjectModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author hck
 * @since 2021-03-18
 */
@TableName("t_beian")
public class BeianEntity {
    @TableId
    private Integer id;
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
    private BigDecimal totalAmount;
    private BigDecimal equipment;
    private BigDecimal initWorkCapital;
    private BigDecimal construction;
    private BigDecimal completeRate;
    private Boolean change;
    private Date changeLetterDate;
    private String changeContent;
    private String changeFilePath;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer sourceProjectId;
    private String remark;
    private Integer productId;
    private String applicant;

    public BeianEntity(RelatedProjectModel model, Integer msUserId, Date now) {
        this.change = false;
        this.companyId = model.getCompanyId();
        this.sourceProjectId = model.getSourceProjectId();
        this.productId = model.getProductId();
        this.creatorId = -1;
        this.lastUpdatorId = -1;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.createTime = now;
        this.lastUpdateTime = now;
        this.year = model.getPyear();
        this.remark = model.getRemark();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getEquipment() {
        return equipment;
    }

    public void setEquipment(BigDecimal equipment) {
        this.equipment = equipment;
    }

    public BigDecimal getInitWorkCapital() {
        return initWorkCapital;
    }

    public void setInitWorkCapital(BigDecimal initWorkCapital) {
        this.initWorkCapital = initWorkCapital;
    }

    public BigDecimal getConstruction() {
        return construction;
    }

    public void setConstruction(BigDecimal construction) {
        this.construction = construction;
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

    public Date getChangeLetterDate() {
        return changeLetterDate;
    }

    public void setChangeLetterDate(Date changeLetterDate) {
        this.changeLetterDate = changeLetterDate;
    }

    public String getChangeContent() {
        return changeContent;
    }

    public void setChangeContent(String changeContent) {
        this.changeContent = changeContent;
    }

    public String getChangeFilePath() {
        return changeFilePath;
    }

    public void setChangeFilePath(String changeFilePath) {
        this.changeFilePath = changeFilePath;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
}
