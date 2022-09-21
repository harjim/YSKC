package com.yskc.docservice.models.rs.rdequipment;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.rdequipment
 * @Author: zhangdingfu
 * @CreateTime: 2021-09-08 16:58
 * @Description: 完整年度model
 */
public class FullYearProjectModel {

    /**
     * 唯一：ecode/enumber等
     */
    private String key;

    private String rdTitle;

    private Date beginDate;

    private Date endDate;

    /**
     * 离职日期（设备不存在该日期）
     */
    private Date leaveDate;
    /**
     * 缺失月份
     */
    private String lackMonth;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
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

    public String getLackMonth() {
        return lackMonth;
    }

    public void setLackMonth(String lackMonth) {
        this.lackMonth = lackMonth;
    }

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }
}
