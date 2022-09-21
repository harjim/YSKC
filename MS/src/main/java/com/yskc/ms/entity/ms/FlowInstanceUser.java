package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

/**
 * Created by hck
 * on 2020/12/15 10:41
 * description:
 */
@TableName("flowInstance_user")
public class FlowInstanceUser extends MsBaseEntity {

    @TableId
    private Integer id;
    private Integer instanceId;
    private Integer userId;
    private Integer nodeId;
    private Boolean status;//标注或签 会签状态
    @TableField(exist = false)
    private Integer lastSubmiter;
    private Integer moduleId;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getLastSubmiter() {
        return lastSubmiter;
    }

    public void setLastSubmiter(Integer lastSubmiter) {
        this.lastSubmiter = lastSubmiter;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}
