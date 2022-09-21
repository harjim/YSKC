package com.yskc.ms.models.log;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/6/4 16:33
 * description:查询日志参数model
 */
public class QuerySysLogModel extends PageParams implements Serializable {


    private String userName;

    private String description;

    private Integer logType;

    private String companyName;

    private Integer pageType;

    /**
     * 用户rs操作日志查询用
     */
    private Integer userId;
    /**
     * 日志周期
     */
    private Date beginDate;

    private Date endDate;

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

    public Integer getPageType() {
        return pageType;
    }

    public void setPageType(Integer pageType) {
        this.pageType = pageType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

//    public void loadTerm() {
//        this.beginDate = this.beginDate != null ? DateUtil.beginOfDay(this.beginDate) : null;
//        this.endDate = this.endDate != null ? DateUtil.endOfDay(this.endDate) : null;
//    }
}
