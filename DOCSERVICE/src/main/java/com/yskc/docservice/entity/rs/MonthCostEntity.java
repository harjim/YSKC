package com.yskc.docservice.entity.rs;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import fr.opensagres.xdocreport.core.utils.StringUtils;

import java.util.Date;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/entity/rs
 * @Author: hm
 * @CreateTime: 2022/8/17
 * @Description: 月度成本实体
 */
@TableName("c_year_cost")
public class MonthCostEntity {
    @TableId
    private Integer id;

    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer companyId;
    private Integer year;
    private Integer rdType;
    private Float cost;
    private Integer month;

    public MonthCostEntity(Integer creatorId, Integer msCreatorId, Integer companyId, Integer year, Integer rdType, String cost, Integer month) {
        this.creatorId = creatorId;
        this.msCreatorId = msCreatorId;
        this.companyId = companyId;
        this.year = year;
        this.rdType = rdType;
        if (StringUtils.isEmpty(cost) || Float.parseFloat(cost) == 0) {
            this.cost = null;
        } else {
            this.cost = Float.parseFloat(cost);
        }
        this.month = month;
        this.lastUpdatorId = this.creatorId;
        this.createTime = new Date();
        this.lastUpdateTime = this.createTime;
        this.msLastUpdatorId = this.msCreatorId;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
