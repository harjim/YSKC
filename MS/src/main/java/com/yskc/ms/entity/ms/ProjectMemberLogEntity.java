package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * Created by hck
 * on 2020/8/26 8:36
 * description:修改人员类型日志
 */
@TableName("project_member_log")
public class ProjectMemberLogEntity {
    @TableId
    private Integer id;
    private Integer projectId;
    private Integer customerId;
    private Integer mType;//人员类型
    private Integer memberId;//人员id
    private Integer operationId;//操作人id
    private Date operationTime;//操作时间

    public static ProjectMemberLogEntity build(Integer projectId, Integer customerId, Integer mType, Integer memberId, Integer createId, Date now) {
        ProjectMemberLogEntity entity = new ProjectMemberLogEntity();
        entity.projectId = projectId;
        entity.customerId = customerId;
        entity.mType = mType;
        entity.memberId = memberId;
        entity.operationId = createId;
        entity.operationTime = now;
        return  entity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public ProjectMemberLogEntity copy(Integer pId) {
        ProjectMemberLogEntity newLog = new ProjectMemberLogEntity();
        newLog.customerId = this.customerId;
        newLog.mType = this.mType;
        newLog.memberId = this.memberId;
        newLog.operationId = this.operationId;
        newLog.operationTime = this.operationTime;
        newLog.projectId = pId;
        return newLog;
    }
}
