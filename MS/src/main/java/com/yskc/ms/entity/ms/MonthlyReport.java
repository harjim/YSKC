package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-09 14:30
 * @Description: 月报表
 */
@TableName("monthly_report")
public class MonthlyReport {

    @TableId
    private Integer id;
    private Integer userId;
    /**
     * 工作月份
     */
    private Date workMonth;
    private Date createTime;
    private Date updateTime;
    private Integer done;
    private Integer commit;
    private Integer reject;
    private Integer serviceCnt;
    private Integer companyCnt;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getWorkMonth() {
        return workMonth;
    }

    public void setWorkMonth(Date workMonth) {
        this.workMonth = workMonth;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
    }

    public Integer getCommit() {
        return commit;
    }

    public void setCommit(Integer commit) {
        this.commit = commit;
    }

    public Integer getReject() {
        return reject;
    }

    public void setReject(Integer reject) {
        this.reject = reject;
    }

    public Integer getServiceCnt() {
        return serviceCnt;
    }

    public void setServiceCnt(Integer serviceCnt) {
        this.serviceCnt = serviceCnt;
    }

    public Integer getCompanyCnt() {
        return companyCnt;
    }

    public void setCompanyCnt(Integer companyCnt) {
        this.companyCnt = companyCnt;
    }
}
