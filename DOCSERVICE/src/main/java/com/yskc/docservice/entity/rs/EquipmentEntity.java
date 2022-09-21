package com.yskc.docservice.entity.rs;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.docservice.enums.EquipmentEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-09 10:13:52
 */
@TableName("equipment")
public class EquipmentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private String ename;
    /**
     *
     */
    private String ecode;
    /**
     *
     */
    private String emodal;
    /**
     *
     */
    private BigDecimal unitPrice;
    /**
     *
     */
    private String unit;
    /**
     *
     */
    private BigDecimal quantity;
    /**
     *
     */
    private Integer usefullife;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date purchaseDate;
    /**
     *
     */
    private String remark;
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
    private Integer deptId;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date depreciationDate;
    /**
     *
     */
    private BigDecimal depreciationRate;
    /**
     *
     */
    private BigDecimal usagePower;

    private Integer etype;

    private Date lastUpdateTime;

    private Integer lastUpdatorId;

    private String kinds;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    private Integer workshopId;

    private String deptName;
    private String workshop;
    private String productLine;
    private String processSection;
    private String data;
    private Date invalidated;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEname() {
        return ename;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEmodal(String emodal) {
        this.emodal = emodal;
    }

    public String getEmodal() {
        return emodal;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setUsefullife(Integer usefullife) {
        this.usefullife = usefullife;
    }

    public Integer getUsefullife() {
        return usefullife;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
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

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDepreciationDate(Date depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public Date getDepreciationDate() {
        return depreciationDate;
    }

    public void setDepreciationRate(BigDecimal depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public BigDecimal getDepreciationRate() {
        return depreciationRate;
    }

    public void setUsagePower(BigDecimal usagePower) {
        this.usagePower = usagePower;
    }

    public BigDecimal getUsagePower() {
        return usagePower;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    /**
     * 设备列表导入数据build
     *
     * @param excel
     * @param zero
     * @param now
     * @param info
     */
    public static EquipmentEntity build(EquipmentExcel excel, BigDecimal zero, Date now, RsUserInfo info, Integer deptId) {
        EquipmentEntity entity = new EquipmentEntity();
        entity.depreciationDate = excel.getDepreciationDate();
        entity.purchaseDate = excel.getPurchaseDate();
        entity.depreciationRate = excel.getDepreciationRate() == null ? zero : excel.getDepreciationRate();
        entity.quantity = excel.getQuantity() == null || excel.getQuantity().compareTo(zero) <= 0 ? BigDecimal.ONE : excel.getQuantity();
        entity.remark = excel.getRemark();
        entity.unit = (excel.getUnit() == null ? "" : excel.getUnit());
        entity.unitPrice = excel.getUnitPrice();
        entity.usefullife = excel.getUsefullife() == null || excel.getUsefullife() <= 0 ? 10 : excel.getUsefullife();
        entity.usagePower = excel.getUsagePower() == null ? zero : excel.getUsagePower();
        entity.ecode = excel.getEcode();
        entity.ename = excel.getEname();
        entity.emodal = excel.getEmodal() == null ? "" : excel.getEmodal();
        entity.etype = EquipmentEnum.getOrDefault(excel.getTypeName()).getType();
        entity.companyId = info.getCompanyId();
        entity.deptId = deptId;
        entity.kinds = excel.getKinds();
        entity.deptName = excel.getFullname();
        entity.workshop = excel.getWorkshop();
        entity.productLine = excel.getProductLine();
        entity.processSection = excel.getProcessSection();
        entity.data = excel.getData();
        entity.invalidated = excel.getInvalidated();
        entity.createBase(now, info.getCompanyId(),info.getUserId(),info.getMsUserId());
        return entity;
    }


    public static EquipmentEntity build(String ecode, String ename, Integer type, String rdDeptName, Date date,
                                        Integer companyId,Integer userId,Integer msUserId) {
        EquipmentEntity equipment = new EquipmentEntity();
        equipment.ecode = ecode;
        equipment.ename = ename;
        equipment.etype = type;
        equipment.unitPrice = BigDecimal.ZERO;
        equipment.quantity = BigDecimal.ONE;
        equipment.usefullife = 10;
        equipment.usagePower = equipment.unitPrice;
        equipment.depreciationRate = equipment.unitPrice;
        equipment.unit = "";
        equipment.emodal = "";
        equipment.deptName = rdDeptName;
        equipment.createBase(date, companyId,userId,msUserId);
        return equipment;
    }

    void createBase(Date now, Integer companyId,Integer userId,Integer msUserId) {
        this.creatorId = userId;
        this.lastUpdatorId = userId;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.createTime = now;
        this.lastUpdateTime = now;
        this.companyId = companyId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getProcessSection() {
        return processSection;
    }

    public void setProcessSection(String processSection) {
        this.processSection = processSection;
    }

    public Date getInvalidated() {
        return invalidated;
    }

    public void setInvalidated(Date invalidated) {
        this.invalidated = invalidated;
    }
}