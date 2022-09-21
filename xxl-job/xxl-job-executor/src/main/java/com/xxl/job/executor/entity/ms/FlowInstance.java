package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xxl.job.executor.models.flow.FlowModeModel;
import com.xxl.job.executor.models.flowInstance.FlowInstanceModel;

import java.util.Date;

/**
 * @author zdf
 */
@TableName("flowInstance")
public class FlowInstance {
    @TableId
    private Integer id;
    private Integer creatorId;
   private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer curNodeId;
    private Integer flowId;
    private Integer status;
    private Integer moduleId;
    private Integer nodeStatus;
    private Integer deptId;
    private Integer lastSubmiter;

    public FlowInstance(FlowInstanceModel item,Date now,Integer userId) {
        this.id = item.getId();
       this.lastUpdatorId = userId;
        this.lastSubmiter = userId;
        this.lastUpdateTime= now;
        this.curNodeId = item.getCurNodeId();
        this.flowId = item.getFlowId();
        this.status = item.getStatus();
        this.nodeStatus = item.getNodeStatus();
        this.moduleId= item.getModuleId();
    }

    public FlowInstance(Integer id,Integer userId, Date now, FlowModeModel flowModule,Integer deptId) {
        this.id = id;
      this.reboot(userId,now,flowModule,deptId);
    }


    public void reboot(Integer userId, Date now, FlowModeModel flowModule, Integer deptId) {
        creatorId = userId;
        this.lastUpdatorId = creatorId;
        createTime = now;
        this.lastSubmiter = userId;
        lastUpdateTime = now;
        curNodeId = flowModule.getNodeId();
        this.flowId = flowModule.getFlowId();
        this.status = 0;
        this.nodeStatus = 0;
        this.moduleId = flowModule.getModuleId();
        this.deptId = null != deptId ? deptId : -1;
    }

    public FlowInstance(Integer id, Integer nodeId, Integer lastUpdatorId,Integer deptId) {
        this.curNodeId = nodeId;
        this.id = id;
      this.lastUpdatorId = lastUpdatorId;
        this.lastSubmiter = lastUpdatorId;
        this.deptId = deptId;
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

    public Integer getCurNodeId() {
        return curNodeId;
    }

    public void setCurNodeId(Integer curNodeId) {
        this.curNodeId = curNodeId;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(Integer nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getLastSubmiter() {
        return lastSubmiter;
    }

    public void setLastSubmiter(Integer lastSubmiter) {
        this.lastSubmiter = lastSubmiter;
    }
}
