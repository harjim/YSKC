package com.yskc.rs.entity.project;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.projectenergy.EnergyListModel;
import com.yskc.rs.models.projectenergy.ProjectEnergyModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-17 15:32:14
 */
@TableName("p_energy")
public class ProjectEnergyEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    private Integer projectId;
    /**
     *
     */
    private Integer energyDataId;
    /**
     *
     */
    private Integer companyId;
    /**
     *
     */
    private Integer creatorId;
    /**
     *
     */
    private Date createTime;
    /**
     *
     */
    private Date lastUpdateTime;
    /**
     *
     */
    private Integer lastUpdatorId;

    private BigDecimal rdAmount;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    private Integer etype;
    private BigDecimal totalHour;
    private BigDecimal rdHour;
    private BigDecimal totalYield;
    private BigDecimal rdYield;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setEnergyDataId(Integer energyDataId) {
        this.energyDataId = energyDataId;
    }

    public Integer getEnergyDataId() {
        return energyDataId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public static ProjectEnergyEntity build(EnergyListModel item, UserInfo userInfo, Date now, Integer projectId, BigDecimal totalHour, BigDecimal rdHour, BigDecimal amount, Integer etype) {
        ProjectEnergyEntity result = new ProjectEnergyEntity();
        result.companyId = userInfo.getCompanyId();
        result.creatorId = userInfo.getId();
        result.lastUpdatorId = userInfo.getId();
        result.energyDataId = item.getId();
        result.projectId = projectId;
        result.createTime = now;
        result.creatorId = userInfo.getUserId();
        result.lastUpdatorId =userInfo.getUserId();
        result.msCreatorId = userInfo.getMsUserId();
        result.msLastUpdatorId = userInfo.getMsUserId();
        result.lastUpdateTime = now;
        //研发费用
        result.rdAmount = amount;
        result.totalHour = totalHour;
        result.rdHour = rdHour;
        result.etype = etype;
        result.totalYield = BigDecimal.ZERO;
        result.rdYield = BigDecimal.ZERO;
        return result;
    }

    public static ProjectEnergyEntity build(ProjectEnergyModel item, UserInfo userInfo, Date now) {
        ProjectEnergyEntity result = new ProjectEnergyEntity();
        result.id = item.getId();
        result.rdAmount = item.getRdAmount();
        result.totalHour = item.getTotalHour();
        result.rdHour = item.getRdHour();
        result.lastUpdatorId = userInfo.getUserSource() == 0 ? userInfo.getId() : -1;
        result.msLastUpdatorId = userInfo.getUserSource() == 1 ? userInfo.getId() : -1;
        result.lastUpdateTime = now;
        result.totalYield = item.getTotalYield();
        result.rdYield = item.getRdYield();
        return result;
    }

    public static ProjectEnergyEntity buildDefault(Integer id) {
        ProjectEnergyEntity result = new ProjectEnergyEntity();
        result.id = id;
        result.rdAmount = result.rdHour = result.totalHour = result.totalYield = result.rdYield =  BigDecimal.ZERO;
        return result;
    }

    public Integer getEtype() {
        return etype;
    }

    public void setEtype(Integer etype) {
        this.etype = etype;
    }

    public BigDecimal getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(BigDecimal totalHour) {
        this.totalHour = totalHour;
    }

    public BigDecimal getRdHour() {
        return rdHour;
    }

    public void setRdHour(BigDecimal rdHour) {
        this.rdHour = rdHour;
    }

    public BigDecimal getTotalYield() {
        return totalYield;
    }

    public void setTotalYield(BigDecimal totalYield) {
        this.totalYield = totalYield;
    }

    public BigDecimal getRdYield() {
        return rdYield;
    }

    public void setRdYield(BigDecimal rdYield) {
        this.rdYield = rdYield;
    }

}
