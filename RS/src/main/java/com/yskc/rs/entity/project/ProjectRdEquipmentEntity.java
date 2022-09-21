package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.models.projectrdequipment.ProjectRdEquipmentModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 10:54
 * @Description: 项目设备研发折旧
 */
@TableName("p_rdEquipment")
public class ProjectRdEquipmentEntity implements Serializable {

    @TableId
    private Integer id;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private Integer projectId;
    private Date month;
    private BigDecimal rdDepreciation;
    private String ecode;
    private BigDecimal rdHour;
    private BigDecimal rdRatio;
    private BigDecimal powerRate;
    private BigDecimal powerUnitPrice;

    public BigDecimal getPowerUnitPrice() {
        return powerUnitPrice;
    }

    public void setPowerUnitPrice(BigDecimal powerUnitPrice) {
        this.powerUnitPrice = powerUnitPrice;
    }

    public BigDecimal getPowerRate() {
        return powerRate;
    }

    public void setPowerRate(BigDecimal powerRate) {
        this.powerRate = powerRate;
    }

    public static ProjectRdEquipmentEntity build(Date now,Integer companyId,Integer userId,Integer msUserId,
                                                 Integer projectId,Date month,String ecode,BigDecimal rdHour,BigDecimal rdDepreciation,
                                                 BigDecimal rdRatio){
        ProjectRdEquipmentEntity entity = new ProjectRdEquipmentEntity();
        entity.createTime = entity.lastUpdateTime = now;
        entity.creatorId = entity.lastUpdatorId = userId;
        entity.msCreatorId = entity.msLastUpdatorId = msUserId;
        entity.companyId = companyId;
        entity.projectId = projectId;
        entity.month = month;
        entity.ecode = ecode;
        entity.rdHour = rdHour;
        entity.rdDepreciation  = rdDepreciation;
        entity.rdRatio = rdRatio;
        return entity;
    }

    public static ProjectRdEquipmentEntity build(Date now,Integer userId,Integer msUserId,Integer companyId,ProjectRdEquipmentModel item,int hourBit) {
        ProjectRdEquipmentEntity entity = new ProjectRdEquipmentEntity();
        entity.companyId = companyId;
        entity.projectId = item.getProjectId();
        entity.lastUpdateTime = entity.createTime = now;
        entity.msLastUpdatorId = entity.msCreatorId = msUserId;
        entity.lastUpdatorId = entity.creatorId =  userId;
        entity.month = item.getMonth();
        entity.rdDepreciation = item.getRdDepreciation();
        entity.ecode = item.getEcode();
        entity.rdHour = item.getRdHourByBit(hourBit);
        return entity;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRdDepreciation() {
        return rdDepreciation;
    }

    public void setRdDepreciation(BigDecimal rdDepreciation) {
        this.rdDepreciation = rdDepreciation;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRdRatio() {
        return rdRatio;
    }

    public void setRdRatio(BigDecimal rdRatio) {
        this.rdRatio = rdRatio;
    }
}
