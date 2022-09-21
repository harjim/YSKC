package com.yskc.rs.entity.init;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.models.UserInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-23 16:40:58
 */
@TableName("i_equipment")
public class InitEquipmentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private String ecode;
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
    private Date lastUpdateTime;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private String effect;
    private Date entryDate;

    private Integer year;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public String getEcode() {
        return ecode;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public static InitEquipmentEntity build(UserInfo info, String ecode, String effect, Integer projectId, Date now,Date entryDate,Integer year) {
        InitEquipmentEntity initEquipmentEntity = buildUpdate(info,effect,now,entryDate);
        initEquipmentEntity.companyId = info.getCompanyId();
        initEquipmentEntity.ecode = ecode;
        initEquipmentEntity.creatorId = info.getUserId();
        initEquipmentEntity.createTime = now;
        initEquipmentEntity.msCreatorId = info.getMsUserId();
        initEquipmentEntity.projectId = projectId;
        initEquipmentEntity.year=year;
        return initEquipmentEntity;
    }

    public static InitEquipmentEntity buildUpdate(UserInfo info,  String effect, Date now, Date entryDate) {
        InitEquipmentEntity initEquipmentEntity = new InitEquipmentEntity();
        initEquipmentEntity.lastUpdatorId = info.getUserId();
        initEquipmentEntity.lastUpdateTime = now;
        initEquipmentEntity.msLastUpdatorId = info.getMsUserId();
        initEquipmentEntity.effect = effect;
        initEquipmentEntity.entryDate = entryDate;
        return initEquipmentEntity;
    }
}
