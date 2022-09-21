package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-19 10:31
 * @Description: 成果审核实体
 */
@TableName("p_audit_achievement")
public class AuditAchievementEntity {
    @TableId
    private Integer id;
    private Integer moduleId;
    private Integer companyId;
    /**
     * 成果id
     */
    private Integer achievementId;
    /**
     * 0进行中 1通过 2驳回 3激活
     */
    private Integer status;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;

    public AuditAchievementEntity() {
    }

    public AuditAchievementEntity(Date now, Integer moduleId, Integer msUserId, Integer companyId, Integer achievementId, Integer status) {
        this.moduleId = moduleId;
        this.companyId = companyId;
        this.achievementId = achievementId;
        this.status = status;
        this.msCreatorId = msUserId;
        this.msLastUpdatorId = msUserId;
        this.createTime = this.lastUpdateTime = now;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
