package com.yskc.docservice.entity.rs.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.projectrdemployee.ProjectRdEmployeeModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.project
 * @Author: zhangdingfu
 * @CreateTime: 2020-03-31 10:30
 * @Description: 人员研发费用
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
    private BigDecimal attendanceHour;
    private Boolean attendanceEdit;

    /**
     * 研发工时
     *
     * @param now
     * @param projectId
     * @param userInfo
     * @param model
     * @return
     */
    public static ProjectRdEmployeeEntity build(Date now, Integer projectId, RsUserInfo userInfo, ProjectRdEmployeeModel model, Integer hourBit) {
        ProjectRdEmployeeEntity entity = new ProjectRdEmployeeEntity();
        entity.loadUpdate(userInfo, now);
        entity.month = model.getMonth();
        entity.projectId = projectId;
        entity.companyId = userInfo.getCompanyId();
        entity.enumber = model.getEnumber();
        entity.rdInsuranceFund = model.getRdInsuranceFund();
        entity.rdHour = model.getRdHourByBit(hourBit);
        entity.rdPay = model.getRdPay();
        return entity;
    }

    /**
     * attandance更新
     *
     * @param userInfo
     * @param now
     * @param total
     * @param id
     * @return
     */
    public static ProjectRdEmployeeEntity build(RsUserInfo userInfo, Date now, BigDecimal total, Integer id) {
        ProjectRdEmployeeEntity entity = new ProjectRdEmployeeEntity();
        entity.loadUpdate(userInfo, now);
        entity.id = id;
        entity.attendanceHour = total;
        return entity;
    }

    private void loadUpdate(RsUserInfo userInfo, Date now) {
        this.lastUpdateTime = now;
        this.msLastUpdatorId = userInfo.getMsUserId();
        this.lastUpdatorId = userInfo.getUserId();
    }

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

    public BigDecimal getAttendanceHour() {
        return attendanceHour;
    }

    public void setAttendanceHour(BigDecimal attendanceHour) {
        this.attendanceHour = attendanceHour;
    }

    public Boolean getAttendanceEdit() {
        return attendanceEdit;
    }

    public void setAttendanceEdit(Boolean attendanceEdit) {
        this.attendanceEdit = attendanceEdit;
    }
}
