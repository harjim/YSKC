package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-01 09:49
 * @Description: 操作日志实体（统计）
 */
@TableName("operation_log")
public class OperationLogEntity {

    private Integer id ;
    private Integer userId ;
    private Date operationDate;
    private Date lastOperationTime;
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
