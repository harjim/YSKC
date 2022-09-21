package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 研发人员
 */
@TableName("p_rdEmployee")
public class ProjectRdEmployeeEntity implements Serializable {
    @TableId
    private Integer id;
    /**
     *
     */
    private Date createTime;
    private Date lastUpdateTime;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer projectId;
    private Integer companyId;
    private Date month;
    private String enumber;
    private BigDecimal rdHour;
    private BigDecimal rdInsuranceFund;
    private BigDecimal rdPay;
    private BigDecimal rdRatio;


    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getRdInsuranceFund() {
        return rdInsuranceFund;
    }

    public void setRdInsuranceFund(BigDecimal rdInsuranceFund) {
        this.rdInsuranceFund = rdInsuranceFund;
    }

    public BigDecimal getRdPay() {
        return rdPay;
    }

    public void setRdPay(BigDecimal rdPay) {
        this.rdPay = rdPay;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getRdRatio() {
        return rdRatio;
    }

    public void setRdRatio(BigDecimal rdRatio) {
        this.rdRatio = rdRatio;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }
}
