package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.utils.AttDataUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-13 18:13:59
 */
@TableName("p_equipment_used")
public class EquipmentUsedEntity implements Serializable {
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
    private String ecode;
    /**
     *
     */
    private Date month;
    /**
     *
     */
    private String usedEquData;
    /**
     *
     */
    private BigDecimal workHours;
    /**
     *
     */
    private BigDecimal remainHours;

    private Date createTime;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    @TableField(exist = false)
    private String[] usedArr;

    public static EquipmentUsedEntity buildUsed(String ecode, BigDecimal workHours, BigDecimal remainHours, String usedEquData) {
        EquipmentUsedEntity entity = new EquipmentUsedEntity();
        entity.ecode = ecode;
        entity.workHours = workHours;
        entity.remainHours = remainHours;
        entity.usedEquData = usedEquData;
        return entity;
    }

    public static EquipmentUsedEntity build(String ecode, BigDecimal workHours, BigDecimal remainHours, String usedEquData, Integer companyId, Integer userId, Integer msUserId, Date month, Date now) {
        EquipmentUsedEntity entity = buildUsed(ecode, workHours, remainHours, usedEquData);
        entity.usedArr = usedEquData.split(",");
        entity.companyId = companyId;
        entity.createTime = entity.lastUpdateTime = now;
        entity.creatorId = entity.lastUpdatorId = userId;
        entity.msCreatorId = entity.msLastUpdatorId = msUserId;
        entity.month = month;
        return entity;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEcode() {
        return ecode;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getMonth() {
        return month;
    }

    public void setUsedEquData(String usedEquData) {
        this.usedEquData = usedEquData;
    }

    public String getUsedEquData() {
        return usedEquData;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public BigDecimal getRemainHours() {
        return remainHours;
    }

    public void setRemainHours(BigDecimal remainHours) {
        this.remainHours = remainHours;
    }

    /**
     * 设置更新[该方法在setCreate()前触发]
     *
     * @param remainHours
     * @param usedEquData
     * @param now
     * @param userId
     * @param msUserId
     */
    public void setUpdate(BigDecimal remainHours, String usedEquData, Date now, Integer userId, Integer msUserId) {
        this.remainHours = remainHours;
        this.usedEquData = usedEquData;
        this.lastUpdateTime = now;
        this.lastUpdatorId = userId;
        this.msLastUpdatorId = msUserId;
    }

    /**
     * 设置创建[该方法在setCreate()后触发]
     *
     * @param monthBegin
     * @param companyId
     */
    public void setCreate(Date monthBegin, Integer companyId) {
        this.month = monthBegin;
        this.companyId = companyId;
        this.creatorId = this.lastUpdatorId;
        this.createTime = this.lastUpdateTime;
        this.msCreatorId = this.msLastUpdatorId;
    }

    public String[] getUsedArr() {
        return usedArr;
    }

    public void setUsedArr(String[] usedArr) {
        this.usedArr = usedArr;
    }

    public void reloadUsedEquData() {
        this.usedEquData = String.join(",", this.usedArr);
        BigDecimal newRemainHour = this.workHours.subtract(AttDataUtils.getTotal(this.usedArr));
        this.remainHours = newRemainHour.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newRemainHour;
    }
}
