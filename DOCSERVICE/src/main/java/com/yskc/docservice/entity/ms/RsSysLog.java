package com.yskc.docservice.entity.ms;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-12 16:24
 * @Description: 日志
 */
@TableName("sys_log")
public class RsSysLog implements Serializable {

    @TableId
    private Integer id;

    private String description;

    /**
     * 0 管理端 1 企业端 2 小程序
     */
    private Integer logType;

    private Integer userId;

    private Integer source;

    private String userName;

    private Date logTime;

    private String logUrl;

    private String logParams;

    private String requestIp;

    private String remark;

    private Integer companyId;

    private String companyName;

    public static RsSysLog build(Integer userId, Integer userSource, String userName, String realName, String description, String url, String argument, String requestIp, Integer companyId, String companyName) {
        RsSysLog sysLog = new RsSysLog();
        try {
            sysLog.userId = userId;
            sysLog.source = userSource;
            sysLog.userName = realName + "(" + userName + ")";
            sysLog.description = description;
            sysLog.logUrl = url;
            sysLog.logParams = argument != null && argument.length() > 4000 ? argument.substring(0, 4000) : argument;
            sysLog.logTime = new Date();
            sysLog.logType = userSource == 0 ? 1 : 0;
            sysLog.requestIp = requestIp;
            sysLog.companyId = companyId;
            sysLog.companyName = companyName;
        } catch (Exception e) {
            return null;
        }
        return sysLog;
    }
    public static RsSysLog build(Integer userId, Integer userSource, String userName, String realName, String description, String url, String argument, String requestIp, Integer companyId, String companyName, String remark) {
        RsSysLog sysLog = build(userId, userSource, userName, realName, description, url, argument, requestIp, companyId, companyName);
        if (sysLog == null) {
            return null;
        }
        sysLog.setRemark(remark);
        return sysLog;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }
        this.description = description;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getLogParams() {
        return logParams;
    }

    public void setLogParams(String logParams) {
        this.logParams = logParams;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
