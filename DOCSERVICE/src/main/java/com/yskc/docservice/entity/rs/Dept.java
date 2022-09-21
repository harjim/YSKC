package com.yskc.docservice.entity.rs;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yskc.docservice.models.rs.RsUserInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * 组织架构表
 *
 * @author huronghua
 */
@TableName("dept")
public class Dept implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private Integer id;
    private String deptName;
    private int parentId;
    private int level;
    private String identity;
    private String remark;
    private int creatorId;
    private Date createTime;
    private int companyId;
    private String fullPath;
    private String fullname;
    private Integer lastUpdatorId;

    private Date lastUpdateTime;

    private Integer msCreatorId;
    private Integer msLastUpdatorId;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
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

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static Dept build(RsUserInfo info, Date now) {
        Dept d = new Dept();
        d.companyId = info.getCompanyId();
        d.createTime = now;
        d.lastUpdateTime = now;
        d.creatorId = info.getId();
        d.msCreatorId = info.getMsUserId();
        d.lastUpdatorId = info.getId();
        d.msLastUpdatorId = info.getMsUserId();
        d.deptName = info.getCompanyName();
        d.fullPath = "";
        d.identity = "";
        d.level = 0;
        d.parentId = -1;
        return d;
    }

}
