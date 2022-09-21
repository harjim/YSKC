package com.xxl.job.executor.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.xxl.job.executor.core.config.Constant;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-04 08:56
 * @Description: 推送考勤消息日志
 */
@TableName("pushAttendance_log")
public class PushAttendanceLog {

    @TableId
    private Integer id;
    private Integer companyId;
    private String enumber;
    private Date month;
    private Date createTime;
    private String result;

    public PushAttendanceLog(Date now, Integer companyId, String enumber, Date month, String result, String msg) {
        this.createTime = now;
        this.companyId = companyId;
        this.enumber = enumber;
        this.month = month;
        String temp;
        if (result.contains(Constant.BRACE_OPEN) && result.contains(Constant.BRACE_CLOSE)) {
            temp = result.substring(result.indexOf(Constant.BRACE_OPEN) + 1, result.indexOf(Constant.BRACE_CLOSE));
        } else {
            temp = result;
        }
        this.result = temp + ",msg=" + msg;
        if (this.result.length() > 500) {
            this.result.substring(0, 500);
        }
    }

    public PushAttendanceLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
