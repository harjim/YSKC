package com.yskc.ms.models.flow;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/3/17 8:08
 * @Description:
 * @author: hsx
 */
public class FlowListModel implements Serializable {

    private Integer id;

    //模块名
    private String flowName;

    //标题
    private String content;

    private String companyName;

    private Integer lastSubmiterId;

    private String lastSubmiter;

    private String auditUsers;

    private Integer status;

    private Date lastUpdateTime;

    private Integer moduleId;

    /**
     * 节点个数
     */
    private Integer nodeCnt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getLastSubmiterId() {
        return lastSubmiterId;
    }

    public void setLastSubmiterId(Integer lastSubmiterId) {
        this.lastSubmiterId = lastSubmiterId;
    }

    public String getLastSubmiter() {
        return lastSubmiter;
    }

    public void setLastSubmiter(String lastSubmiter) {
        this.lastSubmiter = lastSubmiter;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getNodeCnt() {
        return nodeCnt;
    }

    public void setNodeCnt(Integer nodeCnt) {
        this.nodeCnt = nodeCnt;
    }
}
