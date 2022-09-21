package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/5/27 11:09
 * @Description:高新进度审核日志实体类
 * @author: hsx
 */
@TableName("p_deliver_log")
public class ProjectDeliverLog implements Serializable {

    private static final long serialVersionUID = 2244333537255888062L;

    @TableId
    private Integer id;

    private Integer companyId;

    private Integer deliverId;

    private Integer status;

    private String suggestion;

    private Integer node;

    private Date logTime;

    private String auditUser;

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

    public Integer getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(Integer deliverId) {
        this.deliverId = deliverId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public ProjectDeliverLog() {
    }

    public ProjectDeliverLog(Integer companyId, Integer deliverId, Integer status, String suggestion, Integer node, Date logTime, String auditUser) {
        this.companyId = companyId;
        this.deliverId = deliverId;
        this.status = status;
        this.suggestion = suggestion;
        this.node = node;
        this.logTime = logTime;
        this.auditUser = auditUser;
    }
}
