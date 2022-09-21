package com.yskc.docservice.entity.rs;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 09:08
 * @Description: 研发设备列表
 */
@TableName("rdEquipment")
public class RdEquipmentEntity implements Serializable {

    @TableId
    private Integer id;

    private Integer creatorId;

    private Integer lastUpdatorId;

    private Date createTime;

    private Date lastUpdateTime;

    private Integer msCreatorId;

    private Integer msLastUpdatorId;

    private Integer year;

    private Integer companyId;

    private Integer rdDeptId;

    private String ecode;

    private Integer etype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getRdDeptId() {
        return rdDeptId;
    }

    public void setRdDeptId(Integer rdDeptId) {
        this.rdDeptId = rdDeptId;
    }

    public String getEcode() {
        return ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public static RdEquipmentEntity build(Integer companyId, Integer userId, Integer msUserId, String ecode,
                                          int year, Date now, Integer etype,Integer rdDeptId) {
        RdEquipmentEntity rdEquipmentEntity = new RdEquipmentEntity();
        rdEquipmentEntity.companyId = companyId;
        rdEquipmentEntity.creatorId = userId;
        rdEquipmentEntity.lastUpdatorId = userId;
        rdEquipmentEntity.createTime = now;
        rdEquipmentEntity.lastUpdateTime = now;
        rdEquipmentEntity.msLastUpdatorId = msUserId;
        rdEquipmentEntity.msCreatorId = msUserId;
        rdEquipmentEntity.year = year;
        rdEquipmentEntity.rdDeptId = rdDeptId;
        rdEquipmentEntity.ecode = ecode;
        rdEquipmentEntity.etype = etype;
        return rdEquipmentEntity;
    }
}
