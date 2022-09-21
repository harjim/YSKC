package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-30 09:57
 * @Description: 操作日志统计
 */
@TableName("operation_log")
public class OperationLog {
    @TableId
    private Integer id;

    private Integer userId;
    /**
     * 操作日期
     */
    private Date operationDate;

    /**
     * 最后操作时间
     */
    private Date lastOperationTime;
    /**
     * 操作次数
     */
    private Integer operationCnt;

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

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
    }

    public Date getLastOperationTime() {
        return lastOperationTime;
    }

    public void setLastOperationTime(Date lastOperationTime) {
        this.lastOperationTime = lastOperationTime;
    }

    public Integer getOperationCnt() {
        return operationCnt;
    }

    public void setOperationCnt(Integer operationCnt) {
        this.operationCnt = operationCnt;
    }
}
