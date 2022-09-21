package com.yskc.docservice.entity.rs.company;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.models.rs.company.CompanyHolidayModel;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity.company
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-05 08:47
 * @Description: 客户节假日表
 */
@TableName("c_holiday")
public class CompanyHoliday {

    @TableId
    private Integer id;
    public int companyId;
    public Date month;
    public String holidays;
    private Integer msCreatorId;
    private Integer msLastUpdatorId;
    private Integer creatorId;
    private Integer lastUpdatorId;
    private Date createTime;
    private Date lastUpdateTime;

    public static CompanyHoliday build(CompanyHolidayModel item, Integer companyId, Integer msUserId, Integer userId, Date now) {
        CompanyHoliday holiday = new CompanyHoliday();
        holiday.month = item.getMonth();
        holiday.holidays = item.getHolidays();
        holiday.companyId = companyId;
        holiday.creatorId = userId;
        holiday.lastUpdatorId = userId;
        holiday.msCreatorId = msUserId;
        holiday.msLastUpdatorId = msUserId;
        holiday.createTime = now;
        holiday.lastUpdateTime = now;
        return holiday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public String getHolidays() {
        return holidays;
    }

    public void setHolidays(String holidays) {
        this.holidays = holidays;
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
}
