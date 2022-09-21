package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.config.Constant;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.utils.AttDataUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-17 14:32:50
 */
@TableName("p_equipment")
public class ProjectEquipmentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    private Integer equipmentDataId;

    private String equData;

    private Integer companyId;

    private Integer projectId;

    private String ecode;

    private Date month;

    private BigDecimal workHours;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer creatorId;
    private Date createTime;
    private Integer lastUpdatorId;
    private Date lastUpdateTime;

    @TableField(exist = false)
    private String[] equDataArr;

    public static ProjectEquipmentEntity build(Date now, String ecode, Date month, Integer projectId,
                                               Integer userId, Integer msUserId, Integer companyId, BigDecimal workHours,
                                               Integer equipmentDataId) {
        ProjectEquipmentEntity entity = new ProjectEquipmentEntity();
        entity.ecode = ecode;
        entity.month = month;
        entity.projectId = projectId;
        entity.workHours = workHours;
        entity.equipmentDataId = equipmentDataId;
        entity.createTime = entity.lastUpdateTime = now;
        entity.companyId = companyId;
        entity.creatorId = entity.lastUpdatorId = userId;
        entity.msCreatorId = entity.msLastUpdatorId = msUserId;
        entity.equDataArr = Constant.DEFAULT_HOUR_ATT_DATA.split(",");
        return entity;
    }

    public static ProjectEquipmentEntity build(Date now, String ecode, String equData, Date month, Integer projectId, UserInfo userInfo, BigDecimal workHours, Integer equipmentDataId) {
        ProjectEquipmentEntity entity = new ProjectEquipmentEntity();
        entity.ecode = ecode;
        entity.equData = equData;
        entity.month = month;
        entity.projectId = projectId;
        entity.workHours = workHours;
        entity.equipmentDataId = equipmentDataId;
        entity.createTime = now;
        entity.lastUpdateTime = now;
        entity.companyId = userInfo.getCompanyId();
        entity.creatorId = userInfo.getId();
        entity.msCreatorId = userInfo.getMsUserId();
        entity.lastUpdatorId = userInfo.getId();
        entity.msLastUpdatorId = userInfo.getMsUserId();
        return entity;
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

    public void setEquipmentDataId(Integer equipmentDataId) {
        this.equipmentDataId = equipmentDataId;
    }

    public Integer getEquipmentDataId() {
        return equipmentDataId;
    }

    public void setEquData(String equData) {
        this.equData = equData;
    }

    public String getEquData() {
        return equData;
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

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getWorkHours() {
        return workHours;
    }

    public void setWorkHours(BigDecimal workHours) {
        this.workHours = workHours;
    }

    public String[] getEquDataArr() {
        return equDataArr;
    }

    public void setEquDataArr(String[] equDataArr) {
        this.equDataArr = equDataArr;
    }

    /**
     * 添加时间
     *
     * @param dayIndex
     * @param dayHour
     * @param used
     */
    public void addTime(int dayIndex, BigDecimal dayHour, EquipmentUsedEntity used) {
        BigDecimal oldDayHour = new BigDecimal(this.equDataArr[dayIndex]);
        BigDecimal newHour = oldDayHour.add(dayHour);
        if (newHour.compareTo(AttDataUtils.DAY_HOUR) > 0) {
            return;
        }
        BigDecimal oldUsedHour = new BigDecimal(used.getUsedArr()[dayIndex]);
        BigDecimal newUsedHour = newHour.add(oldUsedHour);
        if (newUsedHour.compareTo(AttDataUtils.DAY_HOUR) > 0) {
            return;
        }
        this.equDataArr[dayIndex] = newHour.stripTrailingZeros().toPlainString();
        used.getUsedArr()[dayIndex] = newUsedHour.stripTrailingZeros().toPlainString();
    }

    public void reloadEquData() {
        this.equData = String.join(",", this.equDataArr);
        this.workHours = AttDataUtils.getTotal(this.equDataArr);
    }
}
