package com.xxl.job.executor.models.flowInstance;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.executor.utils.ToolUtils;

import java.util.*;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.flowInstance
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-26 09:28
 * @Description: 审核信息
 */
public class AuditMsgModel {
    private Integer type;
    private Integer status;
    private Set<Integer> userIds;
    private String modeName;
    private String nodeName;
    private String projectName;
    private Integer initiatorId;
    private Integer instanceId;
    private Integer nodeId;

    public AuditMsgModel(Integer status, String modeName, String nodeName, String projectName,
                                Integer initiatorId, Integer instanceId, Integer nodeId,Set<Integer> userIds,Integer type) {
        this.status = status;
        this.modeName = modeName;
        this.nodeName = nodeName;
        this.projectName = projectName;
        this.initiatorId = initiatorId;
        this.instanceId = instanceId;
        this.nodeId = nodeId;
        this.userIds=userIds;
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<Integer> userIds) {
        this.userIds = userIds;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getInitiatorId() {
        return initiatorId;
    }

    public void setInitiatorId(Integer initiatorId) {
        this.initiatorId = initiatorId;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public List<Map<String, String>> buildMsgForm(String creator, Boolean isSendCopy, String state) {
        List<Map<String, String>> form = new ArrayList<>();
        form.add(ToolUtils.kvMap("发起人：", creator));
        form.add(ToolUtils.kvMap("项目：", projectName));
        form.add(ToolUtils.kvMap("模块：", modeName));
        form.add(ToolUtils.kvMap("节点：", nodeName));
        if (!isSendCopy) {
            form.add(ToolUtils.kvMap("状态：", state));
        } else {
            form.add(ToolUtils.kvMap("状态：", "已审核"));
        }
        form.add(ToolUtils.kvMap("时间：", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)));
        return form;
    }

}
