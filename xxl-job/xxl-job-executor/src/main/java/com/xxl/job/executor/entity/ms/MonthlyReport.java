package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xxl.job.executor.models.monthlyreport.MonthlyReportDetailModel;
import com.yskc.common.utils.DateUtil;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
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
    @TableField(exist = false)
    private Set<Integer> companyIds = new HashSet<>();

    public MonthlyReport() {
    }

    public MonthlyReport(MonthlyReportDetailModel item,Date now) {
        this.userId = item.getUserId();
        this.workMonth = DateUtil.parse(item.getWorkMonth(),DateUtil.DEFAULT_YYMM_FORMAT);
        this.createTime = this.updateTime = now;
        this.done = item.getDone() != null ? item.getDone() : 0;
        this.commit = item.getCommit() != null ? item.getCommit() : 0;
        this.reject = item.getReject() != null ? item.getReject() : 0;
        this.serviceCnt = item.getServiceCnt() != null ? item.getServiceCnt() : 0;
        this.companyCnt = 0;
        if(null != item.getCompanyId()){
            this.companyIds.add(item.getCompanyId());
        }
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
        if(!CollectionUtils.isEmpty(companyIds)){
            return companyIds.size();
        }
        return companyCnt;
    }

    public void setCompanyCnt(Integer companyCnt) {
        this.companyCnt = companyCnt;
    }

    public void addCnt(MonthlyReportDetailModel item) {
        this.companyIds.add(item.getCompanyId());
        this.done += item.getDone() != null ? item.getDone() : 0;
        this.commit += item.getCommit() != null ? item.getCommit() : 0;
        this.reject += item.getReject() != null ? item.getReject() : 0;
    }

    public Set<Integer> getCompanyIds() {
        return companyIds;
    }

    public void setCompanyIds(Set<Integer> companyIds) {
        this.companyIds = companyIds;
    }
}
