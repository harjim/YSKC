package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.models.rs.ProjectStatusModel;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models
 * @Author: zhangdingfu
 * @CreateTime: 2020-07-15 17:47
 * @Description: 项目预算状态
 */
@TableName("p_budget_status")
public class BudgetStatus {
    @TableId
    private Integer id;

    private Integer companyId;

    private Integer projectId;

    /**
     * 0:初始，1:提交，2:拒绝，3: 定稿，4:撤回
     */
    private Integer status;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public static BudgetStatus build(ProjectStatusModel model, Integer msUserId, Date now,Integer status) {
        BudgetStatus budgetStatus = new BudgetStatus();
        budgetStatus.companyId = model.getCompanyId();
        budgetStatus.projectId = model.getProjectId();
        budgetStatus.createTime = now;
        budgetStatus.lastUpdateTime = now;
        budgetStatus.msCreatorId = msUserId;
        budgetStatus.msLastUpdatorId = msUserId;
        budgetStatus.status = status;
        return budgetStatus;
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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
