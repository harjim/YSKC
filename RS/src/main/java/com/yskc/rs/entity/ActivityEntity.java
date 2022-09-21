package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-04 16:06:23
 */
@TableName("c_activity")
public class ActivityEntity implements Serializable {

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Integer lastUpdatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer msCreatorId;
    /**
     *
     */
    private Integer msLastUpdatorId;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer year;
    /**
     *
     */
    private String pKey;
    /**
     *
     */
    private String pValue;
    public ActivityEntity() {
    }

    public ActivityEntity(Integer creatorId, Integer lastUpdatorId, Date createTime, Date lastUpdateTime, Integer msCreatorId, Integer msLastUpdatorId, Integer companyId, Integer year, String pKey, String pValue) {
        this.creatorId = creatorId;
        this.lastUpdatorId = lastUpdatorId;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
        this.msCreatorId = msCreatorId;
        this.msLastUpdatorId = msLastUpdatorId;
        this.companyId = companyId;
        this.year = year;
        this.pKey = pKey;
        this.pValue = pValue;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getYear() {
        return year;
    }

    public void setPKey(String pKey) {
        this.pKey = pKey;
    }

    public String getPKey() {
        return pKey;
    }

    public void setPValue(String pValue) {
        this.pValue = pValue;
    }

    public String getPValue() {
        return pValue;
    }


}
