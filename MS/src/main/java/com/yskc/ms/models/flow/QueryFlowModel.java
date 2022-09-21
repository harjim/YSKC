package com.yskc.ms.models.flow;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/12/18 8:25
 * description:
 */
public class QueryFlowModel extends PageParams {

    //模块名
    private String flowName;

    //标题
    private String content;

    //输入框查询
    private String companyName;

    //发起人  下拉框传id
    private Integer lastSubmiterId;

    private String lastSubmiter;

    //当前处理人   输入框查询
    private String auditUsers;

    //状态  序号查询
    private Integer status;

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
}
