package com.xxl.job.executor.entity.rs;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @program: xxl-job
 * @description: projectAuditOpt
 * @author: cyj
 * @create: 2022-05-17 16:50
 **/
@TableName("c_worker")
public class CWorker {

    private Integer companyId;
    private Integer year;
    private String ysTech;
    private String ysFina;
    private Date createTime;
    private Date lastUpdateTime;

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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getYsTech() {
        return ysTech;
    }

    public void setYsTech(String ysTech) {
        this.ysTech = ysTech;
    }

    public String getYsFina() {
        return ysFina;
    }

    public void setYsFina(String ysFina) {
        this.ysFina = ysFina;
    }

    public CWorker() {
    }

    public CWorker(Integer companyId, Integer year, Date createTime, Date lastUpdateTime) {
        this.companyId = companyId;
        this.year = year;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setYsTechOrYsFina(Integer mType, String realName) {
        if (mType==1){
            this.ysTech = realName;
        }else if (mType==2){
            this.ysFina = realName;
        }
    }

}
