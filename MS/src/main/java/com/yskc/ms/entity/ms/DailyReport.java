package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.common.utils.DateUtil;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-08-03 16:23
 * @Description: 用户审核汇总
 */
@TableName("daily_report")
public class DailyReport {
    @TableId
    private Integer id;
    private Integer userId;
    private Date workDate;
    private Date createTime;
    private Date updateTime;
    private Integer done;
    private Integer commit;
    private Integer reject;
    private Integer companyId;

    public DailyReport() {
    }

    public DailyReport(String workDate, Integer userId,Integer companyId, Date now) {
        this.userId = userId;
        this.workDate = DateUtil.parse(workDate, DateUtil.DEFAULT_DATE_FORMAT);
        this.companyId = companyId;
        this.createTime = now;
        this.updateTime = now;
        this.done = 0;
        this.commit = 0;
        this.reject = 0;
    }

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

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
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

    public void addCnt(Integer commit, Integer done, Integer reject) {
        this.commit += commit;
        this.done += done;
        this.reject += reject;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
