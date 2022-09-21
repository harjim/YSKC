package com.yskc.rs.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.rs.models.wechat.EmployeeOpenidModel;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.entity
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-27 10:16
 * @Description: 人员openid表
 */
@TableName("employeeOpenid")
public class EmployeeOpenidEntity {

    @TableId
    private Integer id;
    private Integer companyId;
    private String companyName;
    private String enumber;
    private String openid;
    private String unionid;
    private Boolean remainAttendance;
    private Date createTime;
    private Date lastUpdateTime;

    public static EmployeeOpenidEntity build(EmployeeOpenidModel eo) {
        EmployeeOpenidEntity entity = new EmployeeOpenidEntity();
        entity.companyId = eo.getCompanyId();
        entity.companyName = eo.getCompanyName();
        entity.enumber = eo.getEnumber();
        entity.openid = eo.getOpenid();
        entity.remainAttendance = false;
        entity.unionid = eo.getUnionid();
        Date now = new Date();
        entity.createTime = now;
        entity.lastUpdateTime = now;
        return entity;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Boolean getRemainAttendance() {
        return remainAttendance;
    }

    public void setRemainAttendance(Boolean remainAttendance) {
        this.remainAttendance = remainAttendance;
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
