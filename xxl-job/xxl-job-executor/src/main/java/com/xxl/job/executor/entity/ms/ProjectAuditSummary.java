package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-02-20 17:02
 * @Description: 项目审核统计
 */
@TableName("project_audit_summary")
public class ProjectAuditSummary {

    @TableId
    private Integer id;
    private Integer projectId;
    private Integer doneCnt;
    private Integer ongoingCnt;
    private Integer docDoneCnt;
    private Integer docOngoingCnt;
    private Integer doneTotal;
    private Integer ongoingTotal;
    private Date createTime;
    private Date lastUpdateTime;

    public static ProjectAuditSummary buildCnt(Integer projectId, Integer doneCnt, Integer ongoingCnt, Date now) {
        ProjectAuditSummary entity = new ProjectAuditSummary();
        entity.projectId = projectId;
        entity.doneCnt = doneCnt;
        entity.ongoingCnt = ongoingCnt;
        entity.docDoneCnt = 0;
        entity.docOngoingCnt = 0;
        entity.reset(now);
        return entity;
    }

    public static ProjectAuditSummary buildDocCnt(Integer projectId, Integer docDoneCnt, Integer docOngoingCnt, Date now) {
        ProjectAuditSummary entity = new ProjectAuditSummary();
        entity.projectId = projectId;
        entity.doneCnt = 0;
        entity.ongoingCnt = 0;
        entity.docDoneCnt = docDoneCnt;
        entity.docOngoingCnt = docOngoingCnt;
        entity.reset(now);
        return entity;
    }

    private void reset(Date now) {
        this.doneTotal = 0;
        this.ongoingTotal = 0;
        this.createTime = now;
        this.lastUpdateTime = now;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getDoneCnt() {
        return doneCnt;
    }

    public void setDoneCnt(Integer doneCnt) {
        this.doneCnt = doneCnt;
    }

    public Integer getOngoingCnt() {
        return ongoingCnt;
    }

    public void setOngoingCnt(Integer ongoingCnt) {
        this.ongoingCnt = ongoingCnt;
    }

    public Integer getDocDoneCnt() {
        return docDoneCnt;
    }

    public void setDocDoneCnt(Integer docDoneCnt) {
        this.docDoneCnt = docDoneCnt;
    }

    public Integer getDocOngoingCnt() {
        return docOngoingCnt;
    }

    public void setDocOngoingCnt(Integer docOngoingCnt) {
        this.docOngoingCnt = docOngoingCnt;
    }

    public Integer getDoneTotal() {
        return doneTotal;
    }

    public void setDoneTotal(Integer doneTotal) {
        this.doneTotal = doneTotal;
    }

    public Integer getOngoingTotal() {
        return ongoingTotal;
    }

    public void setOngoingTotal(Integer ongoingTotal) {
        this.ongoingTotal = ongoingTotal;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
