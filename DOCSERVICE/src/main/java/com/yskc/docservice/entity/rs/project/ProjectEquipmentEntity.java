package com.yskc.docservice.entity.rs.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.models.rs.RsUserInfo;

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

    public static ProjectEquipmentEntity build(Date now, String ecode, String equData, Date month, Integer projectId, RsUserInfo userInfo, BigDecimal workHours, Integer equipmentDataId) {
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
}
