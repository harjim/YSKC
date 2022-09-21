package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/7/1 13:41
 * description:
 */
@TableName("patent_apply")
public class RsPatentApplyEntity implements Serializable {

    @TableId
    private Integer id;

    private String patentNo;

    private String applyName;

    private String address;

    private Integer companyId;

    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    private int lastUpdatorId;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public RsPatentApplyEntity() {
    }

    public RsPatentApplyEntity(String patentNo, String applyName, String address, Integer msUserId, Date now) {
        this.patentNo = patentNo;
        this.address = address;
        this.applyName = applyName;
        this.creatorId = -1;
        this.lastUpdatorId = -1;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.setCreateTime(now);
        this.setLastUpdateTime(now);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(int lastUpdatorId) {
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


}
