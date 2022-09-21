package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.models.company.SupportModel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by hck
 * on 2020/12/4 14:13
 * description:市政府扶持情况
 */
@TableName("c_support")
public class SupportEntity {
    @TableId
    private Integer id;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private String projectName;
    private Date startTime;
    private Date endTime;
    private String supportTime;
    private String supportDeptName;
    private BigDecimal supportAmount;
    private Date checkTime;
    private String checkResult;
    private String remark;
    private Integer syear;
    private String issuceNum;
    private String master;

    public static SupportEntity buildUpdate(SupportModel item, Date now, Integer userId) {
        SupportEntity entity = build(item);
        entity.id = item.getId();
        entity.loadUpdate(now, userId);
        return entity;
    }

    public static SupportEntity buildInsert(SupportModel item, Date now, Integer userId) {
        SupportEntity entity = build(item);
        entity.loadCreate(now, userId);
        return entity;
    }

    private static SupportEntity build(SupportModel item) {
        SupportEntity entity = new SupportEntity();
        entity.companyId = item.getCompanyId();
        entity.projectName = item.getProjectName();
        entity.supportDeptName = item.getSupportDeptName();
        entity.supportAmount = item.getSupportAmount();
        entity.checkTime = item.getCheckTime();
        entity.syear = item.getSyear();
        entity.issuceNum = item.getIssuceNum();
        entity.master = item.getMaster();
        entity.supportTime = item.getSupportTime();
        return entity;
    }

    private void loadCreate(Date now, Integer creatorUserId) {
        this.createTime = now;
        this.creatorId = -1;
        this.msCreatorId = creatorUserId;
        loadUpdate(now, creatorUserId);
    }

    private void loadUpdate(Date now, Integer updateUserId) {
        this.lastUpdateTime = now;
        this.lastUpdatorId = -1;
        this.msLastUpdatorId = updateUserId;
    }

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getSupportTime() {
        return supportTime;
    }

    public void setSupportTime(String supportTime) {
        this.supportTime = supportTime;
    }

    public String getSupportDeptName() {
        return supportDeptName;
    }

    public void setSupportDeptName(String supportDeptName) {
        this.supportDeptName = supportDeptName;
    }

    public BigDecimal getSupportAmount() {
        return supportAmount;
    }

    public void setSupportAmount(BigDecimal supportAmount) {
        this.supportAmount = supportAmount;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSyear() {
        return syear;
    }

    public void setSyear(Integer syear) {
        this.syear = syear;
    }

    public String getIssuceNum() {
        return issuceNum;
    }

    public void setIssuceNum(String issuceNum) {
        this.issuceNum = issuceNum;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
