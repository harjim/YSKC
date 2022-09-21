package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/8/23 10:23
 * @Description:
 */
@TableName("patent_plan_process")
public class PatentPlanProcess {

    private Integer id;

    private Integer patentPlanId;

    private Integer userId;

    private Date createTime;

    private Integer process;

    private String remark;


    public PatentPlanProcess() {
    }

    public PatentPlanProcess(Integer patentPlanId, Integer userId, Date createTime, Integer process, String remark) {
        this.patentPlanId = patentPlanId;
        this.userId = userId;
        this.createTime = createTime;
        this.process = process;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatentPlanId() {
        return patentPlanId;
    }

    public void setPatentPlanId(Integer patentPlanId) {
        this.patentPlanId = patentPlanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getProcess() {
        return process;
    }

    public void setProcess(Integer process) {
        this.process = process;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
