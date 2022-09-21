package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.models.projectyieldconfig.RefreshYieldConfigModel;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-21 13:45
 * @Description: 试制生成配置表
 */
@TableName("p_trial_config")
public class ProjectTrialConfigEntity extends BaseEntity {

    @TableId
    private Integer id;

    private Integer companyId;

    /**
     * 试制开始时间
     */
    private Date trialTime;
    /**
     * 开始休息时间
     */
    private Date startTime;
    /**
     * 结束休息时间
     */
    private Date endTime;

    public static ProjectTrialConfigEntity build(RefreshYieldConfigModel refresh, Integer companyId, Integer userId, Integer msUserId, Date now) {
        ProjectTrialConfigEntity entity = new ProjectTrialConfigEntity();
        entity.trialTime = refresh.getTrialTime();
        entity.startTime = refresh.getStartTime();
        entity.endTime = refresh.getEndTime();
        entity.companyId = companyId;
        entity.create(userId,msUserId,now);
        return entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(Date trialTime) {
        this.trialTime = trialTime;
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
}
