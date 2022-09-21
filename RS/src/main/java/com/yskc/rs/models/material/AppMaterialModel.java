package com.yskc.rs.models.material;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.entity.MaterialEntity;
import com.yskc.rs.models.params.PageParams;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据录入物料返回实体
 *
 * @author Administrator
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppMaterialModel extends PageParams {

    private Integer id;

    private Integer materialDataId;

    private String mcode;

    private String mname;
    private String accNumber;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date acqDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal unitPrice;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal remainQuantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal maxQuantity;

    /**
     * 原始数量
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal originalQuantity;
    /**
     *
     */
    private String unit;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private String remark;

    /**
     * 月份
     */
    private Date acqMonth;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date selectDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;

    /**
     * 使用量
     */
    private BigDecimal used;
    /**
     * 修改前使用量
     */
    private BigDecimal oldUsed;

    private String auditor;
    /**
     *
     */
    private String billNo;
    /**
     *
     */
    private String biller;
    /**
     *
     */
    private String booker;

    private String deptName;

    private String specification;
    /**
     *
     */
    private String warehouse;

    private List<MaterialEntity> materialList;

    private Integer projectId;

    private Integer rdType;

    private Integer type;

    private List<Integer> ids;

    //2019-09-03新增两个字段
    private String picker;
    private BigDecimal totalAmount;

    private String purpose;

    private Integer accountTitleId;
    private String fullAccountName;

    //2019/9/29新增(废品数量,成品数量)
    private BigDecimal wastage;
    private BigDecimal finished;

    private Integer lastUpdatorId;

    private Date lastUpdateTime;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    //材料跟踪表字段
    private Integer ptid;
    private Integer pId;
    private BigDecimal normalOutputRate;
    private BigDecimal rdOutputRate;
    private BigDecimal rdOutPut;
    private BigDecimal rdOutputAmount;
    private BigDecimal rdLossRate;
    private BigDecimal rdLoss;
    private BigDecimal rdLossAmount;
    private BigDecimal scrapRate;
    private BigDecimal scrap;
    private BigDecimal scrapAmount;
    private String scrapNo;
    private BigDecimal sampleRevenue;
    private BigDecimal scrapPrice;
    private BigDecimal specialIncome;
    private String specialIncomeNo;
    ///////
    private boolean editState;

    private Date startMonth;
    private Date endMonth;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal finishAmount;//成品金额
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal finishQuantity;//成品数量
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal finishUnitPrice;//成品单价
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal wasteAmount;//废品金额
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal wasteQuantity;//废品数量
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal wasteUnitPrice;//废品单价
    private BigDecimal rdAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalYield;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdYield;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal depreciationRatio;
    public BigDecimal getFinishAmount() {
        return finishAmount;
    }

    public void setFinishAmount(BigDecimal finishAmount) {
        this.finishAmount = finishAmount;
    }

    public BigDecimal getFinishQuantity() {
        return finishQuantity;
    }

    public void setFinishQuantity(BigDecimal finishQuantity) {
        this.finishQuantity = finishQuantity;
    }

    public BigDecimal getFinishUnitPrice() {
        return finishUnitPrice;
    }

    public void setFinishUnitPrice(BigDecimal finishUnitPrice) {
        this.finishUnitPrice = finishUnitPrice;
    }

    public BigDecimal getWasteAmount() {
        return wasteAmount;
    }

    public void setWasteAmount(BigDecimal wasteAmount) {
        this.wasteAmount = wasteAmount;
    }

    public BigDecimal getWasteQuantity() {
        return wasteQuantity;
    }

    public void setWasteQuantity(BigDecimal wasteQuantity) {
        this.wasteQuantity = wasteQuantity;
    }

    public BigDecimal getWasteUnitPrice() {
        return wasteUnitPrice;
    }

    public void setWasteUnitPrice(BigDecimal wasteUnitPrice) {
        this.wasteUnitPrice = wasteUnitPrice;
    }

    public Date getStartMonth() {
        if (startMonth != null) {
            return DateUtil.getMonthFirstDay(startMonth);
        }
        return startMonth;
    }

    public void setStartMonth(Date startMonth) {
        this.startMonth = startMonth;
    }

    public Date getEndMonth() {
        if (endMonth != null) {
            return DateUtil.getMonthLastDay(endMonth);
        }
        return endMonth;
    }
    public void setEndMonth(Date endMonth) {
        this.endMonth = endMonth;
    }


    public boolean isEditState() {
        return false;
    }

    public void setEditState(boolean editState) {
        this.editState = editState;
    }

    public Integer getPtid() {
        return ptid == null ? 0 : ptid;
    }

    public void setPtid(Integer ptid) {
        this.ptid = ptid;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public BigDecimal getNormalOutputRate() {
        return normalOutputRate == null ? BigDecimal.ZERO : normalOutputRate;
    }

    public void setNormalOutputRate(BigDecimal normalOutputRate) {
        this.normalOutputRate = normalOutputRate;
    }

    public BigDecimal getRdOutputRate() {
        return rdOutputRate == null ? BigDecimal.ZERO : rdOutputRate;
    }

    public void setRdOutputRate(BigDecimal rdOutputRate) {
        this.rdOutputRate = rdOutputRate;
    }

    public BigDecimal getRdOutPut() {
        return rdOutPut == null ? BigDecimal.ZERO : rdOutPut;
    }

    public void setRdOutPut(BigDecimal rdOutPut) {
        this.rdOutPut = rdOutPut;
    }

    public BigDecimal getRdOutputAmount() {
        return rdOutputAmount == null ? BigDecimal.ZERO : rdOutputRate;
    }

    public void setRdOutputAmount(BigDecimal rdOutputAmount) {
        this.rdOutputAmount = rdOutputAmount;
    }

    public BigDecimal getRdLossRate() {
        return rdLossRate == null ? BigDecimal.ZERO : rdLossRate;
    }

    public void setRdLossRate(BigDecimal rdLossRate) {
        this.rdLossRate = rdLossRate;
    }

    public BigDecimal getRdLoss() {
        return rdLoss == null ? BigDecimal.ZERO : rdLoss;
    }

    public void setRdLoss(BigDecimal rdLoss) {
        this.rdLoss = rdLoss;
    }

    public BigDecimal getRdLossAmount() {
        return rdLossAmount == null ? BigDecimal.ZERO : rdLossAmount;
    }

    public void setRdLossAmount(BigDecimal rdLossAmount) {
        this.rdLossAmount = rdLossAmount;
    }

    public BigDecimal getScrapRate() {
        return scrapRate == null ? BigDecimal.ZERO : scrapRate;
    }

    public void setScrapRate(BigDecimal scrapRate) {
        this.scrapRate = scrapRate;
    }

    public BigDecimal getScrap() {
        return scrap == null ? BigDecimal.ZERO : scrap;
    }

    public void setScrap(BigDecimal scrap) {
        this.scrap = scrap;
    }

    public BigDecimal getScrapAmount() {
        return scrapAmount == null ? BigDecimal.ZERO : scrapAmount;
    }

    public void setScrapAmount(BigDecimal scrapAmount) {
        this.scrapAmount = scrapAmount;
    }

    public String getScrapNo() {
        return scrapNo == null ? "" : scrapNo;
    }

    public void setScrapNo(String scrapNo) {
        this.scrapNo = scrapNo;
    }

    public BigDecimal getSampleRevenue() {
        return sampleRevenue == null ? BigDecimal.ZERO : sampleRevenue;
    }

    public void setSampleRevenue(BigDecimal sampleRevenue) {
        this.sampleRevenue = sampleRevenue;
    }

    public BigDecimal getScrapPrice() {
        return scrapPrice == null ? BigDecimal.ZERO : scrapPrice;
    }

    public void setScrapPrice(BigDecimal scrapPrice) {
        this.scrapPrice = scrapPrice;
    }

    public BigDecimal getSpecialIncome() {
        return specialIncome == null ? BigDecimal.ZERO : specialIncome;
    }

    public void setSpecialIncome(BigDecimal specialIncome) {
        this.specialIncome = specialIncome;
    }

    public String getSpecialIncomeNo() {
        return specialIncomeNo == null ? "" : specialIncomeNo;
    }

    public void setSpecialIncomeNo(String specialIncomeNo) {
        this.specialIncomeNo = specialIncomeNo;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public BigDecimal getWastage() {
        return wastage;
    }

    public void setWastage(BigDecimal wastage) {
        this.wastage = wastage;
    }

    public BigDecimal getFinished() {
        return finished;
    }

    public void setFinished(BigDecimal finished) {
        this.finished = finished;
    }

    public Integer getAccountTitleId() {
        return accountTitleId;
    }

    public void setAccountTitleId(Integer accountTitleId) {
        this.accountTitleId = accountTitleId;
    }

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

    public BigDecimal getOldUsed() {
        return oldUsed;
    }

    public void setOldUsed(BigDecimal oldUsed) {
        this.oldUsed = oldUsed;
    }

    public BigDecimal getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(BigDecimal remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getPicker() {
        return picker;
    }

    public void setPicker(String picker) {
        this.picker = picker;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }

    public BigDecimal getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(BigDecimal originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Integer getMaterialDataId() {
        return materialDataId;
    }

    public void setMaterialDataId(Integer materialDataId) {
        this.materialDataId = materialDataId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(Date selectDate) {
        this.selectDate = selectDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<MaterialEntity> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<MaterialEntity> materialList) {
        this.materialList = materialList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public BigDecimal getUsed() {
        return used;
    }

    public void setUsed(BigDecimal used) {
        this.used = used;
    }

    public void setAcqMonth(Date acqMonth) {
        this.acqMonth = acqMonth;
    }

    public Date getAcqMonth() {
        return acqMonth;
    }

    public void setCreateMonth(Date acqMonth) {
        this.acqMonth = acqMonth;
    }

    public BigDecimal getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(BigDecimal maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getMname() {
        return mname;
    }

    public void setAcqDate(Date acqDate) {
        this.acqDate = acqDate;
    }

    public Date getAcqDate() {
        return acqDate;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
    }

    public BigDecimal getRdYield() {
        return rdYield;
    }

    public void setRdYield(BigDecimal rdYield) {
        this.rdYield = rdYield;
    }

    public BigDecimal getDepreciationRatio() {
        return depreciationRatio;
    }

    public void setDepreciationRatio(BigDecimal depreciationRatio) {
        this.depreciationRatio = depreciationRatio;
    }
}
