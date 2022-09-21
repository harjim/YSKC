package com.yskc.ms.models.params;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.params
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-13 17:15
 * @Description: 我的审核参数
 */
public class MyAuditParams extends PageParams {

    private String modeName;
    private String companyName;
    private String content;
    private Boolean ongoing;
    private Boolean mySubmit;
    private Integer status;
    private Integer lastSubmiter;
    private Date beginDate;
    private Date endDate;
    private String auditUsers;

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getOngoing() {
        return ongoing;
    }

    public void setOngoing(Boolean ongoing) {
        this.ongoing = ongoing;
    }

    public Boolean getMySubmit() {
        return mySubmit;
    }

    public void setMySubmit(Boolean mySubmit) {
        this.mySubmit = mySubmit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLastSubmiter() {
        return lastSubmiter;
    }

    public void setLastSubmiter(Integer lastSubmiter) {
        this.lastSubmiter = lastSubmiter;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAuditUsers() {
        return auditUsers;
    }

    public void setAuditUsers(String auditUsers) {
        this.auditUsers = auditUsers;
    }
}
