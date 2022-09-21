package com.yskc.rs.models.accountingrdsalary;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.accountingrdsalary
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-05 14:40
 * @Description: 核算区间
 */
public class AccountPeriodModel {

    private Date begin;

    private Date end;

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
