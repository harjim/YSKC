package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

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
public class SysLog implements Serializable {

    @TableId
    private Integer id;

    private String description;

    private Integer logType;

    private Integer userId;

    private String userName;

    private Date logTime;

    private String logUrl;

    private String logParams;

    private String remark;

    private String requestIp;

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

    public String getRequestIp() {
        return requestIp;
    }

    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    public static SysLog build(Integer userId, String userName, String realName, String description, String url, String argument, String requestIp) {
        SysLog sysLog = new SysLog();
        try {
            sysLog.userId = userId;
            sysLog.userName = realName + "(" + userName + ")";
            sysLog.description = description;
            sysLog.logUrl = url;
            sysLog.logParams = argument != null && argument.length() > 4000 ? argument.substring(0, 4000) : argument;
            sysLog.logTime = new Date();
            sysLog.logType = 0;
            sysLog.requestIp = requestIp;
        } catch (Exception e) {
            return null;
        }
        return sysLog;
    }

    public static SysLog build(Integer userId, String userName, String realName, String description, String url, String argument, String requestIp, String remark) {
        SysLog sysLog = build(userId, userName, realName, description, url, argument, requestIp);
        if (sysLog == null) {
            return null;
        }
        sysLog.setRemark(remark);
        return sysLog;
    }

}
