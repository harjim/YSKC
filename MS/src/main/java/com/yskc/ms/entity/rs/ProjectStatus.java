package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-15 11:45
 * @Description: 项目状态（月: 初始，提交，拒绝，定稿，撤回）
 */
@TableName("p_project_status")
public class ProjectStatus {
    @TableId
    private Integer id;

    private Integer rdType;

    private Integer companyId;

    private Integer projectId;

    private Date month;

    /**
     * 0:初始，1:提交，2:拒绝，3: 定稿，4:撤回
     */
    private Integer status;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public static ProjectStatus build(Integer projectId, Date month, Integer companyId, Integer status, Integer msUserId, Date now) {
        ProjectStatus projectStatus = new ProjectStatus();
        projectStatus.rdType = 0;
        projectStatus.companyId = companyId;
        projectStatus.projectId = projectId;
        projectStatus.month = month;
        projectStatus.status = status;
        projectStatus.createTime = now;
        projectStatus.lastUpdateTime = now;
        projectStatus.msCreatorId = msUserId;
        projectStatus.msLastUpdatorId = msUserId;
        return projectStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
}
