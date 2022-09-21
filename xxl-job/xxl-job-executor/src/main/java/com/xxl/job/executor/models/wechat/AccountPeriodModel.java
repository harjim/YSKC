package com.xxl.job.executor.models.wechat;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.wechat
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-20 09:29
 * @Description: 公司账期model
 */
public class AccountPeriodModel {

    private Integer companyId;

    private Date month;

    private Date begin;

    private Date end;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
